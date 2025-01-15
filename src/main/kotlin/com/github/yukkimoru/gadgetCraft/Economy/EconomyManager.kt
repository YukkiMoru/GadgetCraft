package com.github.yukkimoru.gadgetCraft.Economy

import java.util.UUID

object EconomyManager {

	fun setBalance(uuid: UUID, balance: Double) {
		Sqlite.setBalance(uuid, balance)
	}

	fun getBalance(uuid: UUID): Double {
		return Sqlite.getBalance(uuid)
	}

	fun withdrawBalance(uuid: UUID, amount: Double) {
		val balance = getBalance(uuid)
		setBalance(uuid, balance + amount)
	}

	fun depositBalance(uuid: UUID, amount: Double) {
		val balance = getBalance(uuid)
		setBalance(uuid, balance - amount)
	}
}