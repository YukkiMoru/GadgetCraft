package com.github.yukkimoru.gadgetCraft.customBlock

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.block.Sign

class SignBreakListener : Listener {

	@EventHandler
	fun onBlockBreak(event: BlockBreakEvent) {
		val block = event.block
		if (block.state is Sign) {
			val sign = block.state as Sign
			val player = event.player
			val lines = sign.lines
			if (lines[0].equals("economy", ignoreCase = true)) {
				player.sendMessage("特定の文字が含まれた看板が撤去されました！")

			}
		}
	}
}