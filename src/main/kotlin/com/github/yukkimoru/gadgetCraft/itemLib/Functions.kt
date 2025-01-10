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

	fun isWearingEquip(player: Player, equipment: Int, customModelData: Int): Boolean {
		val item: ItemStack = when (equipment) {
			Equip.BOOTS -> player.inventory.boots
			Equip.HELMET -> player.inventory.helmet
			Equip.CHESTPLATE -> player.inventory.chestplate
			Equip.LEGGINGS -> player.inventory.leggings
			else -> throw IllegalArgumentException("Invalid equipment type: $equipment")
		} ?: return false
		return ItemFactory(plugin).isItemWithCustomModelData(item, customModelData)
	}

	fun delayTick(ticks: Long = 1L, task: () -> Unit) {
		object : BukkitRunnable() {
			override fun run() {
				task()
			}
		}.runTaskLater(plugin, ticks)
	}

	fun runWithCooldown(cooldown: Long, task: () -> Unit) {
		if (!isRunning) {
			isRunning = true
			delayTick(1L) {
				task()
				delayTick(cooldown) {
					isRunning = false
				}
			}
		}
	}

	companion object {
		private var isRunning = false
	}
}