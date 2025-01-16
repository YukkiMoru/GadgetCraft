package com.github.yukkimoru.gadgetCraft.customBlock

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.UUID


object MechanicDatabase {
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
            uuid TEXT PRIMARY KEY,
            Mechanics TEXT,
            world TEXT,
            x REAL,
            y REAL,
            z REAL
        )
    """.trimIndent()
		executeUpdate(sql)
	}

	@Synchronized
	fun setMechanics(uuid: UUID, mechanic: String, world: String, x: Double, y: Double, z: Double) {
		val sql = """
        INSERT INTO BlockGimmicks (uuid, Mechanics, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?)
        ON CONFLICT(uuid) DO UPDATE SET Mechanics = excluded.Mechanics, world = excluded.world, x = excluded.x, y = excluded.y, z = excluded.z
    """.trimIndent()
		try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			statement.setString(2, mechanic)
			statement.setString(3, world)
			statement.setDouble(4, x)
			statement.setDouble(5, y)
			statement.setDouble(6, z)
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	@Synchronized
	fun removeMechanics(uuid: UUID, mechanic: String, world: String, x: Double, y: Double, z: Double) {
		val sql = """
        DELETE FROM BlockGimmicks WHERE uuid = ? AND Mechanics = ? AND world = ? AND x = ? AND y = ? AND z = ?
    """.trimIndent()
		try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			statement.setString(2, mechanic)
			statement.setString(3, world)
			statement.setDouble(4, x)
			statement.setDouble(5, y)
			statement.setDouble(6, z)
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	@Synchronized
	fun getMechanics(uuid: UUID): String {
		val sql = "SELECT Mechanics FROM BlockGimmicks WHERE uuid = ?"
		return try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			val resultSet = statement.executeQuery()
			if (resultSet.next()) resultSet.getString("BlockGimmicks") else ""
		} catch (e: SQLException) {
			e.printStackTrace()
			""
		}
	}

	@Synchronized
	fun isMechanicOwner(uuid: UUID, mechanic: String, world: String, x: Double, y: Double, z: Double): Boolean {
		val sql = """
        SELECT Mechanics FROM BlockGimmicks 
        WHERE uuid = ? AND world = ? AND x = ? AND y = ? AND z = ? AND Mechanics = ?
    """.trimIndent()
		return try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			statement.setString(2, world)
			statement.setDouble(3, x)
			statement.setDouble(4, y)
			statement.setDouble(5, z)
			statement.setString(6, mechanic)
			val resultSet = statement.executeQuery()
			resultSet.next() // 一致する行があれば true を返す
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