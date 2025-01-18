package com.github.yukkimoru.gadgetCraft.items

import com.github.yukkimoru.gadgetCraft.itemLib.BukkitFunctions
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.plugin.java.JavaPlugin
import com.github.yukkimoru.gadgetCraft.itemLib.ItemFactory.getGadgetCraftItemName
import com.github.yukkimoru.gadgetCraft.itemLib.ItemFactory.hasMainHandItemGadgetCraftID

class ExplosiveSword private constructor(plugin: JavaPlugin) : Listener {
	companion object {
		private var instance: ExplosiveSword? = null

		fun getInstance(plugin: JavaPlugin): ExplosiveSword {
			if (instance == null) {
				instance = ExplosiveSword(plugin)
			}
			return instance!!
		}
	}

	private val functions = BukkitFunctions(plugin)
	private var isHoldItem: Boolean = false
	private var isCooldown: Boolean = false
	private val debugMode: Boolean = false

	private val cooldown: Long = 20L // 20 ticks = 1 second
	private val gadgetCraftID = 1

	// gadgetCraftIDからアイテムデータの取得
	private val itemName = getGadgetCraftItemName(gadgetCraftID)

	@EventHandler
	fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
		val player = event.damager
		val entity = event.entity
		if (isHoldItem && !isCooldown) {
			if (debugMode) player.sendMessage("§a${itemName}が実行されました")
			isCooldown = true
			player.velocity = player.location.direction.multiply(-1)
			entity.world.createExplosion(entity.location, 2.0f, false, false)
			functions.delayTick(cooldown) {
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
			isHoldItem = hasMainHandItemGadgetCraftID(player, gadgetCraftID)
			if (debugMode) player.sendMessage("§a${itemName}を装備しています: $isHoldItem")
			if (isHoldItem) {
				if (debugMode) player.sendMessage("§a${itemName}を装備しました")
				isCooldown = true
				functions.delayTick(cooldown) {
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