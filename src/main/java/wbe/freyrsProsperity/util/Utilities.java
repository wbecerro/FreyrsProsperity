package wbe.freyrsProsperity.util;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import wbe.freyrsProsperity.FreyrsProsperity;
import wbe.freyrsProsperity.config.blessings.Blessing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utilities {

    private FreyrsProsperity plugin;

    public Utilities() {
        this.plugin = FreyrsProsperity.getInstance();
    }

    private Location getRandomLocation(World world) {
        WorldBorder worldBorder = world.getWorldBorder();
        int radius = (int) worldBorder.getSize() / 2;
        Random random = new Random();
        int x = random.nextInt(radius + radius) - radius;
        int z = random.nextInt(radius + radius) - radius;
        int y = world.getHighestBlockYAt(x,z) + 1;

        return new Location(world, x, y, z);
    }

    public void spawnBlessing(World world, Blessing blessing) {
        Location location = getRandomLocation(world);
        if(blessing == null) {
            blessing = getRandomBlessing();
        }

        Block block = world.getBlockAt(location);
        block.setType(blessing.getBlock());
        Particle particle = blessing.getParticle();

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!FreyrsProsperity.activeBlessings.containsKey(location)) {
                    this.cancel();
                } else {
                    location.getWorld().spawnParticle(particle, location, 20, 1, 1, 1, 0.1);
                }
            }
        }.runTaskTimer(plugin, 0L, 15L);

        FreyrsProsperity.activeBlessings.put(location, blessing);
        Bukkit.broadcastMessage(FreyrsProsperity.messages.spawned.replace("%blessing%", blessing.getName()));
        Bukkit.broadcast(FreyrsProsperity.messages.adminSpawned.replace("%blessing%", blessing.getName())
                .replace("%x%", String.valueOf(location.getBlockX()))
                .replace("%y%", String.valueOf(location.getBlockY()))
                .replace("%z%", String.valueOf(location.getBlockZ()))
                .replace("%world%", location.getWorld().getName()), "freyrsprosperity.admin");
    }

    public Blessing getRandomBlessing() {
        Random random = new Random();
        int randomNumber = random.nextInt(Blessing.totalWeight);
        int weight = 0;
        List<Blessing> blessings = FreyrsProsperity.config.blessings;

        for(Blessing blessing : blessings) {
            weight += blessing.getWeight();
            if(randomNumber < weight) {
                return blessing;
            }
        }

        return blessings.get(blessings.size() - 1);
    }

    public void removeBlessing(World world) {
        for(Location location : FreyrsProsperity.activeBlessings.keySet()) {
            if(location.getWorld().equals(world)) {
                world.getBlockAt(location).setType(Material.AIR);
            }
        }

        FreyrsProsperity.activeBlessings.clear();
    }

    public void removeBlessing(Location location) {
        location.getWorld().getBlockAt(location).setType(Material.AIR);
        FreyrsProsperity.activeBlessings.remove(location);
    }

    public void removeAllBlessings() {
        for(World world : FreyrsProsperity.config.worlds) {
            removeBlessing(world);
        }
    }

    public boolean isLocationEquals(Location blessing, Location player) {
        if(!blessing.getWorld().equals(player.getWorld())) {
            return false;
        }

        if(Math.floor(blessing.getX()) == Math.floor(player.getX())
                && Math.floor(blessing.getY()) == Math.floor(player.getY())
                && Math.floor(blessing.getZ()) == Math.floor(player.getZ())) {
            return true;
        }

        return false;
    }

    public Location getWorldBlessing(World world) {
        for(Location location : FreyrsProsperity.activeBlessings.keySet()) {
            if(location.getWorld().equals(world)) {
                return location;
            }
        }

        return null;
    }

    public String getBlessingTypes() {
        ArrayList<String> types = new ArrayList<>();
        for(Blessing blessing : FreyrsProsperity.config.blessings) {
            types.add(blessing.getId());
        }

        return types.toString();
    }

    public String getTime() {
        long present = Instant.now().getEpochSecond();
        long time = FreyrsProsperity.nextBoss - present;
        int hours = (int) (time / 3600);
        int minutes = (int) ((time - 3600 * hours) / 60);
        int seconds = (int) (time - hours * 3600 - minutes * 60);

        String timeLine = "";
        if(hours > 0) {
            timeLine += hours + "h ";
        }

        if(minutes > 0) {
            timeLine += minutes + "m ";
        }

        if(seconds > 0) {
            timeLine += seconds + "s";
        }

        return timeLine;
    }

    public String getActiveBlessings() {
        String activeBlessings =  "";
        for(Map.Entry<Location, Blessing> entry : FreyrsProsperity.activeBlessings.entrySet()) {
            activeBlessings += FreyrsProsperity.messages.location.replace("%blessing%", entry.getValue().getName())
                    .replace("%x%", String.valueOf(entry.getKey().getBlockX()))
                    .replace("%y%", String.valueOf(entry.getKey().getBlockY()))
                    .replace("%z%", String.valueOf(entry.getKey().getBlockZ()))
                    .replace("%world%", entry.getKey().getWorld().getName()) + "\n";
        }

        return activeBlessings;
    }

    public Blessing searchBlessingType(String type) {
        List<Blessing> blessings = FreyrsProsperity.config.blessings;
        for(Blessing blessing : blessings) {
            if(blessing.getId().equalsIgnoreCase(type)) {
                return blessing;
            }
        }

        return blessings.get(blessings.size() - 1);
    }
}