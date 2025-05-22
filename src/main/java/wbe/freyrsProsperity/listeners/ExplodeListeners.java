package wbe.freyrsProsperity.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import wbe.freyrsProsperity.FreyrsProsperity;

import java.util.List;

public class ExplodeListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelBlessingBlockExplosion(BlockExplodeEvent event) {
        List<Block> blocks = event.blockList();
        for(Block block : blocks) {
            Location location = block.getLocation();
            for(Location activeLocation : FreyrsProsperity.activeBlessings.keySet()) {
                if(FreyrsProsperity.utilities.isLocationEquals(activeLocation, location)) {
                    blocks.remove(block);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void cancelBlessingEntityExplosion(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();
        for(Block block : blocks) {
            Location location = block.getLocation();
            for(Location activeLocation : FreyrsProsperity.activeBlessings.keySet()) {
                if(FreyrsProsperity.utilities.isLocationEquals(activeLocation, location)) {
                    blocks.remove(block);
                    return;
                }
            }
        }
    }
}
