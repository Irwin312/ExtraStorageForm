package me.khanh.plugins.extrastorageform;

import co.aikar.commands.PaperCommandManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.commands.StorageCommand;
import me.khanh.plugins.extrastorageform.forms.FormManager;
import me.khanh.plugins.extrastorageform.listeners.PlayerCommandPreprocessListener;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ExtraStorageForm extends JavaPlugin {

    @Getter
    private YamlDocument yamlConfig;

    @Getter
    private PaperCommandManager commandManager;

    @Getter
    private FormManager manager;

    @Getter
    private Settings settings;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        try {
            loadConfig();
            settings = new Settings(this);
            manager = new FormManager(this);
            commandManager = new PaperCommandManager(this);
            commandManager.registerCommand(new StorageCommand());
            commandManager.enableUnstableAPI("help");
            getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
            Logger.info("&bThe plugin has been loaded successfully. Took &a" + (System.currentTimeMillis() - startTime) + "&a ms");
        } catch (IOException exception){
            exception.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {

    }

    private void loadConfig() throws IOException {
        yamlConfig = YamlDocument.create(
                new File(getDataFolder(), "config.yml"),
                getResource("config.yml"),
                GeneralSettings.builder().setUseDefaults(false).build(),
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder().setVersioning(new BasicVersioning("ConfigVersion")).build());
    }
}
