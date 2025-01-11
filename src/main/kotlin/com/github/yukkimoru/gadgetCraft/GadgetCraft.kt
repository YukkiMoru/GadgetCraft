package com.github.yukkimoru.gadgetCraft

import org.bukkit.plugin.java.JavaPlugin
import com.github.yukkimoru.gadgetCraft.items.DoubleJumper
import com.github.yukkimoru.gadgetCraft.items.ExplosiveSword
import com.github.yukkimoru.gadgetCraft.commands.GCCommand

class GadgetCraft : JavaPlugin() {

    override fun onEnable() {
        // プラグインのスタートロジック

        // イベントリスナーの登録
        server.pluginManager.registerEvents(DoubleJumper(this), this)
//        server.pluginManager.registerEvents(EnderPack(this), this)
        server.pluginManager.registerEvents(ExplosiveSword(this), this)

        // コマンドの登録
        GCCommand(this)
    }

    override fun onDisable() {
        // プラグインのシャットダウンロジック
    }
}
