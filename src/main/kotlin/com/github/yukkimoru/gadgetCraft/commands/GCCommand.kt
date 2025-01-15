package com.github.yukkimoru.gadgetCraft.commands

import com.github.yukkimoru.gadgetCraft.Economy.EconomyManager
import com.github.yukkimoru.gadgetCraft.itemLib.ItemFactory
import com.github.yukkimoru.gadgetCraft.commands.gui.GUISender.shopArmor
import com.github.yukkimoru.gadgetCraft.commands.gui.GUISender.shopPickaxe
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class GCCommand(plugin: JavaPlugin) : CommandExecutor, TabCompleter {

    init {
        plugin.getCommand("gc")?.apply {
            setExecutor(this@GCCommand)
            tabCompleter = this@GCCommand
        }
    }
    private val itemFactory = ItemFactory

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("このコマンドはプレイヤーのみが使用できます")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("GadgetCraftのコマンドが使用可能です")
            return false
        }

        when (args[0].lowercase()) {
            "gui" -> handleGuiCommand(sender, args)
            "id" -> handleIdCommand(sender)
            "price" -> handlePriceCommand(sender)
            "eco" -> {
                handleEconomyCommand(sender, args)
            }
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
                    "gui" -> listOf("util")
                    "ID" -> emptyList()
                    "Price" -> emptyList()
                    else -> emptyList()
                }
                else -> emptyList()
            }
        } else null
    }

    private fun handleGuiCommand(sender: Player, args: Array<String>) {
        if (args.size < 2) {
            sender.sendMessage("無効な引数です")
            return
        }

        when (args[1].lowercase()) {
            "util" -> {
                sender.sendMessage("ユーティリティGUIを開きました")
                val inventory = shopPickaxe(sender)
                sender.openInventory(inventory)
            }
            "armor" -> {
                sender.sendMessage("防具GUIを開きました")
                val inventory = shopArmor(sender)
                sender.openInventory(inventory)
            }
            else -> sender.sendMessage("無効な引数です")
        }
    }

    private fun handleIdCommand(sender: Player) {
        val itemID = itemFactory.getMainHandItemGadgetCraftID(sender)
        sender.sendMessage("GadgetCraftID: $itemID")
    }

    private fun handlePriceCommand(sender: Player) {
        val price = itemFactory.getPriceThruHand(sender)
        sender.sendMessage("Price: $price")
    }

    private fun handleEconomyCommand(sender: Player, args: Array<String>) {
        if (args.size < 2) {
            sender.sendMessage("無効な引数です")
            return
        }

        val senderUUID = getTargetUUID(sender)

        when (args[1].lowercase()) {
            "balance" -> {
                val balance = EconomyManager.getBalance(senderUUID)
                sender.sendMessage("あなたの所持金は${balance}です。")
            }
            "set" -> {
                if(args.size < 3) {
                    sender.sendMessage("/gc eco set <金額>")
                    return
                }
                EconomyManager.setBalance(senderUUID, args[2].toDouble())
                val balance = EconomyManager.getBalance(senderUUID)
                sender.sendMessage("あなたの所持金は${balance}です。")
            }
            else -> sender.sendMessage("無効な引数です")
        }
    }

    private fun getTargetUUID(player: Player): UUID {
        return player.uniqueId
    }
}