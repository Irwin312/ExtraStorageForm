package me.khanh.plugins.extrastorageform.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;


@CommandAlias("extrastorageform|esf")
public class StorageCommand extends BaseCommand {

    @Default
    @HelpCommand
    @SuppressWarnings("unused")
    public static void onDefaultCommand(CommandSender sender, CommandHelp help){
        ExtraStorageForm plugin = ExtraStorageForm.getPlugin(ExtraStorageForm.class);
        if (sender.hasPermission("esf.help")){
            plugin.getSettings().HELP_COMMAND.forEach(s -> sender.sendMessage(Logger.color(s)));
        } else {
            sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_NO_PERMISSION));
        }
    }

    @Subcommand("reload")
    @SuppressWarnings("unused")
    public static void onReload(CommandSender sender, String[] args){
        ExtraStorageForm plugin = ExtraStorageForm.getPlugin(ExtraStorageForm.class);
        if (!sender.hasPermission("esf.reload")){
            sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_NO_PERMISSION));
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
                sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_NO_PERMISSION));
            } else {
                if (!(sender instanceof Player)){
                    sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_ONLY_PLAYER));
                } else {
                    Player player = (Player) sender;
                    if (!FloodgateApi.getInstance().isFloodgateId(player.getUniqueId())){
                        sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_ONLY_BEDROCK_PLAYER));
                        return;
                    }
                    plugin.getManager().getMainForm().open(FloodgateApi.getInstance().getPlayer(((Player) sender).getUniqueId()));
                }
            }
        } else {
            if (args.length == 1){
                if (!sender.hasPermission("esf.open.other")){
                    sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_NO_PERMISSION));
                } else {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_NO_ONLINE));
                        return;
                    }
                    if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
                        sender.sendMessage(Logger.color(plugin.getSettings().MESSAGE_ONLY_BEDROCK_PLAYER));
                        return;
                    }
                    plugin.getManager().getMainForm().open(FloodgateApi.getInstance().getPlayer(player.getUniqueId()));
                }
            }
        }
    }
}
