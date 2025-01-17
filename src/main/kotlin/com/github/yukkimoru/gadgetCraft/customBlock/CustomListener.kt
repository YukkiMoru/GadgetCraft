package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.economy.EconomyDB.purchase
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.isMechanicOwner
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.removeMechanic
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.setMechanic
import com.github.yukkimoru.gadgetCraft.economy.EconomyDB.sale
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.PlayerInteractEvent

class CustomListener : Listener {



	@EventHandler
	fun onSignChange(event: SignChangeEvent) {
		val player = event.player
		val lines = event.lines
		val location = event.block.location

		if (lines[0].equals("shop", ignoreCase = true)) {
			for(i in 0..3){
				if(lines[0].isEmpty()) {
					if (i == 0) {
						player.sendMessage("1行目にはshopを入力してください")
					}
					if (i == 1) {
						player.sendMessage("2行目にはアイテム名を入力してください")
					}
					if (i == 2) {
						player.sendMessage("3行目には値段を入力してください")
					}
					if (i == 3) {
						player.sendMessage("4行目にはbuyまたはsellを入力してください")
					}
				return
				}
			}
			val itemName = lines[1].toString()
			val price = lines[2].toIntOrNull()
			val buyOrSell = lines[3].toString()
			if (price == null) {
				player.sendMessage("3行目には数値を入力してください")
				return
			}
			if(0 > price){
				player.sendMessage("3行目には0以上の数値を入力してください")
				return
			}
			when (buyOrSell) {
				"buy" -> player.sendMessage("アイテム名:$itemName, 値段:$price の購入ショップを作りました")
				"sell" -> player.sendMessage("アイテム名:$itemName, 値段:$price の売却ショップを作りました")
				else -> {
					player.sendMessage("4行目にはbuyまたはsellを入力してください")
					return
				}
			}
			setMechanic(player.name, "shop", player.world.name, location.blockX, location.blockY, location.blockZ)
		}
	}

	@EventHandler
	fun onBlockBreak(event: BlockBreakEvent) {
		val block = event.block
		if (block.state is Sign) {
			val sign = block.state as Sign
			val player = event.player
			val lines = sign.lines
			val location = block.location
			if (lines[0].equals("shop", ignoreCase = true)) {
				if(isMechanicOwner(player.name, "shop", player.world.name.toString(), location.blockX, location.blockY, location.blockZ) || player.isOp) {
					player.sendMessage("特定の文字が入力された看板を破壊しました")
				} else {
					player.sendMessage("看板を破壊する権限がありません")
					event.isCancelled = true
					sign.update()
				}
				removeMechanic(player.name, "shop", player.world.name.toString(), location.blockX, location.blockY, location.blockZ)
			}
		}
	}

	@EventHandler
	fun onSignRightClick(event: PlayerInteractEvent) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			val block = event.clickedBlock
			if (block != null && block.state is Sign) {
				val player = event.player
				val location = block.location
				val sign = block.state as Sign
				val lines = sign.lines
				if (lines[0].equals("shop", ignoreCase = true)) {
					if(isMechanicOwner(player.name, "shop",
							player.world.name, location.blockX, location.blockY, location.blockZ
						)){
						player.sendMessage("オーナーなので編集が可能です")
					}else{
						player.sendMessage()
						event.isCancelled = true

						val itemName = lines[1].toString()
						val price = lines[2].toIntOrNull()
						val buyOrSell = lines[3].toString()
						when (buyOrSell) {
							"buy" -> {
								player.sendMessage("アイテム名:$itemName, 値段:$price の購入ショップを作りました")
								if(price != null){
									purchase(player.name, price)
								}
							}
							"sell" -> {
								player.sendMessage("アイテム名:$itemName, 値段:$price の売却ショップを作りました")
								if(price != null){
									sale(player.name, price)
								}
							}
							else -> {
								player.sendMessage("4行目にはbuyまたはsellを入力してください")
								return
							}
						}

					}
				}
			}
		}
	}
}