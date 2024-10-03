package org.nanshang.deathTotem;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

    public static YamlConfiguration tempConfig;

    private ConfigManager() {
    }


    // 保存配置文件
    public static void loadConfig() {
        DeathTotem.instance.getConfig().options().copyDefaults();
        DeathTotem.instance.saveDefaultConfig();
        tempConfig = (YamlConfiguration) DeathTotem.instance.getConfig();
    }


}