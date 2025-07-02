package wbe.freyrsProsperity.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.freyrsProsperity.FreyrsProsperity;
import wbe.freyrsProsperity.config.blessings.Blessing;

import java.util.Optional;

public class PapiExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "FreyrsProsperity";
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
            return FreyrsProsperity.utilities.getTime();
        } else if(params.equalsIgnoreCase("blessing")) {
            Optional<Blessing> blessing = FreyrsProsperity.activeBlessings.values().stream().findFirst();
            if(blessing.isPresent()) {
                return blessing.get().getName();
            } else {
                return FreyrsProsperity.messages.noBlessingPlaceholder;
            }
        }

        return null;
    }
}
