package me.khanh.plugins.extrastorageform.config.forms;


import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.config.ConfigManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFormConfig {
    @Getter
    private final YamlDocument yamlDocument;
    protected ConfigManager manager;

    public MainFormConfig(ConfigManager manager) throws IOException {
        this.manager = manager;
        Path formPath = Paths.get(manager.getFormsDir().toString(), "MainForm.yml");
        if (Files.notExists(formPath)) {
            manager.plugin.saveResource("forms/MainForm.yml", false);
        }
        yamlDocument = YamlDocument.create(formPath.toFile());
    }

}
