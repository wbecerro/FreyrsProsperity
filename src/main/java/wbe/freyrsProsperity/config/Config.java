package wbe.freyrsProsperity.config;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import wbe.freyrsProsperity.config.blessings.Blessing;
import wbe.freyrsProsperity.config.blessings.Reward;
import wbe.hephaestusForge.HephaestusForge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public int spawnTime;
    public int itemProtection;
    public List<World> worlds = new ArrayList<>();

    public Material artifactMaterial;
    public String artifactName;
    public List<String> artifactLore = new ArrayList<>();
    public boolean artifactGlow;

    public List<Blessing> blessings = new ArrayList<>();

    public Config(FileConfiguration config) {
        this.config = config;

        spawnTime = config.getInt("Config.spawnTime");
        itemProtection = config.getInt("Config.itemProtection");
        config.getStringList("Config.worlds").stream().forEach((world) -> {
            worlds.add(Bukkit.getWorld(world));
        });

        artifactMaterial = Material.valueOf(config.getString("Items.artifact.material"));
        artifactName = config.getString("Items.artifact.name").replace("&", "§");
        artifactLore = config.getStringList("Items.artifact.description");
        artifactGlow = config.getBoolean("Items.artifact.glow");

        loadBlessings();
    }

    private void loadBlessings() {
        Set<String> configBlessings = config.getConfigurationSection("Blessings").getKeys(false);
        int totalWeight = 0;
        for(String blessing : configBlessings) {
            String name = config.getString("Blessings." + blessing + ".name").replace("&", "§");
            Material block = Material.valueOf(config.getString("Blessings." + blessing + ".block"));
            Sound openSound = Sound.valueOf(config.getString("Blessings." + blessing + ".openSound"));
            Particle particle = Particle.valueOf(config.getString("Blessings." + blessing + ".particles"));
            int weight = config.getInt("Blessings." + blessing + ".weight");
            totalWeight += weight;
            ArrayList<Reward> rewards = loadRewards("Blessings." + blessing + ".rewards");

            Blessing configBlessing = new Blessing(blessing, name, block, openSound, particle, weight, rewards);
            blessings.add(configBlessing);
        }
        Blessing.totalWeight = totalWeight;
    }

    private ArrayList<Reward> loadRewards(String blessing) {
        ArrayList<Reward> rewards = new ArrayList<>();
        Set<String> configRewards = config.getConfigurationSection(blessing).getKeys(false);
        for(String reward : configRewards) {
            String type = config.getString(blessing + "." + reward + ".type");
            String item = config.getString(blessing + "." + reward + ".item");
            int chance = config.getInt(blessing + "." + reward + ".chance");
            int min = config.getInt(blessing + "." + reward + ".min");
            int max = config.getInt(blessing + "." + reward + ".max");
            Particle particle = Particle.valueOf(config.getString(blessing + "." + reward + ".particle"));

            ItemStack itemStack;
            try {
                itemStack = new ItemStack(Material.valueOf(item));
            } catch(IllegalArgumentException ex) {
                itemStack = new ItemStack(HephaestusForge.config.savedItems.get(item));
            }

            if(item == null) {
                continue;
            }

            Reward configReward = new Reward(type, itemStack, chance, min, max, particle);
            rewards.add(configReward);
        }

        return rewards;
    }
}
