package me.khanh.plugins.extrastorageform.forms;

import lombok.Getter;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;

public class FormManager {
    private final ExtraStorageForm plugin;

    @Getter
    private MainForm mainForm;

    public FormManager(ExtraStorageForm plugin){
        this.plugin = plugin;
        mainForm = new MainForm(plugin.getYamlConfig().getSection("Forms.MainForm"));
    }

}
