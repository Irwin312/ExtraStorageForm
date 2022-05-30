package me.khanh.plugins.extrastorageform.forms;

import lombok.Getter;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;

public class FormManager {
    protected final ExtraStorageForm plugin;

    @Getter
    private final MainForm mainForm;

    public FormManager(ExtraStorageForm plugin){
        this.plugin = plugin;
        mainForm = new MainForm(plugin.getConfigManager().getMainFormConfig().getYamlDocument());
    }

}
