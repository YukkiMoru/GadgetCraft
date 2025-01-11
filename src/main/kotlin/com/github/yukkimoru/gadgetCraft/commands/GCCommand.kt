package com.github.yukkimoru.gadgetCraft.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin

class GCCommand(private val plugin: JavaPlugin) : CommandExecutor, TabCompleter {

    init {
        plugin.getCommand("gc")?.apply {
            setExecutor(this@GCCommand)
            tabCompleter = this@GCCommand
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        // Command logic here
        sender.sendMessage("Command executed!")
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String>? {
        // Tab completion logic here
        return listOf("option1", "option2", "option3")
    }
}