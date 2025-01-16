package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.Economy.Sqlite
import org.bukkit.Location
import java.util.UUID

object BlockManager {

	fun setMechanicLocation(uuid: UUID, location: String, mechanic: String) {
		Sqlite.setMechanics(uuid, mechanic, location)
	}

	fun getMechanicLocation(uuid: UUID, location: Location, mechanic: String): Double {
		return Sqlite.getBalance(uuid)
	}
}