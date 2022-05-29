package me.khanh.plugins.extrastorageform.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.Arrays;

@CommandAlias("extrastorageform|esf")
public class StorageCommand extends BaseCommand {

    @Default
    @HelpCommand
    @SuppressWarnings("unused")
    public static void onDefaultCommand(CommandSender sender, CommandHelp help){
        ExtraStorageForm plugin = ExtraStorageForm.getPlugin(ExtraStorageForm.class);
        if (sender.hasPermission("esf.help")){
            plugin.getYamlConfig().getStringList("HelpCommand").forEach(s -> sender.sendMessage(Logger.color(s)));
        } else {
            sender.sendMessage(Logger.color(plugin.getYamlConfig().getString("Messages.NoPermission")));
        }
    }

    @Subcommand("reload")
    @SuppressWarnings("unused")
    public static void onReload(CommandSender sender, String[] args){
        ExtraStorageForm plugin = ExtraStorageForm.getPlugin(ExtraStorageForm.class);
        if (!sender.hasPermission("esf.reload")){
            sender.sendMessage(Logger.color(plugin.getYamlConfig().getString("Messages.NoPermission")));
        } else {
            ;
        }
    }

    @Subcommand("open")
    @SuppressWarnings("unused")
    public static void onOpen(CommandSender sender, String[] args){
        ExtraStorageForm plugin = ExtraStorageForm.getPlugin(ExtraStorageForm.class);
        if (args.length == 0){
            if (!sender.hasPermission("esf.open") && !sender.hasPermission("esf.open.other")){
                sender.sendMessage(Logger.color(plugin.getYamlConfig().getString("Messages.NoPermission")));
            } else {
                if (!(sender instanceof Player)){
                    sender.sendMessage(Logger.color(plugin.getYamlConfig().getString("Messages.OnlyPlayer")));
                } else {
                    plugin.getManager().getMainForm().open(FloodgateApi.getInstance().getPlayer(((Player) sender).getUniqueId()));
                }
            }
        } else {
            ;
        }
    }
}
