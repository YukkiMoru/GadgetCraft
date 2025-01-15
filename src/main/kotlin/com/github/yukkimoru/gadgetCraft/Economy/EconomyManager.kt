package com.github.yukkimoru.gadgetCraft.Economy

import java.util.UUID

object EconomyManager {



	fun getBalance(uuid: UUID): Double {
		return Sqlite.getBalance(uuid)
	}

	fun setBalance(uuid: UUID, balance: Double) {
		Sqlite.setBalance(uuid, balance)
	}
}