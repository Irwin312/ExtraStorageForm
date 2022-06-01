package me.khanh.plugins.extrastorageform.forms;

import com.cryptomorin.xseries.XMaterial;
import com.hyronic.exstorage.api.StorageAPI;
import com.hyronic.exstorage.data.user.Storage;
import com.hyronic.exstorage.data.user.User;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;
import me.khanh.plugins.extrastorageform.Settings;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.impl.SimpleFormImpl;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.Optional;

public class StorageForm {

    @Getter
    private final Section section;

    @Getter
    private final String title;
    @Getter
    private final String text;

    @Getter
    private final String buttonText;

    private final ExtraStorageForm plugin = JavaPlugin.getPlugin(ExtraStorageForm.class);

    public StorageForm(Section section) {
        this.section = section;

        if (section.get("Title") == null) {
            Logger.warnConfig(section, "Title", "is null", "''");
            title = "";
        } else {
            title = section.getString("Title");
        }

        if (!section.getOptionalStringList("Text").isPresent()) {
            Logger.warnConfig(section, "Text", "is null", "''");
            text = "";
        } else {
            StringBuilder textBuilder = new StringBuilder();
            for (String s : section.getStringList("Text")) {
                textBuilder.append(s).append("\n");
            }
            text = textBuilder.toString();
        }

        if (!section.getOptionalString("ButtonText").isPresent()) {
            Logger.warnConfig(section, "ButtonText", "is null", "%material_formatted%: %amount%");
            buttonText = "%material_formatted%: %amount%";
        } else {
            buttonText = section.getString("ButtonText");
        }

    }

    public void open(FloodgatePlayer floodgatePlayer) {
        Player bukkitPlayer = Bukkit.getPlayer(floodgatePlayer.getJavaUniqueId());
        User user = StorageAPI.getInstance().getUser(bukkitPlayer.getUniqueId());
        Storage storage = user.getStorage();

        SimpleForm.Builder simpleBuilder = new SimpleFormImpl.Builder();
        simpleBuilder.title(Logger.placeholder(bukkitPlayer, storagePlaceholder(bukkitPlayer, title)));
        simpleBuilder.content(Logger.placeholder(bukkitPlayer, storagePlaceholder(bukkitPlayer, title)));
        for (String key : storage.getMaterials().keySet()) {
            String[] keyArgs = key.split(":");
            Optional<XMaterial> optionalXMaterial = XMaterial.matchXMaterial(keyArgs[0] + keyArgs[1]);
            if (!optionalXMaterial.isPresent()) {
                Logger.info(String.format("&eWARN: Unknown material with name: %s", keyArgs[0] + keyArgs[1]));
                continue;
            } else {

            }
        }
    }

    public String storagePlaceholder(Player player, String s) {
        User user = StorageAPI.getInstance().getUser(player.getUniqueId());
        Storage storage = user.getStorage();
        return s
                .replace("%used%", String.valueOf(storage.getUsedSpace()))
                .replace("%limit%", String.valueOf(storage.getSpace()))
                ;
    }

    public String materialPlaceholder(Player player, Material material, String s, int amount) {
        User user = StorageAPI.getInstance().getUser(player.getUniqueId());
        Storage storage = user.getStorage();

        Settings settings = plugin.getSettings();
        String result;
        result = s.replace("%amount%", String.valueOf(amount))
                .replace("%material%", material.name());
        if (settings.FORMAT_NAME.containsKey(material)) {
            result = result.replace("%material_formatted%", settings.FORMAT_NAME.get(material).getKey());
        }
        return result;
    }
}
