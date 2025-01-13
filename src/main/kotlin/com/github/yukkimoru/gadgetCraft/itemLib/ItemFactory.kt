package com.github.yukkimoru.gadgetCraft.itemLib

import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemFactory {
	data class Item(
		val material: Material,
		val name: String,
		val lore: List<String>,
		val rarity: String = "NULL",
		val customModelData: Int? = null,
		val price: Int? = null,
	)

	val Gadgets = mapOf(
		1 to Item(
			material = Material.DIAMOND_SWORD,
			name = "爆発剣",
			lore = listOf("強力な爆発を引き起こす剣"),
			rarity = "rare",
			customModelData = 1,
			price = 100,
		),
		2 to Item(
			material = Material.IRON_BOOTS,
			name = "2段ジャンプブーツ",
			lore = listOf("2段ジャンプブーツ"),
			rarity = "common",
			customModelData = 1,
			price = 100,
		)
	)

	fun createItemStack(
		gadgetCraftID: Int,
		displayMode: Boolean,
	): ItemStack {
		// get item data using gadgetCraftID
		val itemData = Gadgets[gadgetCraftID] ?: return ItemStack(Material.AIR)

		val itemStack = ItemStack(itemData.material, 1) // Assuming amount is 1
		val itemMeta = itemStack.itemMeta
		itemMeta?.setDisplayName("§r${RarityUtil.getInfo(itemData.rarity).section}§l${itemData.name}")

		itemMeta?.let {
			it.lore = itemData.lore + RarityUtil.getInfo(itemData.rarity).name
		}

		// Add custom model data if provide
		itemData.customModelData?.let {
			itemMeta?.setCustomModelData(it)
		}

		// Set the item meta
		itemStack.itemMeta = itemMeta

		// Add gadgetCraftID as NBT tag
		val nbtItem = NBTItem(itemStack)
		nbtItem.setInteger("gadgetCraftID", gadgetCraftID)
		nbtItem.setInteger("price", Gadgets[gadgetCraftID]?.price)

		return nbtItem.item
	}

	fun getMainHandItemGadgetCraftID(player: Player): Int? {
		val itemInMainHand = player.inventory.itemInMainHand
		val nbtItem = NBTItem(itemInMainHand)
		return if (nbtItem.hasKey("gadgetCraftID")) nbtItem.getInteger("gadgetCraftID") else null
	}

	fun hasMainHandItemGadgetCraftID(player: Player, gadgetCraftID: Int): Boolean {
		val itemInMainHand = player.inventory.itemInMainHand
		if(itemInMainHand.type == Material.AIR) return false
		val nbtItem = NBTItem(itemInMainHand)
		return nbtItem.hasKey("gadgetCraftID") && nbtItem.getInteger("gadgetCraftID") == gadgetCraftID
	}

	fun getPriceThruHand(player: Player): Int? {
		val itemInMainHand = player.inventory.itemInMainHand
		val nbtItem = NBTItem(itemInMainHand)
		return if (nbtItem.hasKey("price")) nbtItem.getInteger("price") else null
	}

	fun getGadgetCraftItemName(gadgetCraftID: Int): String? {
		return Gadgets[gadgetCraftID]?.name
	}
}