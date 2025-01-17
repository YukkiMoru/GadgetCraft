package com.github.yukkimoru.gadgetCraft.Economy

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.UUID

object EconomyDB {
	private const val DB_URL = "jdbc:sqlite:plugins/GadgetCraft/Economy.db"
	private var connection: Connection? = null

	init {
		connect()
		createTableEconomy()
	}

	@Synchronized
	fun connect() {
		if (connection == null || connection!!.isClosed) {
			try {
				val dbFile = File("plugins/GadgetCraft/Economy.db")
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
            CREATE TABLE IF NOT EXISTS VALUT (
                uuid TEXT PRIMARY KEY,
                balance REAL
            )
        """.trimIndent()
		executeUpdate(sql)
	}

	@Synchronized
	fun getBalance(uuid: UUID): Double {
		val sql = "SELECT balance FROM VALUT WHERE uuid = ?"
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
            INSERT INTO VALUT (uuid, balance) VALUES (?, ?)
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

	private fun executeUpdate(sql: String) {
		try {
			connection?.createStatement()?.executeUpdate(sql)
		} catch (e: SQLException) {
			e.printStackTrace()
		}
	}

	fun purchaseItem(player: String, price: Int) {
		val uuid = UUID.fromString(player)
		val balance = getBalance(uuid)
		if (balance >= price) {
			setBalance(uuid, balance - price)
		}
	}
}