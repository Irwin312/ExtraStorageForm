package me.khanh.plugins.extrastorageform;

import com.cryptomorin.xseries.XMaterial;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.forms.contents.ButtonIcon;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.Material;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Settings {
    @Getter
    private final YamlDocument config;

    public final List<String> COMMANDS;

    public final List<String> HELP_COMMAND;

    public final String MESSAGE_NO_PERMISSION;
    public final String MESSAGE_ONLY_PLAYER;
    public final String MESSAGE_ONLY_BEDROCK_PLAYER;
    public final String MESSAGE_NO_ONLINE;

    public HashMap<Material, AbstractMap.SimpleEntry<String, ButtonIcon>> FORMAT_NAME = new HashMap<>();


    public Settings(ExtraStorageForm plugin) {
        config = plugin.getConfigManager().getMainConfig();
        COMMANDS = config.getStringList("StorageCommands");
        HELP_COMMAND = config.getStringList("HelpCommand");

        MESSAGE_NO_PERMISSION = config.getString("Messages.NoPermission");
        MESSAGE_ONLY_PLAYER = config.getString("Messages.OnlyPlayer");
        MESSAGE_ONLY_BEDROCK_PLAYER = config.getString("Messages.OnlyBedrockPlayer");
        MESSAGE_NO_ONLINE = config.getString("Messages.NoOnline");

        if (config.get("Format") != null) {
            Section formatSection = config.getSection("Format");
            for (Object obj : formatSection.getKeys()) {
                String key = String.valueOf(obj);
                Optional<XMaterial> optionalXMaterial = XMaterial.matchXMaterial(key);
                if (optionalXMaterial.isPresent()) {
                    Section keySection = formatSection.getSection(key);
                    String name = "";
                    if (keySection.get("Name") == null) {
                        Logger.warnConfig(keySection, "Name", "is null", "''");
                    } else {
                        name = keySection.getString("Name");
                    }
                    ButtonIcon icon = null;
                    if (keySection.get("Icon") != null) {
                        icon = new ButtonIcon(keySection.getSection("Icon"));
                    }
                    AbstractMap.SimpleEntry<String, ButtonIcon> formatEntry = new AbstractMap.SimpleEntry<>(name, icon);
                    FORMAT_NAME.put(optionalXMaterial.get().parseMaterial(), formatEntry);
                }
            }
        }
    }
}
