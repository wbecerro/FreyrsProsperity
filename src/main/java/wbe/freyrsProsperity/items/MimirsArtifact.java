package wbe.freyrsProsperity.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.freyrsProsperity.FreyrsProsperity;

import java.util.ArrayList;

public class MimirsArtifact extends ItemStack {

    public MimirsArtifact() {
        super(FreyrsProsperity.config.artifactMaterial);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(FreyrsProsperity.config.artifactMaterial);
        }

        meta.setDisplayName(FreyrsProsperity.config.artifactName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : FreyrsProsperity.config.artifactLore) {
            lore.add(line.replace("&", "§"));
        }

        meta.setLore(lore);

        if(FreyrsProsperity.config.artifactGlow) {
            meta.addEnchant(Enchantment.POWER, 5, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        FreyrsProsperity plugin = FreyrsProsperity.getInstance();
        NamespacedKey key = new NamespacedKey(plugin, "MimirsArtifact");
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

        setItemMeta(meta);
    }
}
