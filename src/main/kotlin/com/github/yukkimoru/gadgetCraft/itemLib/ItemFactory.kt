package com.github.yukkimoru.gadgetCraft.itemLib

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

class ItemFactory(private val plugin: Plugin) {

	fun createItemStack(
		material: Material,
		amount: Int,
		name: String,
		lore: List<String>,
		rarity: String,
		customModelID: Int? = null
	): ItemStack {
		val itemStack = ItemStack(material, amount)
		val itemMeta = itemStack.itemMeta
		itemMeta?.setDisplayName(name)
		if (itemMeta != null) {
			itemMeta.lore = lore + RarityUtil.getInfo(rarity).section
		}

		// Add rarity to the item
		val key = NamespacedKey(plugin, "rarity")
		itemMeta?.persistentDataContainer?.set(key, PersistentDataType.STRING, rarity)

		// Add custom model data if provided
		customModelID?.let {
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