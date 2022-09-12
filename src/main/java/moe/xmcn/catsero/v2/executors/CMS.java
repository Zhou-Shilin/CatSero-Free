/*
 * CatSero v2
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
package moe.xmcn.catsero.v2.executors;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CMS implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 1) {
            if (commandSender.hasPermission("catsero.cms")) {
                if (strings.length == 3) {
                    Env.AMiraiMC.sendMiraiGroupMessage(strings[0], strings[1], strings[2]);
                } else {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configs.getMsgByMsID("minecraft.invalid-options")));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 1) {
            List<String> sublist = new ArrayList<>();
            switch (strings.length) {
                case 1:
                    sublist.add("send");
                    return sublist;
                case 2:
                    if ("send".equals(strings[0])) {
                        sublist.add("BotID");
                        return sublist;
                    }
                    return null;
                case 3:
                    if ("send".equals(strings[0])) {
                        sublist.add("GroupID");
                        return sublist;
                    }
                    return null;
            }
        }
        return null;
    }

}