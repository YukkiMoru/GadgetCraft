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

@Suppress("SpellCheckingInspection")
object GUISender {

	fun shopPickaxe(sender: Player): Inventory {
		val inventorySize = 9 * 6
		val gui = Bukkit.createInventory(sender, inventorySize, "§bメニュー")

		gui.setItem(10, createItem(Material.RECOVERY_COMPASS, 1, "エンダーパック", ChatColor.GREEN))
		gui.setItem(11, createItem(Material.IRON_BOOTS, 1, "２段ジャンプブーツ", ChatColor.AQUA))
		gui.setItem(12, createItem(Material.NETHERITE_SWORD, 1, "爆発剣", ChatColor.RED))
		gui.setItem(13, createItem(Material.DIAMOND_PICKAXE, 1, "採掘ツール", ChatColor.LIGHT_PURPLE))

		gui.setItem(14, ItemFactory.createItemStack(1, true))

		addFrames(gui, Material.BLACK_STAINED_GLASS_PANE, inventorySize)

		GadgetCraft.guiMap[sender.uniqueId] = gui
		return gui
	}

	fun shopArmor(sender: Player): Inventory {
		val inventorySize = 9 * 3
		val gui = Bukkit.createInventory(sender, inventorySize, "§bアーマーショップ")

		gui.setItem(9, createItem(Material.DIAMOND_HELMET, 1, "ダイヤモンドヘルメット", ChatColor.BLUE))
		gui.setItem(10, createItem(Material.DIAMOND_CHESTPLATE, 1, "ダイヤモンドチェストプレート", ChatColor.BLUE))
		gui.setItem(11, createItem(Material.DIAMOND_LEGGINGS, 1, "ダイヤモンドレギンス", ChatColor.BLUE))
		gui.setItem(12, createItem(Material.DIAMOND_BOOTS, 1, "ダイヤモンドブーツ", ChatColor.BLUE))

		addFramesT(gui, Material.BLACK_STAINED_GLASS_PANE, inventorySize)

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

	private fun addFramesT(gui: Inventory, material: Material, size: Int) {
		for (i in 0..8) {
			gui.setItem(i, createItem(material, 1, "", ChatColor.BLACK))
		}
		if(size==27){
			val slots = listOf(13, 22)
			for (slot in slots) {
				gui.setItem(slot, createItem(material, 1, "", ChatColor.BLACK))
			}
		}
		if(size==36){
			val slots = listOf(13, 22, 31)
			for (slot in slots) {
				gui.setItem(slot, createItem(material, 1, "", ChatColor.BLACK))
			}
		}
		if(size==45){
			val slots = listOf(13, 22, 31, 40)
			for (slot in slots) {
				gui.setItem(slot, createItem(material, 1, "", ChatColor.BLACK))
			}
		}
		if(size==54){
			val slots = listOf(13, 22, 31, 40, 49)
			for (slot in slots) {
				gui.setItem(slot, createItem(material, 1, "", ChatColor.BLACK))
			}
		}
	}
}