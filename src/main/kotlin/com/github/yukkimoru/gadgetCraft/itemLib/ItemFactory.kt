package com.github.yukkimoru.gadgetCraft.itemLib

import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemFactory() {

	fun createItemStack(
		material: Material,
		amount: Int,
		name: String,
		lore: List<String>,
		rarity: String,
		customModelData: Int? = null,
		gadgetCraftID: Int
	): ItemStack {
		val itemStack = ItemStack(material, amount)
		val itemMeta = itemStack.itemMeta
		itemMeta?.setDisplayName(name)

		if (itemMeta != null) {
			itemMeta.lore = lore + RarityUtil.getInfo(rarity).section
		}

		// Add custom model data if provided
		customModelData?.let {
			itemMeta?.setCustomModelData(it)
		}

		// Set the item meta
		itemStack.itemMeta = itemMeta

		// Add gadgetCraftID as NBT tag
		val nbtItem = NBTItem(itemStack)
		nbtItem.setInteger("gadgetCraftID", gadgetCraftID)

		return nbtItem.item
	}

	fun getMainHandItemGadgetCraftID(player: Player): Int? {
		val itemInMainHand = player.inventory.itemInMainHand
		val nbtItem = NBTItem(itemInMainHand)
		return if (nbtItem.hasKey("gadgetCraftID")) nbtItem.getInteger("gadgetCraftID") else null
	}
}