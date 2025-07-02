package wbe.freyrsProsperity.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Messages {

    public String listMessage;
    public String notEnoughArgs;
    public String noPermission;
    public String time;
    public String force;
    public String spawned;
    public String adminSpawned;
    public String location;
    public String compassSuccess;
    public String compassFail;
    public String reload;
    public String openBlessing;
    public String noBlessingPlaceholder;
    public List<String> help;

    public Messages(FileConfiguration config) {
        listMessage = config.getString("Messages.list").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        time = config.getString("Messages.time").replace("&", "§");
        force = config.getString("Messages.force").replace("&", "§");
        spawned = config.getString("Messages.spawned").replace("&", "§");
        adminSpawned = config.getString("Messages.adminSpawned").replace("&", "§");
        location = config.getString("Messages.location").replace("&", "§");
        compassSuccess = config.getString("Messages.compassSuccess").replace("&", "§");
        compassFail = config.getString("Messages.compassFail").replace("&", "§");
        help = config.getStringList("Messages.help");
        reload = config.getString("Messages.reload").replace("&", "§");
        openBlessing = config.getString("Messages.openBlessing").replace("&", "§");
        noBlessingPlaceholder = config.getString("Messages.noBlessingPlaceholder").replace("&", "§");
    }
}
