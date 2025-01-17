package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.economy.EconomyDB.purchase
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.isMechanicOwner
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.removeMechanic
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.setMechanic
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.text.lines
import kotlin.toString

class CustomListener : Listener {

	@EventHandler
	fun onSignChange(event: SignChangeEvent) {
		val player = event.player
		val lines = event.lines
		val location = event.block.location

		if (lines[0].equals("shop", ignoreCase = true)) {
			if (lines[1].isEmpty() || lines[2].isEmpty()) {
				player.sendMessage("2行目はアイテム名と3行目は値段")
				return
			}
			val itemName = lines[1].toString()
			val price = lines[2].toIntOrNull()
			if (price == null) {
				player.sendMessage("3行目には数値を入力してください")
				return
			}
			if (0 > price) {
				player.sendMessage("3行目には0以上の数値を入力してください")
				return
			}
			player.sendMessage("アイテム名:" + itemName + "," + "値段:" + price + "のショップを作りました")
			setMechanic(player.name, "shop", player.world.name.toString(), location.blockX, location.blockY, location.blockZ)

			// Get the block the sign is attached to
			val attachedBlock = event.block.getRelative((event.block.state.data as org.bukkit.material.Sign).attachedFace)
			player.sendMessage("看板が付いているブロック: ${attachedBlock.type}")

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
					if(isMechanicOwner(player.name, "shop", player.world.name.toString(), location.blockX.toInt(), location.blockY.toInt(), location.blockZ.toInt())){
						player.sendMessage("オーナーなので編集が可能です")
//						player.sendMessage(player.name+" "+player.world.name.toString()+" "+location.blockX.toInt()+" "+location.blockY.toInt()+" "+location.blockZ.toInt())
					}else{
						player.sendMessage()
						event.isCancelled = true

						val line2 = lines[2].toIntOrNull()
						if (line2 == null) {
							player.sendMessage("3行目には数値を入力してください")
							event.isCancelled = true
							return
						}
						if(0 > line2){
							player.sendMessage("3行目には0以上の数値を入力してください")
							event.isCancelled = true
							return
						}
						player.sendMessage("アイテム名:"+lines[1]+","+"値段:"+line2+"で買いました")
						purchase(player.name, line2)
					}
				}
			}
		}
	}
}