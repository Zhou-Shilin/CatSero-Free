package moe.xmcn.catsero.events.listeners.KickPlayerQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.events.gists.PlayerUUID
import moe.xmcn.catsero.utils.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {

        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "kick" && Config.UsesConfig.getBoolean("qkick-player.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            if (event.senderID == Config.QQ_OP) {
                val pl = PlayerUUID.getUUIDByName(args[2])
                try {
                    Bukkit.getPlayer(pl).kickPlayer(Config.UsesConfig.getString("qkick-player"))
                } finally {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.qkick-player.error"))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                }
            } else {
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                        .sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"))
                } catch (nse: NoSuchElementException) {
                    Config.plugin.logger.warning(
                        Config.getMsgByMsID("general.send-message-qq-error")
                            .replace("%error%", nse.toString() + nse.stackTrace)
                    )
                }
            }
        }
    }
}