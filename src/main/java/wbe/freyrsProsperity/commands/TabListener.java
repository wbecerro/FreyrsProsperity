package wbe.freyrsProsperity.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import wbe.freyrsProsperity.FreyrsProsperity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabListener implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("help", "time", "location", "force", "list",
            "artifact", "reload");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if(!command.getName().equalsIgnoreCase("FreyrsProsperity")) {
            return completions;
        }

        // Mostrar subcomandos
        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        // Argumento 1
        if(args.length == 2) {
            switch(args[0].toLowerCase()) {
                case "force":
                    List<String> blessings = new ArrayList<>();
                    FreyrsProsperity.config.blessings.forEach((blessing -> {
                        if(args[1].isEmpty()) {
                            blessings.add(blessing.getId());
                        } else if(blessing.getId().startsWith(args[1])) {
                            blessings.add(blessing.getId());
                        }
                    }));
                    completions.addAll(blessings);
                    break;
                case "artifact":
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(args[1].isEmpty()) {
                            completions.add(player.getName());
                        } else if(player.getName().startsWith(args[1])) {
                            completions.add(player.getName());
                        }
                    }
                    break;
            }
        }

        // Argumento 2
        if(args.length == 3) {
            switch(args[0].toLowerCase()) {
                case "force":
                    List<String> worlds = new ArrayList<>();
                    Bukkit.getWorlds().forEach((world -> {
                        if(args[2].isEmpty()) {
                            worlds.add(world.getName());
                        } else if(world.getName().startsWith(args[2])) {
                            worlds.add(world.getName());
                        }
                    }));
                    completions.addAll(worlds);
                    break;
            }
        }

        return completions;
    }
}
