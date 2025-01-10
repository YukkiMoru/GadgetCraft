package com.github.YukkiMoru.gadgetCraft.items

import com.github.YukkiMoru.gadgetCraft.itemLib.Functions
import org.bukkit.GameMode
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerToggleFlightEvent
import org.bukkit.plugin.java.JavaPlugin

class DoubleJumper(private val plugin: JavaPlugin) : Listener {

	private val cooldown = 3000L // 3 seconds
	private var wearArmor: Boolean = false
	private val debugMode = false
	private val wearCooldown = 3000L // 3 seconds

	@EventHandler
	fun onPlayerToggleFlight(event: PlayerToggleFlightEvent) {
		if (wearArmor) {
			val player = event.player
			if (player.gameMode == GameMode.CREATIVE) return // クリエ時の無効化

			event.isCancelled = true
			player.allowFlight = false
			player.velocity = player.location.direction.multiply(1.0).setY(1)

			val functions = Functions(plugin)
			functions.delayTick(cooldown / 50) {
				if (wearArmor) {
					player.allowFlight = true
					player.world.playSound(player.location, "entity.wither.shoot", 0.05f, 0.1f)
					if (debugMode) player.sendMessage("§a2段ジャンプが可能!")
				}
			}
		}
	}

	@EventHandler
	fun onInventoryClick(event: InventoryClickEvent) {
		val player = event.whoClicked as? Player ?: return
		if (event.slotType == InventoryType.SlotType.ARMOR) {
			val functions = Functions(plugin)
			functions.delayTick(1L) {
				wearArmor = functions.isWearingEquip(player, Functions.Equip.BOOTS, 301)
				if (wearArmor) {
					if (debugMode) player.sendMessage("§a2段ジャンプ装備を装備しました")
					functions.runWithCooldown(wearCooldown / 50) {
						if (debugMode) player.sendMessage("§a2段ジャンプが可能!")
						player.allowFlight = true
						player.getAttribute(Attribute.SAFE_FALL_DISTANCE)?.baseValue = 8.0
						player.world.playSound(player.location, "entity.wither.shoot", 0.05f, 0.1f)
					}
				} else {
					if (debugMode) player.sendMessage("§c2段ジャンプ装備を外しました")
					player.allowFlight = false
					player.getAttribute(Attribute.SAFE_FALL_DISTANCE)?.baseValue = 3.0
				}
			}
		}
	}
}