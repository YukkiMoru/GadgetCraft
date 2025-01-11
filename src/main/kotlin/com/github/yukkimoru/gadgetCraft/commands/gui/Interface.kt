package com.github.yukkimoru.gadgetCraft.commands.gui

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

@Suppress("SpellCheckingInspection")
object Interface {
	private lateinit var plugin: JavaPlugin

	fun initialize(plugin: JavaPlugin) {
		this.plugin = plugin
	}

	fun shopPickaxe(): Inventory {
		val inventorySize = 36
		val gui = createInventory("ツルハシの商人", inventorySize)

//		val toolFactory = ToolFactory(plugin)
//
//		gui.setItem(10, toolFactory.createPickaxe(200, true))
//		gui.setItem(11, toolFactory.createPickaxe(201, true))
//		gui.setItem(12, toolFactory.createPickaxe(202, true))
//
//		gui.setItem(19, toolFactory.createPickaxe(300, true))
//		gui.setItem(20, toolFactory.createPickaxe(301, true))

		addFrames(gui, Material.BLACK_STAINED_GLASS_PANE, inventorySize)

		return gui
	}

	private fun createInventory(name: String, size: Int): Inventory {
		return Bukkit.createInventory(null, size, name)
	}

	private fun createItem(material: Material, amount: Int, displayName: String, color: ChatColor?): ItemStack {
		val item = ItemStack(material, amount)
		val meta: ItemMeta? = item.itemMeta
		meta?.setDisplayName(color?.toString() + displayName)
		item.itemMeta = meta
		return item
	}

	private fun addFrames(gui: Inventory, material: Material, size: Int) {
		for (i in 0..8) {
			gui.setItem(i, createItem(material, 1, "", ChatColor.BLACK))
		}
		var effectiveColumn = size / 9 - 2
		var row = 9
		while (effectiveColumn > 0) {
			gui.setItem(row, createItem(material, 1, "", ChatColor.BLACK))
			gui.setItem(row + 8, createItem(material, 1, "", ChatColor.BLACK))
			effectiveColumn--
			row += 9
		}

		for (i in row..row + 8) {
			gui.setItem(i, createItem(material, 1, "", ChatColor.BLACK))
		}
	}

	private fun addTFrame(gui: Inventory, material: Material, size: Int) {
		for (i in 0..8) {
			gui.setItem(i, createItem(material, 1, "", ChatColor.BLACK))
		}
		val slots = listOf(13, 22, 31, 40, 49)
		for (slot in slots) {
			gui.setItem(slot, createItem(material, 1, "", ChatColor.BLACK))
		}
	}
}