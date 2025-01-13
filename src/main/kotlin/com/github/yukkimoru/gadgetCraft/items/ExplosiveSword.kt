package com.github.yukkimoru.gadgetCraft.items

import com.github.yukkimoru.gadgetCraft.itemLib.Functions
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.plugin.java.JavaPlugin

class ExplosiveSword(private val plugin: JavaPlugin) : Listener {
	private val functions = Functions(plugin)
	private var isHoldItem:Boolean = false
	private var isCooldown:Boolean = false
	private val debugMode:Boolean = true

	private val itemName = "爆発剣"
	private val cooldown:Long = 1000L // 1000L = 1 seconds
	private val customModelData = 1
	private val gadgetCraftID = 1

	@EventHandler
	fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
		val player = event.damager
		val entity = event.entity
		if (isHoldItem && !isCooldown) {
			if (debugMode) player.sendMessage("§a${itemName}が実行されました")
			isCooldown = true
			player.velocity = player.location.direction.multiply(-1)
			entity.world.createExplosion(entity.location, 2.0f, false, false)
			functions.delayTick(cooldown / 50) {
				if (debugMode) player.sendMessage("§a${itemName}が使用可能")
				isCooldown = false
				player.world.playSound(player.location, "entity.allay.hurt", 0.05f, 0.1f)
			}
		} else {
			if (debugMode) player.sendMessage("§c${itemName}の特殊能力のクールダウン中です")
		}
	}

	@EventHandler
	fun onPlayerItemHeld(event: PlayerItemHeldEvent) {
		val player = event.player
		functions.delayTick(1L) {
			isHoldItem = true
			if (isHoldItem) {
				if (debugMode) player.sendMessage("§a${itemName}を装備しました")
				isCooldown = true
				functions.delayTick(cooldown / 50) {
					if (debugMode) player.sendMessage("§a${itemName}の特殊能力が使用可能")
					player.world.playSound(player.location, "entity.allay.hurt", 0.05f, 0.1f)
					isCooldown = false
				}
			} else {
				if (debugMode) player.sendMessage("§c${itemName}の特殊能力が無効化されました")
				isCooldown = true
			}
		}
	}
}