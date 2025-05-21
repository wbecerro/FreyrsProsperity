package wbe.freyrsProsperity.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wbe.freyrsProsperity.FreyrsProsperity;

public class PlayerJoinListeners implements Listener {

    private FreyrsProsperity plugin;

    public PlayerJoinListeners() {
        this.plugin = FreyrsProsperity.getInstance();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void openBlessingOnInteract(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for(NamespacedKey key : FreyrsProsperity.recipeLoader.keys) {
            player.discoverRecipe(key);
        }
    }
}
