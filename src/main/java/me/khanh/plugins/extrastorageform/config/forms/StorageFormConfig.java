package me.khanh.plugins.extrastorageform.config.forms;

import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.config.ConfigManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageFormConfig {
    @Getter
    private final YamlDocument yamlDocument;
    protected ConfigManager manager;

    public StorageFormConfig(ConfigManager manager) throws IOException {
        this.manager = manager;
        Path formPath = Paths.get(manager.getFormsDir().toString(), "StorageForm.yml");
        if (Files.notExists(formPath)) {
            manager.plugin.saveResource("forms/StorageForm.yml", false);
        }
        yamlDocument = YamlDocument.create(formPath.toFile());
    }
}
