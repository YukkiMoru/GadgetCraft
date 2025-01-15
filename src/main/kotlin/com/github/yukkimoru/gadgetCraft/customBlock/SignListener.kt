package com.github.yukkimoru.gadgetCraft.customBlock

import com.github.yukkimoru.gadgetCraft.BlockManager.BlockManager.setMechanicLocation
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.plugin.java.JavaPlugin

class SignListener(private val plugin: JavaPlugin) : Listener {

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