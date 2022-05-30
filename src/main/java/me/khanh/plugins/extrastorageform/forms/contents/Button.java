package me.khanh.plugins.extrastorageform.forms.contents;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.utils.Logger;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.util.FormImage;

public class Button {

    @Getter
    private final Section section;

    @Getter
    private final String text;

    @Getter
    private ButtonIcon icon;

    public Button(Section section){
        this.section = section;

        if (section.get("Text") == null){
            Logger.warnConfig(section, "Text", "is null", "''");
            text = "";
        } else {
            text = section.getString("Text");
        }

        if (section.get("Icon") != null){
            icon = new ButtonIcon(section.getSection("Icon"));
            if (!icon.isLoad()){
                icon = null;
            }
        }
    }

    public ButtonComponent build(Player player){
        if (icon == null){
            return ButtonComponent.of(Logger.placeholder(player, text));
        } else {
            if (icon.getType().equals(ButtonIcon.IconType.URL)){
                return ButtonComponent.of(Logger.placeholder(player, text), FormImage.Type.URL, Logger.placeholder(player, icon.getValue()));
            } else {
                return ButtonComponent.of(Logger.placeholder(player, text), FormImage.Type.PATH, Logger.placeholder(player, icon.getValue()));
            }
        }
    }
}
