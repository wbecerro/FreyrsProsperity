package wbe.freyrsProsperity.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;
import wbe.freyrsProsperity.FreyrsProsperity;

public class PickupItemListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelInventoryPickup(InventoryPickupItemEvent event) {
        NamespacedKey key = new NamespacedKey(FreyrsProsperity.getInstance(), "blessingItem");
        if(event.getItem().getPersistentDataContainer().has(key)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelEntityPickup(EntityPickupItemEvent event) {
        NamespacedKey key = new NamespacedKey(FreyrsProsperity.getInstance(), "blessingItem");
        if(!event.getItem().getPersistentDataContainer().has(key)) {
            return;
        }

        LivingEntity entity = event.getEntity();
        if(!entity.getType().equals(EntityType.PLAYER)) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) entity;
        if(!event.getItem().getPersistentDataContainer().get(key, PersistentDataType.STRING).equals(player.getName())) {
            event.setCancelled(true);
        }
    }
}
