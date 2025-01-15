package com.github.yukkimoru.gadgetCraft.BlockManager

import com.github.yukkimoru.gadgetCraft.Economy.Sqlite
import org.bukkit.Location
import java.util.UUID

object BlockManager {

	fun setLocation(uuid: UUID,location: Location,mechanic: String): Double {
		return Sqlite.getBalance(uuid)
	}

	fun getBalance(uuid: UUID, balance: Double) {
		Sqlite.setBalance(uuid, balance)
	}
}