package me.khanh.plugins.extrastorageform.forms;

import lombok.Getter;

import java.util.Optional;

public enum FormType {
    MODAL_FORM("MODAL"),
    SIMPLE_FORM("SIMPLE"),
    CUSTOM_FORM("CUSTOM")
    ;

    @Getter
    private final String name;

    FormType(String name) {
        this.name = name;
    }

    public boolean isValid(String s){
        for (FormType type : FormType.values()){
            if (name.equalsIgnoreCase(type.getName())){
                return true;
            }
        }
        return false;
    }

    public Optional<FormType> parseType(String s){
        for (FormType type : FormType.values()){
            if (name.equalsIgnoreCase(type.getName())){
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

}
