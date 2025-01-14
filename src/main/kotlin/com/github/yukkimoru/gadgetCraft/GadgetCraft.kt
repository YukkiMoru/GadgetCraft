package com.github.yukkimoru.gadgetCraft

import org.bukkit.plugin.java.JavaPlugin
//import com.github.yukkimoru.gadgetCraft.items.DoubleJumper
import com.github.yukkimoru.gadgetCraft.items.ExplosiveSword
import com.github.yukkimoru.gadgetCraft.commands.GCCommand
import com.github.yukkimoru.gadgetCraft.commands.gui.GUIReceiver
import com.github.yukkimoru.gadgetCraft.customBlock.SignBreakListener
import com.github.yukkimoru.gadgetCraft.customBlock.SignListener
import org.bukkit.inventory.Inventory
import java.util.*

class GadgetCraft : JavaPlugin() {

    companion object{
        val guiMap: MutableMap<UUID, Inventory> = mutableMapOf()
    }

    override fun onEnable() {
        // プラグインのスタートロジック

        // イベントリスナーの登録
//        server.pluginManager.registerEvents(DoubleJumper(this), this)
//        server.pluginManager.registerEvents(EnderPack(this), this)
        server.pluginManager.registerEvents(ExplosiveSword.getInstance(this), this)

        // コマンドの登録
        GCCommand(this)

        // GUIの登録
        server.pluginManager.registerEvents(GUIReceiver(), this)

        server.pluginManager.registerEvents(SignListener(this), this)
        server.pluginManager.registerEvents(SignBreakListener(this), this)
    }

    override fun onDisable() {
        // プラグインのシャットダウンロジック
    }
}
