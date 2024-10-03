package org.nanshang.deathTotem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class DeathTotem extends JavaPlugin {

    public static DeathTotem instance;

    @Override
    public void onEnable() {
        instance = this;

        /*检查前置插件是否安装*/
        if (!getServer().getPluginManager().isPluginEnabled("Oraxen")) {
            getServer().getConsoleSender().sendMessage("Oraxen 插件未安装！请先安装ItemsAdder插件！本插件即将关闭！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        /*初始化配置文件*/
        ConfigManager.loadConfig();

        /*注册监听器*/
        getServer().getPluginManager().registerEvents(new DeathListener(), this);

        /*注册指令*/
        registerCommands();

        instance.getLogger().info("DeathTotem 插件已启动！");

    }

    /*Reload指令*/
    private void registerCommands() {
        instance.getServer().getCommandMap().register("DeathTotem", new ReloadCommand("dt"));
    }
    public static class ReloadCommand extends Command {
        protected ReloadCommand(@NotNull String name) {
            super(name);
        }
        @Override
        public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
            if (strings.length!= 0 && strings[0].equals("reload")) {
                if (commandSender instanceof Player){
                    Player player = (Player) commandSender;
                    if (player.hasPermission("deathtotem.reload")){
                        instance.reloadConfig();
                        ConfigManager.loadConfig();
                        player.sendMessage("配置文件重载成功！");
                        return true;
                    } else {
                        player.sendMessage("你没有权限执行此命令！");
                        return true;
                    }
                }
                if (commandSender instanceof ConsoleCommandSender){
                    instance.reloadConfig();
                    ConfigManager.loadConfig();
                    instance.getLogger().info("配置文件重载成功！");
                    return true;
                }
            }
            return false;
        }
    }
}