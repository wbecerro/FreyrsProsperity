package wbe.freyrsProsperity.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import wbe.freyrsProsperity.FreyrsProsperity;
import wbe.freyrsProsperity.items.MimirsArtifact;

import java.util.ArrayList;
import java.util.List;

public class RecipeLoader {

    private FreyrsProsperity plugin;

    public static List<NamespacedKey> keys = new ArrayList<>();

    public RecipeLoader() {
        this.plugin = FreyrsProsperity.getInstance();
    }

    public void loadRecipes() {
        loadTomeRecipe();
    }

    public void unloadRecipes() {
        for(NamespacedKey key : keys) {
            plugin.getServer().removeRecipe(key);
        }
    }

    private void loadTomeRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "MimirsArtifact");
        ShapedRecipe recipe = new ShapedRecipe(key, new MimirsArtifact());
        recipe.shape(new String[] { " W ", "ERE", " W "});
        recipe.setIngredient('W', Material.WATER_BUCKET);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setIngredient('R', Material.RECOVERY_COMPASS);
        plugin.getServer().addRecipe(recipe);
        keys.add(key);
    }
}
