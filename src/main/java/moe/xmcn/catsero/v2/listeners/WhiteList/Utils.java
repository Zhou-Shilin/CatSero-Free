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
package moe.xmcn.catsero.v2.listeners.WhiteList;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Loggers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public interface Utils {

    String X_Bot = Configs.JPConfig.uses_config.getString("whitelist.var.bot");
    String X_Group = Configs.JPConfig.uses_config.getString("whitelist.var.group");

    static FileConfiguration getWhiteList() {
        try {
            File wlf = Configs.getConfig("extra-configs/whitelist.yml");

            if (!wlf.exists()) {
                if (!wlf.createNewFile()) {
                    throw new IOException("Could not create extra-configs/whitelist.yml");
                }
            }
            return YamlConfiguration.loadConfiguration(wlf);
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
        return null;
    }

}