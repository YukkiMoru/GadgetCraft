package com.github.yukkimoru.gadgetCraft.commands.gui

import com.github.yukkimoru.gadgetCraft.GadgetCraft
import com.github.yukkimoru.gadgetCraft.itemLib.ItemFactory
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin

@Suppress("SpellCheckingInspection")
class Interface(private val plugin: Plugin) {

	fun shopPickaxe(sender: Player): Inventory {
		val inventorySize = 9 * 3
		val gui = Bukkit.createInventory(sender, inventorySize, "§bメニュー")

		gui.setItem(10, createItem(Material.RECOVERY_COMPASS, 1, "エンダーパック", ChatColor.GREEN))
		gui.setItem(11, createItem(Material.IRON_BOOTS, 1, "２段ジャンプブーツ", ChatColor.AQUA))
		gui.setItem(12, createItem(Material.NETHERITE_SWORD, 1, "爆発剣", ChatColor.RED))
		gui.setItem(13, createItem(Material.DIAMOND_PICKAXE, 1, "採掘ツール", ChatColor.LIGHT_PURPLE))

		gui.setItem(14, ItemFactory().createItemStack(Material.RED_DYE, 1, "§r採掘ツール", listOf("§r採掘ツールの説明"), "1000", 1, 2))

		addFrames(gui, Material.BLACK_STAINED_GLASS_PANE, inventorySize)

		GadgetCraft.guiMap[sender.uniqueId] = gui
		return gui
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