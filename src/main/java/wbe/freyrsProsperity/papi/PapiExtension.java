package wbe.freyrsProsperity.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.freyrsProsperity.util.Utilities;

public class PapiExtension extends PlaceholderExpansion {

    private Utilities utilities;

    public PapiExtension() {
        this.utilities = new Utilities();
    }

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "DeathOath";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("time")) {
            return "";
        }

        return null;
    }
}
