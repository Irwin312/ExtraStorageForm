package me.khanh.plugins.extrastorageform.forms;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public class StorageForm {

    @Getter
    private final Section section;

    @Getter
    private final String title;
    @Getter
    private String text;

    public StorageForm(Section section) {
        this.section = section;

        if (section.get("Title") == null) {
            Logger.warnConfig(section, "Title", "is null", "''");
            title = "";
        } else {
            title = section.getString("Title");
        }

        if (section.get("Text") == null) {
            Logger.warnConfig(section, "Title", "is null", "''");
            text = "";
        } else {
            for (String s : section.getStringList("Text")) {
                text = s + "\n";
            }
        }
    }

    public void open(FloodgatePlayer player) {

    }
}
