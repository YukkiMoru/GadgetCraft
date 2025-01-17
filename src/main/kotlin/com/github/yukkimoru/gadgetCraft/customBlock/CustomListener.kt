package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.isMechanicOwner
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.removeMechanics
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDB.setMechanics
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
			player.sendMessage("特定の文字が入力されました！")
			setMechanics(player.name, "shop", player.world.name.toString(), location.blockX, location.blockY, location.blockZ)
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
				}
				removeMechanics(player.name, "shop", player.world.name.toString(), location.blockX, location.blockY, location.blockZ)
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
						player.sendMessage("オーナーではないので編集ができません")
						event.isCancelled = true
						sign.update()
					}
				}
			}
		}
	}
}