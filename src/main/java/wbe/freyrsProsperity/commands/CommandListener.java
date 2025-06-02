package wbe.freyrsProsperity.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wbe.freyrsProsperity.FreyrsProsperity;
import wbe.freyrsProsperity.config.blessings.Blessing;
import wbe.freyrsProsperity.items.MimirsArtifact;

import java.util.Random;

public class CommandListener implements CommandExecutor {

    private FreyrsProsperity plugin;


    public CommandListener() {
        this.plugin = FreyrsProsperity.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("FreyrsProsperity")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("freyrsprosperity.command.help")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                for(String line : FreyrsProsperity.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                if(!sender.hasPermission("freyrsprosperity.command.list")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                sender.sendMessage(FreyrsProsperity.messages.listMessage + FreyrsProsperity.utilities.getBlessingTypes());
            } else if(args[0].equalsIgnoreCase("time")) {
                if(!sender.hasPermission("freyrsprosperity.command.time")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                sender.sendMessage(FreyrsProsperity.messages.time.replace("%time%", FreyrsProsperity.utilities.getTime()));
            } else if(args[0].equalsIgnoreCase("location")) {
                if(!sender.hasPermission("freyrsprosperity.command.location")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                sender.sendMessage(FreyrsProsperity.utilities.getActiveBlessings());
            } else if(args[0].equalsIgnoreCase("force")) {
                if(!sender.hasPermission("freyrsprosperity.command.force")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                World world = Bukkit.getWorld("world");
                if(args.length > 2) {
                    world = Bukkit.getWorld(args[2]);
                }

                Blessing type = FreyrsProsperity.utilities.getRandomBlessing();
                if(args.length > 1) {
                    type = FreyrsProsperity.utilities.searchBlessingType(args[1]);
                }

                sender.sendMessage(FreyrsProsperity.messages.force);
                FreyrsProsperity.utilities.spawnBlessing(world, type);
            } else if(args[0].equalsIgnoreCase("artifact")) {
                if(!sender.hasPermission("freyrsprosperity.command.artifact")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                Player otherPlayer = player;
                if(args.length > 1) {
                    otherPlayer = Bukkit.getServer().getPlayer(args[1]);
                }

                otherPlayer.getInventory().addItem(new MimirsArtifact());
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("freyrsprosperity.command.reload")) {
                    sender.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }
                plugin.reloadConfiguration();
                sender.sendMessage(FreyrsProsperity.messages.reload);
            }
        }
        return true;
    }
}
