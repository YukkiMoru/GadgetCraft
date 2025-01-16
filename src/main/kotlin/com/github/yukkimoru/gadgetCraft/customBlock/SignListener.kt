package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.customBlock.BlockManager.setMechanicLocation
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.SignChangeEvent

class SignListener : Listener {

	@EventHandler
	fun onSignChange(event: SignChangeEvent) {
		val player = event.player
		val lines = event.lines

		// 一行目に特定の文字が含まれているかチェック
		if (lines[0].equals("economy", ignoreCase = true)) {
			// イベントを発生させる処理
			player.sendMessage("特定の文字が入力されました！")
			// ここに他のイベント処理を追加
			setMechanicLocation(player.uniqueId, player.location.toString(), "economy")
		}
	}
}

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