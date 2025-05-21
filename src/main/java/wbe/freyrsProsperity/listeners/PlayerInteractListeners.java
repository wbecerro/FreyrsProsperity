package wbe.freyrsProsperity.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wbe.freyrsProsperity.FreyrsProsperity;
import wbe.freyrsProsperity.config.blessings.Blessing;
import wbe.freyrsProsperity.config.blessings.Reward;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class PlayerInteractListeners implements Listener {

    private FreyrsProsperity plugin;

    public PlayerInteractListeners() {
        this.plugin = FreyrsProsperity.getInstance();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void openBlessingOnInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                return;
            }
        }

        Player player = event.getPlayer();
        Location location = event.getClickedBlock().getLocation();
        Random random = new Random();

        for(Map.Entry<Location, Blessing> entry : FreyrsProsperity.activeBlessings.entrySet()) {
            if(FreyrsProsperity.utilities.isLocationEquals(entry.getKey(), location)) {
                // Es una bendición
                ArrayList<Reward> rewards = entry.getValue().getRandomRewards();
                for(Reward reward : rewards) {
                    ItemStack item = reward.getItem();
                    item.setAmount(random.nextInt(reward.getMax() - reward.getMin()) + reward.getMin());
                    location.getWorld().dropItem(location.add(0, 1, 0), reward.getItem());
                }
                FreyrsProsperity.utilities.removeBlessing(entry.getKey());
                event.setCancelled(true);
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void useArtifactOnInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                return;
            }
        }

        ItemStack item = event.getItem();
        if(item == null) {
            return;
        }

        if(!item.getType().equals(FreyrsProsperity.config.artifactMaterial)) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey key = new NamespacedKey(plugin, "MimirsArtifact");
        if(!meta.getPersistentDataContainer().has(key)) {
            return;
        }

        Player player = event.getPlayer();
        World world = player.getWorld();
        Location blessing = FreyrsProsperity.utilities.getWorldBlessing(world);
        if(blessing == null) {
            player.setCompassTarget(player.getWorld().getSpawnLocation());
            player.sendMessage(FreyrsProsperity.messages.compassFail);
            event.setCancelled(true);
            return;
        }

        player.sendMessage(FreyrsProsperity.messages.compassSuccess);
        player.setCompassTarget(blessing);

        event.setCancelled(true);
    }
}
