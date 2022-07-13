package moe.xmcn.catsero.events.commands;

import moe.xmcn.catsero.Updater;
import moe.xmcn.catsero.events.gists.HelpList;
import moe.xmcn.catsero.events.gists.PingHost;
import moe.xmcn.catsero.events.gists.WeatherUtils;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Objects;

public class CatSero implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1) {
        /*
          PingHost
         */
            if (args[0].equalsIgnoreCase("ping") && Config.UsesConfig.getBoolean("pinghost.enabled")) {
                if (Config.UsesConfig.getBoolean("pinghost.op-only")) {
                    if (sender.hasPermission("catsero.admin")) {
                        if (args.length == 2) {
                            try {
                                sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.doing"))));
                                String result = PingHost.PingHostUtils(args[1]);
                                if (Objects.equals(result, "Error")) {
                                    sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.error"))));
                                } else {
                                    long flag = Long.parseLong(result);
                                    String message = Config.tryToPAPI(sender, Config.getMsgByMsID("minecraft.pinghost.success")
                                            .replace("%address_original%", args[1])
                                            .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                            .replace("%withdraw%", String.valueOf(flag))
                                            .replace("%lost%", String.valueOf(4 - flag))
                                            .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4)));
                                    //sender.sendMessage(args[1] + "(" + (Punycode.encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                                    sender.sendMessage(message);
                                }
                            } catch (UnknownHostException e) {
                                sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.failed"))));
                            }
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&c请键入正确的地址"));
                            return false;
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.no-permission")));
                        return false;
                    }
                } else if (args.length == 2) {
                    try {
                        sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.doing"))));
                        String result = PingHost.PingHostUtils(args[1]);
                        if (Objects.equals(result, "Error")) {
                            sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.error"))));
                        } else {
                            long flag = Long.parseLong(result);
                            String message = Config.tryToPAPI(sender, Config.getMsgByMsID("minecraft.pinghost.success")
                                    .replace("%address_original%", args[1])
                                    .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                    .replace("%withdraw%", String.valueOf(flag))
                                    .replace("%lost%", String.valueOf(4 - flag))
                                    .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4)));
                            //sender.sendMessage(args[1] + "(" + (Punycode.encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            sender.sendMessage(message);
                        }
                    } catch (UnknownHostException e) {
                        sender.sendMessage(Config.tryToPAPI(sender, ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.pinghost.failed"))));
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&c请键入正确的地址"));
                    return false;
                }

        /*
         天气获取
        */
            } else if (args[0].equalsIgnoreCase("weather") && Config.UsesConfig.getBoolean("weatherinfo.enabled")) {
                if (args.length == 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.doing")));
                    try {
                        String[] resvi = WeatherUtils.getWeather(args[1]);
                        String message = Config.getMsgByMsID("minecraft.weatherinfo.success")
                                .replace("%type%", resvi[4])
                                .replace("%temperature%", resvi[1])
                                .replace("%wind%", resvi[2])
                                .replace("%wind_direction%", resvi[3])
                                .replace("%date%", resvi[0]);
                        sender.sendMessage(message);
                        //sender.sendMessage("天气信息:\n 类型:" + resvi[4] + "\n 温度:" + resvi[1] + "\n 风力:" + resvi[2] + "\n 风向:" + resvi[3] + "\n 日期:" + resvi[0]);
                    } catch (UnsupportedEncodingException uee) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.error")));
                        return false;
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.weatherinfo.null-city")));
                    return false;
                }

        /*
         Punycode
         */
            } else if (args[0].equalsIgnoreCase("punycode")) {
                if (args.length > 2 && args[2].equals("urlmode")) {
                    sender.sendMessage(Punycode.encodeURL(args[1]));
                } else {
                    sender.sendMessage(Punycode.encode(args[1]));
                }

        /*
         插件重载
        */
            } else if (args[0].equalsIgnoreCase("reload")) {
                Config.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&a配置文件已重载"));
                return true;
        /*
         手动检查更新
        */
            } else if (args[0].equalsIgnoreCase("update")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&a开始检查更新..."));
                sender.sendMessage(Updater.startUpdateCheck());

        /*
         可能你没启用 (XD
         */
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(HelpList.Companion.getList("mc"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.undefined-usage")));
                return false;
            }
        } else {
        /*
          其他
         */
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "This server is running CatSero v" + Config.PluginInfo.getString("version") + "By" + Config.PluginInfo.getString("author")));
            return true;
        }
        return false;
    }
}
