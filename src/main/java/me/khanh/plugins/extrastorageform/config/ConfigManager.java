package me.khanh.plugins.extrastorageform.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;
import me.khanh.plugins.extrastorageform.config.forms.MainFormConfig;
import me.khanh.plugins.extrastorageform.config.forms.StorageFormConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigManager {
    public final ExtraStorageForm plugin;

    @Getter
    private final YamlDocument mainConfig;

    @Getter
    private final Path formsDir;

    @Getter
    private final MainFormConfig mainFormConfig;
    @Getter
    private final StorageFormConfig storageFormConfig;


    public ConfigManager(ExtraStorageForm plugin) throws IOException {
        this.plugin = plugin;

        mainConfig = YamlDocument.create(
                new File(plugin.getDataFolder(), "config.yml"),
                plugin.getResource("config.yml"),
                GeneralSettings.builder().setUseDefaults(false).build(),
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder().setVersioning(new BasicVersioning("ConfigVersion")).build());

        formsDir = Paths.get(plugin.getDataFolder().getAbsolutePath(), "forms");
        if (Files.notExists(formsDir)) {
            Files.createDirectories(formsDir);
        }

        mainFormConfig = new MainFormConfig(this);

        storageFormConfig = new StorageFormConfig(this);

    }
}
