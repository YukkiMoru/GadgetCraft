package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDatabase.isMechanicOwner
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDatabase.removeMechanics
import com.github.yukkimoru.gadgetCraft.customBlock.MechanicDatabase.setMechanics
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
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

			setMechanics(player.uniqueId, "shop", player.world.toString(), location.x, location.y, location.z)
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
				if(isMechanicOwner(player.uniqueId, "shop", player.world.toString(), location.x, location.y, location.z) || player.isOp) {
					player.sendMessage("特定の文字が入力された看板を破壊しました")
				} else {
					player.sendMessage("看板を破壊する権限がありません")
					event.isCancelled = true
				}
				removeMechanics(player.uniqueId, "shop", player.world.toString(), location.x, location.y, location.z)
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
				if(isMechanicOwner(player.uniqueId, "shop", player.world.toString(), location.x, location.y, location.z)){
					event.isCancelled = true
					player.sendMessage("別のオーナーがいるため、看板を編集する権限がありません")
				}
			}
		}
	}

	@EventHandler
	fun onBlockPlace(event: BlockPlaceEvent) {
		val player = event.player
		val block = event.block
	}
}