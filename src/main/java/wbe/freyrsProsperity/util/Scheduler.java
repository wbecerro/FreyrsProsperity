package wbe.freyrsProsperity.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import wbe.freyrsProsperity.FreyrsProsperity;

import java.time.Instant;

public class Scheduler {

    private static FreyrsProsperity plugin;

    public static void startSchedulers() {
        plugin = FreyrsProsperity.getInstance();
        startBlessingSpawning();
    }

    private static void startBlessingSpawning() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(World world : FreyrsProsperity.config.worlds) {
                    if(FreyrsProsperity.activeBlessings.isEmpty()) {
                        FreyrsProsperity.utilities.spawnBlessing(world, null);
                    }
                    FreyrsProsperity.nextBlessing = Instant.now().getEpochSecond() + FreyrsProsperity.config.spawnTime;
                }
            }
        }, 10L, FreyrsProsperity.config.spawnTime * 20L);
    }
}
