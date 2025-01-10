package com.github.yukkimoru.gadgetCraft

import org.bukkit.plugin.java.JavaPlugin
import com.github.yukkimoru.gadgetCraft.items.DoubleJumper
import com.github.yukkimoru.gadgetCraft.items.EnderPack
import com.github.yukkimoru.gadgetCraft.items.ExplosiveSword


class GadgetCraft : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic

        server.pluginManager.registerEvents(DoubleJumper(this), this)
//        server.pluginManager.registerEvents(EnderPack(this), this)
        server.pluginManager.registerEvents(ExplosiveSword(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
