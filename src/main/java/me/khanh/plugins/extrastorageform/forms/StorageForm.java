package me.khanh.plugins.extrastorageform.forms;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;

public class StorageForm {

    @Getter
    private Section section;

    public StorageForm(Section section) {
        this.section = section;

    }
}
