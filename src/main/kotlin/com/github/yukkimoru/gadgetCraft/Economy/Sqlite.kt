package com.github.yukkimoru.gadgetCraft.Economy

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.UUID

object Sqlite {
	private const val DB_URL = "jdbc:sqlite:plugins/GadgetCraft/economy.db"
	private var connection: Connection? = null

	init {
		connect()
		createTableEconomy()
		createTableShop()
	}

	@Synchronized
	fun connect() {
		if (connection == null || connection!!.isClosed) {
			try {
				val dbFile = File("plugins/GadgetCraft/economy.db")
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

	private fun createTableEconomy() {
		val sql = """
            CREATE TABLE IF NOT EXISTS economy (
                uuid TEXT PRIMARY KEY,
                balance REAL
            )
        """.trimIndent()
		executeUpdate(sql)
	}

	private fun createTableShop() {
		val sql = """
            CREATE TABLE IF NOT EXISTS BlockMechanics (
                uuid TEXT PRIMARY KEY,
                Mechanics TEXT,
                location TEXT
            )
        """.trimIndent()
		executeUpdate(sql)
	}

	@Synchronized
	fun getBalance(uuid: UUID): Double {
		val sql = "SELECT balance FROM economy WHERE uuid = ?"
		return try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			val resultSet = statement.executeQuery()
			if (resultSet.next()) resultSet.getDouble("balance") else 0.0
		} catch (e: SQLException) {
			e.printStackTrace()
			0.0
		}
	}

	@Synchronized
	fun setBalance(uuid: UUID, balance: Double) {
		val sql = """
            INSERT INTO economy (uuid, balance) VALUES (?, ?)
            ON CONFLICT(uuid) DO UPDATE SET balance = excluded.balance
        """.trimIndent()
		try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			statement.setDouble(2, balance)
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	@Synchronized
	fun setMechanics(uuid: UUID, mechanic: String, location: String) {
		val sql = """
			INSERT INTO BlockMechanics (uuid, Mechanics, location) VALUES (?, ?, ?)
			ON CONFLICT(uuid) DO UPDATE SET Mechanics = excluded.Mechanics, location = excluded.location
		""".trimIndent()
		try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			statement.setString(2, mechanic)
			statement.setString(3, location)
			statement.executeUpdate()
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	@Synchronized
	fun getMechanics(uuid: UUID): String {
		val sql = "SELECT Mechanics FROM BlockMechanics WHERE uuid = ?"
		return try {
			val statement: PreparedStatement = connection!!.prepareStatement(sql)
			statement.setString(1, uuid.toString())
			val resultSet = statement.executeQuery()
			if (resultSet.next()) resultSet.getString("Mechanics") else ""
		} catch (e: SQLException) {
			e.printStackTrace()
			""
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