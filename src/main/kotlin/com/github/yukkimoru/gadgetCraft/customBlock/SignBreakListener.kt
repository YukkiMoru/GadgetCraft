package com.github.yukkimoru.gadgetCraft.customBlock

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.block.Sign

class SignBreakListener(private val plugin: JavaPlugin) : Listener {

	@EventHandler
	fun onBlockBreak(event: BlockBreakEvent) {
		val block = event.block

		// ブロックが看板かどうかを確認
		if (block.state is Sign) {
			val sign = block.state as Sign
			val player = event.player
			val lines = sign.lines

			// 一行目に特定の文字が含まれているかチェック
			if (lines[0].equals("特定の文字", ignoreCase = true)) {
				// 看板が撤去されたときの処理
				player.sendMessage("特定の文字が含まれた看板が撤去されました！")
				// ここに他のイベント処理を追加
			}
		}
	}
}