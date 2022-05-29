package moe.xmcn.catsero.events.listeners.OPPlayerQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.events.gists.PlayerUUID
import moe.xmcn.catsero.utils.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.permissions.ServerOperator

class OnGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessage(event: MiraiGroupMessageEvent) {
        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "setop" && Config.UsesConfig.getBoolean("qop-player.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            if (event.senderID == Config.QQ_OP) {
                val pl = PlayerUUID.getUUIDByName(args[2])
                val plname: ServerOperator = Bukkit.getPlayer(pl)
                val isOp = plname.isOp
                if (isOp) {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Config.Prefix_QQ + "已经是管理员了！")
                    } catch (nse: NoSuchElementException) {
                        println(Config.getMsgByMsID("general.send-message-qq-error").replace("%error%", nse.toString() + nse.stackTrace))
                    }
                } else {
                    plname.isOp = true
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Config.Prefix_QQ + "已添加新的管理员")
                    } catch (nse: NoSuchElementException) {
                        println(Config.getMsgByMsID("general.send-message-qq-error").replace("%error%", nse.toString() + nse.stackTrace))
                    }
                }
            }
        }
    }
}