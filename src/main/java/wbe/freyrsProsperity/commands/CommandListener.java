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
                if(!player.hasPermission("freyrsprosperity.command.help")) {
                    player.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                for(String line : FreyrsProsperity.messages.help) {
                    player.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                if(!player.hasPermission("freyrsprosperity.command.list")) {
                    player.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                player.sendMessage(FreyrsProsperity.messages.listMessage + FreyrsProsperity.utilities.getBlessingTypes());
            } else if(args[0].equalsIgnoreCase("time")) {
                if(!player.hasPermission("freyrsprosperity.command.time")) {
                    player.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                player.sendMessage(FreyrsProsperity.messages.time.replace("%time%", FreyrsProsperity.utilities.getTime()));
            } else if(args[0].equalsIgnoreCase("location")) {
                if(!player.hasPermission("freyrsprosperity.command.location")) {
                    player.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                player.sendMessage(FreyrsProsperity.utilities.getActiveBlessings());
            } else if(args[0].equalsIgnoreCase("force")) {
                if(!player.hasPermission("freyrsprosperity.command.force")) {
                    player.sendMessage(FreyrsProsperity.messages.noPermission);
                    return false;
                }

                World world = player.getWorld();
                if(args.length > 2) {
                    world = Bukkit.getWorld(args[2]);
                }

                Blessing type = FreyrsProsperity.utilities.getRandomBlessing();
                if(args.length > 1) {
                    type = FreyrsProsperity.utilities.searchBlessingType(args[1]);
                }

                player.sendMessage(FreyrsProsperity.messages.force);
                FreyrsProsperity.utilities.spawnBlessing(world, type);
            } else if(args[0].equalsIgnoreCase("artifact")) {
                if(!player.hasPermission("freyrsprosperity.command.artifact")) {
                    player.sendMessage(FreyrsProsperity.messages.noPermission);
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
