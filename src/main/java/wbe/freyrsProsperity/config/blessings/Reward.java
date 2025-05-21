package wbe.freyrsProsperity.config.blessings;

import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public class Reward {

    private ItemStack item;

    private int chance;

    private int min;

    private int max;

    private Particle particle;

    public Reward(ItemStack item, int chance, int min, int max, Particle particle) {
        this.item = item;
        this.chance = chance;
        this.min = min;
        this.max = max;
        this.particle = particle;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }
}
