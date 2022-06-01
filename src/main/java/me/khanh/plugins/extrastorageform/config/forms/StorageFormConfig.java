package me.khanh.plugins.extrastorageform.config.forms;

import com.hyronic.exstorage.gui.base.SortType;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.config.ConfigManager;
import me.khanh.plugins.extrastorageform.utils.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageFormConfig {
    @Getter
    private final YamlDocument yamlDocument;
    protected ConfigManager manager;

    @Getter
    private final boolean SORT_ENABLE;
    @Getter
    private SortType SORT_DEFAULT_TYPE;
    @Getter
    private String SORT_TEXT;

    public StorageFormConfig(ConfigManager manager) throws IOException {
        this.manager = manager;
        Path formPath = Paths.get(manager.getFormsDir().toString(), "StorageForm.yml");
        if (Files.notExists(formPath)) {
            manager.plugin.saveResource("forms/StorageForm.yml", false);
        }
        yamlDocument = YamlDocument.create(formPath.toFile());
        yamlDocument.setGeneralSettings(GeneralSettings.builder().setUseDefaults(false).build());

        SORT_ENABLE = yamlDocument.getBoolean("Sort.Enable");

        if (SORT_ENABLE) {
            Section sortSection = yamlDocument.getSection("Sort");
            if (!sortSection.getOptionalString("DefaultType").isPresent()) {
                Logger.warnConfig(sortSection, "DefaultType", "is null", "NAME");
                SORT_DEFAULT_TYPE = SortType.NONE;
            } else {
                String defaultType = sortSection.getString("DefaultType");
                try {
                    SORT_DEFAULT_TYPE = SortType.valueOf(defaultType.toUpperCase());
                } catch (IllegalArgumentException e) {
                    Logger.warnConfig(sortSection, "DefaultType", "unknown type", "NAME");
                }
                if (!sortSection.getOptionalStringList("Text").isPresent()) {
                    Logger.warnConfig(sortSection, "Text", "is null", "''");
                    SORT_TEXT = "";
                } else {
                    StringBuilder textBuilder = new StringBuilder();
                    for (String s : sortSection.getStringList("Text")) {
                        textBuilder.append(s).append("\n");
                    }
                    SORT_TEXT = textBuilder.toString();
                }
            }
        }
    }
}
