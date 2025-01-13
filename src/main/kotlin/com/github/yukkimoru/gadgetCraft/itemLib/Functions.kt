package com.github.yukkimoru.gadgetCraft.itemLib

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

@Suppress("unused")
class Functions(val plugin: JavaPlugin) {
	object Equip {
		const val HELMET = 39
		const val CHESTPLATE = 38
		const val LEGGINGS = 37
		const val BOOTS = 36
	}

	fun convEquipToID(equip: String): Int {
		return when (equip) {
			"BOOTS" -> Equip.BOOTS
			"HELMET" -> Equip.HELMET
			"CHESTPLATE" -> Equip.CHESTPLATE
			"LEGGINGS" -> Equip.LEGGINGS
			else -> throw IllegalArgumentException("Invalid equipment type: $equip")
		}
	}

	fun delayTick(ticks: Long = 1L, task: () -> Unit) {
		object : BukkitRunnable() {
			override fun run() {
				task()
			}
		}.runTaskLater(plugin, ticks)
	}
}