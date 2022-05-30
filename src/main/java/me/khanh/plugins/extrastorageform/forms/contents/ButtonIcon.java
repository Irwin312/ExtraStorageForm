package me.khanh.plugins.extrastorageform.forms.contents;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import me.khanh.plugins.extrastorageform.utils.Logger;

public class ButtonIcon {
    @Getter
    private final Section section;
    @Getter
    private IconType type;
    @Getter
    private String value;

//    private final String URL_REGEX = "/^(?:http(s)?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#[\\]@!\\$&'\\(\\)\\*\\+,;=.]+$/i";

    @Getter
    private boolean load;

    public ButtonIcon(Section section){
        this.section = section;

        if (section.get("Type") != null){
            if (section.getString("Type").equalsIgnoreCase("url")){
                type = IconType.URL;
            } else {
                if (section.getString("Type").equalsIgnoreCase("path")){
                    type = IconType.PATH;
                } else {
                    Logger.warnConfig(section, "Type", "unknown icon type", null);
                    return;
                }
            }

            if (section.get("Value") != null){
                value = section.getString("Value");
            } else {
                Logger.warnConfig(section, "Value", "is null", null);
                return;
            }
        } else {
            Logger.info(String.format("&eWARN: %s.%s is null", section.getRouteAsString(), "Type"));
            Logger.warnConfig(section, "Type", "is null", null);
            return;
        }

        load = true;
    }

    public enum IconType{
        URL, PATH
    }
}
