package com.github.yukkimoru.gadgetCraft.Economy

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.util.UUID

object Sqlite {
	private const val DB_URL = "jdbc:sqlite:plugins/GadgetCraft/economy.db"
	private var connection: Connection? = null

	init {
		connect()
		createTable()
	}

	fun connect() {
		connection = DriverManager.getConnection(DB_URL)
	}

	private fun createTable() {
		val sql = """
            CREATE TABLE IF NOT EXISTS economy (
                uuid TEXT PRIMARY KEY,
                balance REAL
            )
        """.trimIndent()
		connection?.createStatement()?.execute(sql)
	}

	fun getBalance(uuid: UUID): Double {
		val sql = "SELECT balance FROM economy WHERE uuid = ?"
		val statement: PreparedStatement = connection!!.prepareStatement(sql)
		statement.setString(1, uuid.toString())
		val resultSet = statement.executeQuery()
		return if (resultSet.next()) resultSet.getDouble("balance") else 0.0
	}

	fun setBalance(uuid: UUID, balance: Double) {
		val sql = """
            INSERT INTO economy (uuid, balance) VALUES (?, ?)
            ON CONFLICT(uuid) DO UPDATE SET balance = excluded.balance
        """.trimIndent()
		val statement: PreparedStatement = connection!!.prepareStatement(sql)
		statement.setString(1, uuid.toString())
		statement.setDouble(2, balance)
		statement.executeUpdate()
	}
}