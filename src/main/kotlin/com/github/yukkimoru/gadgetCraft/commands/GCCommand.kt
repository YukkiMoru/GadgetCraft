package com.github.yukkimoru.gadgetCraft.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class GCCommand(private val plugin: JavaPlugin) : CommandExecutor, TabCompleter {

    init {
        plugin.getCommand("gc")?.apply {
            setExecutor(this@GCCommand)
            tabCompleter = this@GCCommand
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        sender.sendMessage("Command executed!")
        when(args[0]) {
            "gui" -> sender.sendMessage("gui")
            else -> sender.sendMessage("無効な引数です")
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        return if (sender is Player) {
            when (args.size) {
                1 -> listOf("gui")
                2 -> when (args[0].lowercase()) {
                    "gui" -> listOf("utility", "pickaxe", "potion", "weapon")
                    else -> emptyList()
                }
                else -> emptyList()
            }
        } else null
    }
}