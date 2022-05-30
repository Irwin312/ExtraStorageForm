package me.khanh.plugins.extrastorageform;

import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;

import java.util.List;

public class Settings {
    @Getter
    private final YamlDocument config;

    public final List<String> COMMANDS;

    public final List<String> HELP_COMMAND;

    public final String MESSAGE_NO_PERMISSION;
    public final String MESSAGE_ONLY_PLAYER;
    public final String MESSAGE_ONLY_BEDROCK_PLAYER;
    public final String MESSAGE_NO_ONLINE;


    public Settings(ExtraStorageForm plugin){
        config = plugin.getYamlConfig();
        COMMANDS = config.getStringList("StorageCommands");
        HELP_COMMAND = config.getStringList("HelpCommand");

        MESSAGE_NO_PERMISSION = config.getString("Messages.NoPermission");
        MESSAGE_ONLY_PLAYER = config.getString("Messages.OnlyPlayer");
        MESSAGE_ONLY_BEDROCK_PLAYER = config.getString("Messages.OnlyBedrockPlayer");
        MESSAGE_NO_ONLINE = config.getString("Messages.NoOnline");
    }
}
