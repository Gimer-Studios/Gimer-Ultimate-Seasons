package gimer.gimerultimateseasons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TemperatureDisplayTask extends BukkitRunnable {

    private final SeasonManager seasonManager;
    private final Map<Player, BossBar> bossBars;

    public TemperatureDisplayTask(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
        this.bossBars = new HashMap<>();
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateTemperatureBossBar(player);
        }
    }

    private void updateTemperatureBossBar(Player player) {
        double temperatureCelsius = seasonManager.getCurrentTemperatureCelsius();
        double temperatureFahrenheit = seasonManager.getCurrentTemperatureFahrenheit();
        Season currentSeason = seasonManager.getCurrentSeason();

        // Limiting display to the tenths place
        String formattedTemperatureCelsius = String.format("%.1f", temperatureCelsius);
        String formattedTemperatureFahrenheit = String.format("%.1f", temperatureFahrenheit);

        BossBar bossBar = bossBars.computeIfAbsent(player, p -> Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID));
        bossBar.setTitle(ChatColor.GOLD + "Temperature: " + formattedTemperatureCelsius + " °C / " +
                formattedTemperatureFahrenheit + " °F\n" + ChatColor.AQUA + "Season: " + currentSeason.toString());

        bossBar.setProgress(1.0);

        if (!bossBar.getPlayers().contains(player)) {
            bossBar.addPlayer(player);
        }
    }
}