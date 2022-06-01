package me.khanh.plugins.extrastorageform.forms;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.ExtraStorageForm;
import me.khanh.plugins.extrastorageform.forms.contents.Button;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainForm {

    @Getter
    private final Section section;

    @Getter
    private final String title;

    private final ExtraStorageForm plugin = ExtraStorageForm.getPlugin(ExtraStorageForm.class);

    @Getter
    private final boolean load;


    @Getter
    List<Button> buttons = new ArrayList<>();
    @Getter
    private String text;

    public MainForm(Section section) {
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

        Section buttonSection = section.getSection("Contents");
        for (Object obj : buttonSection.getKeys()) {
            String key = String.valueOf(obj);
            String[] arrayOfButton = new String[]{"Storage", "Filter", "Partner", "Sell"};
            if (!Arrays.asList(arrayOfButton).contains(key)) {
                Logger.warnConfig(section, key, "what is this. Please delete this", null);
                continue;
            }
            buttons.add(new Button(buttonSection.getSection(key)));
        }
        load = true;
    }

    public SimpleForm buildForm(FloodgatePlayer floodgatePlayer) {
        Player player = Bukkit.getPlayer(floodgatePlayer.getJavaUniqueId());
        SimpleForm.Builder builder = SimpleForm.builder();
        builder.title(Logger.placeholder(player, title));
        builder.content(Logger.placeholder(player, text));
        for (Button button : buttons) {
            button.build(builder, player);
        }
        builder.responseHandler((simpleForm, s) -> {
            SimpleFormResponse response = simpleForm.parseResponse(s);
            if (!response.isCorrect()) {
                return;
            }
            switch (response.getClickedButtonId()) {
                case 0:
                    plugin.getManager().getStorageForm().open(FloodgateApi.getInstance().getPlayer(player.getUniqueId()));
            }
        });
        return builder.build();
    }

    public void open(FloodgatePlayer player){
        player.sendForm(buildForm(player));
    }

}
