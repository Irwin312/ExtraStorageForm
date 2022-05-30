package me.khanh.plugins.extrastorageform.listeners;

import me.khanh.plugins.extrastorageform.ExtraStorageForm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.geysermc.floodgate.api.FloodgateApi;

public class PlayerCommandPreprocessListener implements Listener {
    protected final ExtraStorageForm plugin;

    public PlayerCommandPreprocessListener(ExtraStorageForm plugin){
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent event){
        String command = event.getMessage().substring(1);
        Player player = event.getPlayer();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())){
            return;
        }
        if (plugin.getSettings().COMMANDS.contains(command)){
            event.setMessage("/extrastorageform:extrastorageform open");
        }
    }
}
