package wbe.freyrsProsperity.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.freyrsProsperity.FreyrsProsperity;

public class EventListeners {

    public void initializeListeners() {
        FreyrsProsperity plugin = FreyrsProsperity.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerInteractListeners(), plugin);
        pluginManager.registerEvents(new PlayerJoinListeners(), plugin);
    }
}
