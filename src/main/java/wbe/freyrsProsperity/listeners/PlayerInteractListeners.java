package wbe.freyrsProsperity.listeners;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import wbe.freyrsProsperity.FreyrsProsperity;
import wbe.freyrsProsperity.config.blessings.Blessing;
import wbe.freyrsProsperity.config.blessings.Reward;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class PlayerInteractListeners implements Listener {

    private FreyrsProsperity plugin;

    private static boolean opening = false;

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

        if(opening) {
            return;
        }

        Location location = event.getClickedBlock().getLocation();
        Random random = new Random();

        for(Map.Entry<Location, Blessing> entry : FreyrsProsperity.activeBlessings.entrySet()) {
            if(FreyrsProsperity.utilities.isLocationEquals(entry.getKey(), location)) {
                // Es una bendición
                opening = true;
                ArrayList<Reward> rewards = entry.getValue().getRandomRewards();
                long delay = 0;
                Bukkit.broadcastMessage(FreyrsProsperity.messages.openBlessing
                        .replace("%player%", event.getPlayer().getName())
                        .replace("%blessing%", entry.getValue().getName()));
                for(Reward reward : rewards) {
                    ItemStack item = reward.getItem();
                    item.setAmount(random.nextInt(reward.getMin(), reward.getMax() + 1));

                    // Spawnear cada item por separado con un poco de delay (10 ticks / 0,5s por defecto)
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Item droppedItem = location.getWorld().dropItem(location.clone().add(0.5, 1, 0.5), reward.getItem());
                            NamespacedKey key = new NamespacedKey(plugin, "blessingItem");
                            droppedItem.getPersistentDataContainer().set(key, PersistentDataType.STRING, event.getPlayer().getName());
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    droppedItem.getPersistentDataContainer().remove(key);
                                }
                            }.runTaskLaterAsynchronously(plugin, FreyrsProsperity.config.itemProtection * 20L);

                            // Lanzar el objeto desde la bendición
                            double vectorX = random.nextDouble() * (0.2 - (-0.2)) - 0.2;
                            double vectorZ = random.nextDouble() * (0.2 - (-0.2)) - 0.2;
                            Vector vector = new Vector(vectorX, 0.4, vectorZ);
                            droppedItem.setVelocity(vector);
                            location.getWorld().playSound(location, Sound.BLOCK_TRIAL_SPAWNER_SPAWN_ITEM, 1F, 0.5F);

                            // Mostrar las partículas mientras el objeto no esté en el suelo o no lo cojan
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if(!droppedItem.isValid() || droppedItem.isDead() ||droppedItem.isOnGround()) {
                                        this.cancel();
                                    } else {
                                        location.getWorld().spawnParticle(reward.getParticle(), droppedItem.getLocation(), 1);
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 2L);
                        }
                    }, delay);
                    delay += 10;
                }

                // Hacer desaparacer la bendición al soltar todos los objetos
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        FreyrsProsperity.utilities.removeBlessing(entry.getKey());
                        location.getWorld().playSound(location, entry.getValue().getSound(), 1F, 1F);
                        opening = false;
                    }
                }, delay + 10);
                event.setCancelled(true);
                return;
            }
        }
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
