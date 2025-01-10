package com.github.yukkimoru.gadgetCraft.items

import com.github.yukkimoru.gadgetCraft.itemLib.Functions

import org.bukkit.GameMode
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action.RIGHT_CLICK_AIR
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import org.bukkit.plugin.java.JavaPlugin

class DoubleJumper(plugin: JavaPlugin) : Listener {
    private val functions = Functions(plugin)
    private var isWearArmor: Boolean = false
    private var isCooldown: Boolean = false

    private val itemName = "2段ジャンプ"
    private val customModelID = 301
    private val cooldown: Long = 3000L // 1000L = 1 seconds
    private val debugMode: Boolean = true


    @EventHandler
    fun onPlayerToggleFlight(event: PlayerToggleFlightEvent) {
        val player = event.player
        if (isWearArmor && !isCooldown) {
            if (debugMode) player.sendMessage("§a${itemName}が実行されました")
            isCooldown = true
            if (player.gameMode == GameMode.CREATIVE) return // クリエ時の無効化
            event.isCancelled = true
            player.allowFlight = false
            player.velocity = player.location.direction.multiply(1.0).setY(1)
            functions.delayTick(cooldown / 50) {
                if (isWearArmor) {
                    if (debugMode) player.sendMessage("§a${itemName}が使用可能")
                    isCooldown = false
                    player.allowFlight = true
                    player.world.playSound(player.location, "entity.wither.shoot", 0.05f, 0.1f)
                }
            }
        } else {
            if (debugMode) player.sendMessage("§c${itemName}の特殊能力のクールダウン中です")
        }
    }

	@EventHandler
	fun onInventoryClick(event: InventoryClickEvent) {
		val player = event.whoClicked as? Player ?: return
		if(debugMode)player.sendMessage("§a[onInventoryClick]slotType:${event.slotType}, slot:${event.slot}")
		if (event.slotType == InventoryType.SlotType.ARMOR
            && functions.convEquipToID("BOOTS") == event.slot) {
            isWearArmor = functions.isWearingEquip(player, Functions.Equip.BOOTS, customModelID)
			functions.delayTick(1L) {
//                getAction(player, isWearArmor)
			}
		}
	}
	@EventHandler
	fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action == RIGHT_CLICK_AIR
            || event.action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK){
            val player = event.player
//            if (debugMode) player.sendMessage("§a[onPlayerInteract]${itemName}が右クリックされました")
            functions.delayTick(1L) {
                isWearArmor = functions.isWearingEquip(player, Functions.Equip.BOOTS, customModelID)
                if (isWearArmor) {
                    if (debugMode) player.sendMessage("§a${itemName}を装備しました")
                    isCooldown = true
                    player.allowFlight = false
                    functions.delayTick(cooldown / 50) {
                        if (debugMode) player.sendMessage("§a${itemName}が可能!")
                        player.getAttribute(Attribute.SAFE_FALL_DISTANCE)?.baseValue = 8.0
                        player.world.playSound(player.location, "entity.wither.shoot", 0.05f, 0.1f)
                        player.allowFlight = true
                        isCooldown = false
                    }
                } else {
                    if (debugMode) player.sendMessage("§c${itemName}を外しました")
                    isCooldown = true
                    player.allowFlight = false
                    player.getAttribute(Attribute.SAFE_FALL_DISTANCE)?.baseValue = 3.0
                }
            }
        }
	}
}