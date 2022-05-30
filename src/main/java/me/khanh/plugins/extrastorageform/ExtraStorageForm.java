package me.khanh.plugins.extrastorageform;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.commands.StorageCommand;
import me.khanh.plugins.extrastorageform.config.ConfigManager;
import me.khanh.plugins.extrastorageform.forms.FormManager;
import me.khanh.plugins.extrastorageform.listeners.PlayerCommandPreprocessListener;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class ExtraStorageForm extends JavaPlugin {
    @Getter
    private PaperCommandManager commandManager;

    @Getter
    private FormManager manager;

    @Getter
    private Settings settings;

    @Getter
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        try {
            configManager = new ConfigManager(this);
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
}
