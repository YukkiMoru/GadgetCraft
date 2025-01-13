package com.github.yukkimoru.gadgetCraft.commands

import com.github.yukkimoru.gadgetCraft.itemLib.ItemFactory
import com.github.yukkimoru.gadgetCraft.commands.gui.Interface
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
        if (sender is Player) {
            if (args.isEmpty()) {
                sender.sendMessage("GadgetCraftのコマンドが使用可能です")
                return false
            }
            when (args[0]) {
                "gui" -> {
                    when (args[1]) {
                        "util" -> {
                            sender.sendMessage("Utility GUI opened")
                            val inventory = Interface(plugin).shopPickaxe(sender)
                            sender.openInventory(inventory)
                        }
                        "pickaxe" -> {
                            // pickaxeの処理
                        }
                        else -> sender.sendMessage("無効な引数です")
                    }
                }
                "test" -> {
                    //get gadgetCraftID
                    val itemFactory = ItemFactory()
                    val itemID = itemFactory.getMainHandItemGadgetCraftID(sender)
                    sender.sendMessage("GadgetCraftID: $itemID")
                }
                else -> sender.sendMessage("無効な引数です")
            }
        } else {
            sender.sendMessage("このコマンドはプレイヤーのみが使用できます")
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
                    "gui" -> listOf("util", "pickaxe", "potion", "weapon")
                    else -> emptyList()
                }
                else -> emptyList()
            }
        } else null
    }
}