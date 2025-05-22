package wbe.freyrsProsperity.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import wbe.freyrsProsperity.FreyrsProsperity;

import java.util.List;

public class BlockPistonExtendListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelBlessingMovement(BlockPistonExtendEvent event) {
        List<Block> blocks = event.getBlocks();
        for(Block block : blocks) {
            Location location = block.getLocation();
            for(Location activeLocation : FreyrsProsperity.activeBlessings.keySet()) {
                if(FreyrsProsperity.utilities.isLocationEquals(activeLocation, location)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
