package gimer.gimerultimateseasons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TemperatureCommand implements CommandExecutor {

    private final SeasonManager seasonManager;

    public TemperatureCommand(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("temperature")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                double temperatureCelsius = seasonManager.getCurrentTemperatureCelsius();
                double temperatureFahrenheit = seasonManager.getCurrentTemperatureFahrenheit();

                player.sendMessage("Current Temperature: " + temperatureCelsius + " °C / " +
                        temperatureFahrenheit + " °F");
            } else {
                sender.sendMessage("This command can only be used by players.");
            }
            return true;
        }
        return false;
    }
}
