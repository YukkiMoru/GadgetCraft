package com.github.yukkimoru.gadgetCraft.itemLib

import org.bukkit.Material

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.Plugin

class ItemFactory(private val plugin: Plugin) {

	fun createItemStack(
		material: Material,
		amount: Int,
		name: String,
		lore: List<String>,
		rarity: String,
		customModelData: Int? = null
	): ItemStack {
		val itemStack = ItemStack(material, amount)
		val itemMeta = itemStack.itemMeta
		itemMeta?.setDisplayName(name)


		itemMeta?.setDisplayName(name)
		if (itemMeta != null) {
			itemMeta.lore = lore + RarityUtil.getInfo(rarity).section
		}

		// Add custom model data if provided
		customModelData?.let {
			itemMeta?.setCustomModelData(it)
		}



		itemStack.itemMeta = itemMeta
		return itemStack
	}

	// カスタムモデルデータ(ID)を取得、ない場合はnullを返す
	fun getCustomModelData(item: ItemStack): Int? {
		val meta: ItemMeta = item.itemMeta ?: return null
		return if (meta.hasCustomModelData()) meta.customModelData else null
	}

	fun isItemWithCustomModelData(item: ItemStack, modelData: Int): Boolean {
		return getCustomModelData(item) == modelData
	}
}