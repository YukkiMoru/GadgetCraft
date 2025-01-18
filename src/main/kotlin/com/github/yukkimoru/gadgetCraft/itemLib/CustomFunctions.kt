package com.github.yukkimoru.gadgetCraft.itemLib

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

@Suppress("unused")
class BukkitFunctions(val plugin: JavaPlugin) { // bukkitの遅延処理、マジックナンバーの除去のために作成
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

object DisplayFunctions {
	fun intToRoman(num: Int): String {
		val romanNumerals = listOf(
			1000 to "M", 900 to "CM", 500 to "D", 400 to "CD",
			100 to "C", 90 to "XC", 50 to "L", 40 to "XL",
			10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I"
		)
		var number = num
		val result = StringBuilder()
		for ((value, symbol) in romanNumerals) {
			while (number >= value) {
				result.append(symbol)
				number -= value
			}
		}
		return result.toString()
	}

	fun tickToTime(tick: Int): String {
		val second = tick / 20
		val minute = second / 60
		val hour = minute / 60
		return if(hour > 0) "${hour % 24}:${minute % 60}:${second % 60}"
		else{
			"${minute % 60}:${second % 60}"
		}
	}

	data class RarityInfo(val name: String, val section: String)

	fun getInfo(rarity: String): RarityInfo {
		return when (rarity.lowercase(Locale.getDefault())) {
			"common" -> RarityInfo("§f§lコモン", "§f")
			"uncommon" -> RarityInfo("§a§lアンコモン", "§a")
			"rare" -> RarityInfo("§9§lレア", "§9")
			"epic" -> RarityInfo("§5§lエピック", "§5")
			"legendary" -> RarityInfo("§6§lレジェンド", "§6")
			"mythic" -> RarityInfo("§d§lミシック", "§d")
			else -> RarityInfo("§4§k**§r§4§lNULL§r§4§k**", "§4")
		}
	}
}