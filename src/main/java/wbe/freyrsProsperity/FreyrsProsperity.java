package wbe.freyrsProsperity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.freyrsProsperity.commands.CommandListener;
import wbe.freyrsProsperity.commands.TabListener;
import wbe.freyrsProsperity.config.Config;
import wbe.freyrsProsperity.config.Messages;
import wbe.freyrsProsperity.config.blessings.Blessing;
import wbe.freyrsProsperity.listeners.EventListeners;
import wbe.freyrsProsperity.papi.PapiExtension;
import wbe.freyrsProsperity.util.RecipeLoader;
import wbe.freyrsProsperity.util.Scheduler;
import wbe.freyrsProsperity.util.Utilities;

import java.io.File;
import java.util.HashMap;

public final class FreyrsProsperity extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private TabListener tabListener;

    private EventListeners eventListeners;

    private PapiExtension papiExtension;

    public static RecipeLoader recipeLoader;

    public static Config config;

    public static Messages messages;

    public static Utilities utilities;

    public static HashMap<Location, Blessing> activeBlessings = new HashMap<>();

    public static long nextBoss;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiExtension = new PapiExtension();
            papiExtension.register();
        }

        saveDefaultConfig();
        getLogger().info("Freyr's Prosperity enabled correctly.");
        reloadConfiguration();
        commandListener = new CommandListener();
        eventListeners = new EventListeners();

        recipeLoader.loadRecipes();
        getCommand("freyrsprosperity").setExecutor(this.commandListener);
        tabListener = new TabListener();
        getCommand("freyrsprosperity").setTabCompleter(tabListener);
        eventListeners.initializeListeners();
        Scheduler.startSchedulers();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        recipeLoader.unloadRecipes();
        reloadConfig();
        utilities.removeAllBlessings();
        getLogger().info("Freyr's Prosperity disabled correctly.");
    }

    public static FreyrsProsperity getInstance() {
        return getPlugin(FreyrsProsperity.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        reloadConfig();
        configuration = getConfig();
        recipeLoader = new RecipeLoader();
        messages = new Messages(configuration);
        config = new Config(configuration);
        utilities = new Utilities();
    }
}
