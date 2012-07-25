package com.p000ison.dev.allpermissions;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Adds all permissions to a file
 *
 * @author p000ison
 */
public class AllPermissions extends JavaPlugin
{

    private static Permission perms = null;

    @Override
    public void onDisable()
    {
    }

    @Override
    public void onEnable()
    {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            setupPermissions();
        }
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("allperm")) {
            if (sender.hasPermission("allperms")) {
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("group")) {
                        for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
                            for (org.bukkit.permissions.Permission permission : plugin.getDescription().getPermissions()) {
                                if (!perms.groupHas(args[1], args[2], permission.getName())) {
                                    perms.groupAdd(args[1], args[2], permission.getName());
                                }
                            }
                        }
                        sender.sendMessage(commandLabel);
                    } else if (args[0].equalsIgnoreCase("player")) {
                        for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
                            for (org.bukkit.permissions.Permission permission : plugin.getDescription().getPermissions()) {
                                if (!perms.has(sender, permission.getName())) {
                                    perms.playerAdd(args[1], args[2], permission.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
