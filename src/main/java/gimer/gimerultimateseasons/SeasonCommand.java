package gimer.gimerultimateseasons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SeasonCommand implements CommandExecutor {

    private final SeasonManager seasonManager;

    public SeasonCommand(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("season") && sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Current season: " + seasonManager.getPlayerSeason(player));
            return true;
        }
        return false;
    }
}
