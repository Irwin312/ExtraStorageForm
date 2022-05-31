package me.khanh.plugins.extrastorageform.utils;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Logger {
    private static final String VERSION = Bukkit.getBukkitVersion();
    private static final String PREFIX_NON_RGB = "&bExtraStorageForm";
    private static final String PREFIX_RGB = "&x&9&f&9&8&e&8E&x&a&0&9&e&e&6x&x&a&1&a&5&e&5t&x&a&2&a&b&e&3r&x&a&3&b&1&e&1a&x&a&4&b&7&e&0S&x&a&5&b&e&d&et&x&a&6&c&4&d&co&x&a&8&c&a&d&br&x&a&9&d&0&d&9a&x&a&a&d&7&d&7g&x&a&b&d&d&d&6e&x&a&c&e&3&d&4F&x&a&d&e&9&d&2o&x&a&e&f&0&d&1r&x&a&f&f&6&c&fm";
    private static final String PREFIX = Integer.parseInt(VERSION.split("-")[0].split("\\.")[0]) > 1 ? PREFIX_RGB : Integer.parseInt(VERSION.split("-")[0].split("\\.")[1]) >= 16 ? PREFIX_RGB : PREFIX_NON_RGB;
    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void info(String s) {
        Bukkit.getConsoleSender().sendMessage(color("&f[&b" + PREFIX + "&f] &r" + s));
    }

    public static String placeholder(Player player, String s) {
        return PlaceholderAPI.setPlaceholders(player, color(s));
    }

    public static void warnConfig(@NotNull Section section, @NotNull String key, @NotNull String reason, @Nullable String defaultValue) {
        String fileName = Objects.requireNonNull(section.getRoot().getFile()).getName();
        if (section.getRouteAsString() == null) {
            if (defaultValue == null) {
                info(String.format("&eWARN: &6%s &e>> &c%s %s", fileName, key, reason));
            } else {
                info(String.format("&eWARN: &6%s &e>> &c%s %s. &dUsing default value: %s", fileName, key, reason, defaultValue));
            }
        } else {
            if (defaultValue == null) {
                info(String.format("&eWARN: &6%s &e>> &c%s.%s %s", fileName, section.getRouteAsString(), key, reason));
            } else {
                info(String.format("&eWARN: &6%s &e>> &c%s.%s %s. &dUsing default value: %s", fileName, section.getRouteAsString(), key, reason, defaultValue));
            }
        }
    }
}
