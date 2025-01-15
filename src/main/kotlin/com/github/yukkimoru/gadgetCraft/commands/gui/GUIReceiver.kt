package com.github.yukkimoru.gadgetCraft.commands.gui

import com.github.yukkimoru.gadgetCraft.GadgetCraft
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class GUIReceiver : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(GadgetCraft.guiMap[event.whoClicked.uniqueId] == event.inventory) {
            if (event.view.title == "§bアーマーショップ") {
                handleArmorShopGUI(event)
            } else if (event.view.title == "§bメニュー") {
                handlePickaxeShopGUI(event)
            }
        }
    }

    private fun handlePickaxeShopGUI(event: InventoryClickEvent) {
//        event.isCancelled = true
        when (event.slot) {
            10 -> purchasePickaxe(event, 200)
            11 -> purchasePickaxe(event, 201)
            12 -> purchasePickaxe(event, 202)
            19 -> purchasePickaxe(event, 300)
            20 -> purchasePickaxe(event, 301)
        }
    }

    private fun handleArmorShopGUI(event: InventoryClickEvent) {
        event.isCancelled = true
        when (event.slot) {
            9 -> purchaseArmor(event, 100)
            10 -> purchaseArmor(event, 101)
            11 -> purchaseArmor(event, 102)
            12 -> purchaseArmor(event, 103)
        }
    }

    private fun purchasePickaxe(event: InventoryClickEvent, customModelID: Int) {
        val world = Bukkit.getWorld("world")
        val player = event.whoClicked as Player
        val playerInventory = player.inventory
//        val costMaterial = toolFactory.pickaxes[customModelID]?.pickaxeCosts ?: emptyMap()
        if (isInventoryFull(playerInventory)) {
            player.sendMessage("Your inventory is full!")
            world?.playSound(player.location, "entity.enderman.teleport", 1.2f, 0.1f)
            return
        }
//        val hasAllMaterials = costMaterial.all { (material: Material, amount: Int) ->
//            playerInventory.all(material).values.sumOf { it.amount } >= amount
//        }
//        if (hasAllMaterials) {
//            costMaterial.forEach { (material: Material, amount: Int) ->
//                playerInventory.removeItem(ItemStack(material, amount))
//            }
//            playerInventory.addItem(toolFactory.createPickaxe(customModelID, false))
//            world?.playSound(player.location, "minecraft:block.note_block.pling", 1.2f, 2.0f)
//        } else {
//            world?.playSound(player.location, "entity.enderman.teleport", 1.2f, 0.1f)
//            player.sendMessage("You do not have the required materials!")
//        }
        world?.playSound(player.location, "minecraft:block.note_block.pling", 1.2f, 2.0f)
    }

    private fun purchaseArmor(event: InventoryClickEvent, customModelID: Int) {
        val world = Bukkit.getWorld("world")
        val player = event.whoClicked as Player
        player.sendMessage("You purchased armor!")
        val playerInventory = player.inventory
        if (isInventoryFull(playerInventory)) {
            player.sendMessage("Your inventory is full!")
            world?.playSound(player.location, "entity.enderman.teleport", 1.2f, 0.1f)
            return
        }
        world?.playSound(player.location, "minecraft:block.note_block.pling", 1.2f, 2.0f)
    }

    private fun isInventoryFull(playerInventory: Inventory): Boolean {
        return playerInventory.firstEmpty() == -1
    }
}