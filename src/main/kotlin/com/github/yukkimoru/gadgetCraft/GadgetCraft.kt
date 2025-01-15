package com.github.yukkimoru.gadgetCraft

import com.github.yukkimoru.gadgetCraft.Economy.Sqlite
import org.bukkit.plugin.java.JavaPlugin
import com.github.yukkimoru.gadgetCraft.items.ExplosiveSword
import com.github.yukkimoru.gadgetCraft.commands.GCCommand
import com.github.yukkimoru.gadgetCraft.commands.gui.GUIReceiver
import com.github.yukkimoru.gadgetCraft.customBlock.SignBreakListener
import com.github.yukkimoru.gadgetCraft.customBlock.SignListener
import org.bukkit.inventory.Inventory
import java.util.*

class GadgetCraft : JavaPlugin() {

    companion object {
        val guiMap: MutableMap<UUID, Inventory> = mutableMapOf()
    }

    override fun onEnable() {
        // データベースの初期化
        Sqlite.connect()

        // プラグインのスタートロジック
        server.pluginManager.registerEvents(ExplosiveSword.getInstance(this), this)
        GCCommand(this)
        server.pluginManager.registerEvents(GUIReceiver(), this)
        server.pluginManager.registerEvents(SignListener(this), this)
        server.pluginManager.registerEvents(SignBreakListener(), this)
    }

    override fun onDisable() {
        // プラグインのシャットダウンロジック
        Sqlite.disconnect()
    }
}