package wbe.freyrsProsperity.config.blessings;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.Random;

public class Blessing {

    private String id;

    private String name;

    private Material block;

    private Sound sound;

    private Particle particle;

    private int weight;

    private ArrayList<Reward> rewards = new ArrayList<>();

    public static int totalWeight = 0;

    public Blessing(String id, String name, Material block, Sound sound, Particle particle, int weight, ArrayList<Reward> rewards) {
        this.id = id;
        this.name = name;
        this.block = block;
        this.sound = sound;
        this.particle = particle;
        this.weight = weight;
        this.rewards = rewards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getBlock() {
        return block;
    }

    public void setBlock(Material block) {
        this.block = block;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Reward> rewards) {
        this.rewards = rewards;
    }

    public ArrayList<Reward> getRandomRewards() {
        ArrayList<Reward> randomRewards = new ArrayList<>();
        Random random = new Random();

        for(Reward reward : rewards) {
            if(random.nextInt(100) + 1 <= reward.getChance()) {
                randomRewards.add(reward);
            }
        }

        return randomRewards;
    }
}
