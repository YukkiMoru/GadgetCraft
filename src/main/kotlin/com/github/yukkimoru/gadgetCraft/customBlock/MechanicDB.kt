package com.github.yukkimoru.gadgetCraft.customBlock

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object MechanicDB {
	private const val DB_URL = "jdbc:sqlite:plugins/GadgetCraft/Mechanic.db"
	private var connection: Connection? = null

	init {
		connect()
		createTableMechanic()
	}

	@Synchronized
	fun connect() {
		if (connection == null || connection!!.isClosed) {
			try {
				val dbFile = File("plugins/GadgetCraft/Mechanic.db")
				dbFile.parentFile.mkdirs() // ディレクトリが存在しない場合は作成
				connection = DriverManager.getConnection(DB_URL)
			} catch (e: SQLException) {
				e.printStackTrace()
			}
		}
	}

	@Synchronized
	fun disconnect() {
		try {
			connection?.close()
		} catch (e: SQLException) {
			e.printStackTrace()
		} finally {
			connection = null
		}
	}

	private fun createTableMechanic() {
		val sql = """
            CREATE TABLE IF NOT EXISTS BlockGimmicks (
                player TEXT,
                mechanics TEXT,
                world TEXT,
                x REAL,
                y REAL,
                z REAL,
                PRIMARY KEY (x, y, z)
            )
        """.trimIndent()
		executeUpdate(sql)
	}

	@Synchronized
	fun setMechanics(player: String, mechanic: String, world: String, x: Double, y: Double, z: Double) {
		val sql = """
        INSERT INTO BlockGimmicks (player, mechanics, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?)
        ON CONFLICT(x, y, z) DO UPDATE SET player = excluded.player, mechanics = excluded.mechanics, world = excluded.world
    """.trimIndent()
		try {
			connection?.prepareStatement(sql)?.use { statement ->
				statement.setString(1, player)
				statement.setString(2, mechanic)
				statement.setString(3, world)
				statement.setDouble(4, x)
				statement.setDouble(5, y)
				statement.setDouble(6, z)
				statement.executeUpdate()
			}
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	@Synchronized
	fun removeMechanics(player: String, mechanic: String, world: String, x: Double, y: Double, z: Double) {
		val sql = """
            DELETE FROM BlockGimmicks WHERE player = ? AND mechanics = ? AND world = ? AND x = ? AND y = ? AND z = ?
        """.trimIndent()
		try {
			connection?.prepareStatement(sql)?.use { statement ->
				statement.setString(1, player)
				statement.setString(2, mechanic)
				statement.setString(3, world)
				statement.setDouble(4, x)
				statement.setDouble(5, y)
				statement.setDouble(6, z)
				statement.executeUpdate()
			}
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	@Synchronized
	fun isMechanicOwner(player: String, mechanic: String, world: String, x: Double, y: Double, z: Double): Boolean {
		val sql = """
        SELECT mechanics FROM BlockGimmicks
        WHERE player = ? AND world = ? AND x = ? AND y = ? AND z = ? AND mechanics = ?
    """.trimIndent()
		return try {
			connection?.prepareStatement(sql)?.use { statement ->
				statement.setString(1, player)
				statement.setString(2, world)
				statement.setDouble(3, x)
				statement.setDouble(4, y)
				statement.setDouble(5, z)
				statement.setString(6, mechanic)
				statement.executeQuery().use { resultSet ->
					resultSet.next() // Return true if a matching row is found
				}
			} ?: false
		} catch (e: SQLException) {
			e.printStackTrace()
			false
		}
	}

	private fun executeUpdate(sql: String) {
		try {
			connection?.createStatement()?.executeUpdate(sql)
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}
}