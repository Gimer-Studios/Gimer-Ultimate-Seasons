package gimer.gimerultimateseasons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SeasonManager {

    public void setCurrentTemperature(double temperature) {
        this.currentTemperatureCelsius = temperature;
        updateSeasonForAllPlayers();
    }
    private final GimerUltimateSeasons plugin;
    private Season currentSeason = Season.SPRING;
    private Map<String, Season> playerSeasons = new HashMap<>();
    private double currentTemperatureCelsius = 15.0;

    public SeasonManager(GimerUltimateSeasons plugin) {
        this.plugin = plugin;
        updateSeason();
        scheduleRealTimeUpdater();
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Season newSeason) {
        currentSeason = newSeason;
    }

    public double getCurrentTemperatureCelsius() {
        return currentTemperatureCelsius;
    }

    public double getCurrentTemperatureFahrenheit() {
        // Convert Celsius to Fahrenheit: (Celsius * 9/5) + 32
        return (currentTemperatureCelsius * 9 / 5) + 32;
    }

    public double calculateDefaultTemperature() {
        switch (getCurrentSeason()) {
            case SPRING:
                return 15.0; // 15 degrees Celsius in spring
            case SUMMER:
                return 25.0; // 25 degrees Celsius in summer
            case AUTUMN:
                return 18.0; // 18 degrees Celsius in autumn
            case WINTER:
                return 5.0;  // 5 degrees Celsius in winter
            default:
                return 20.0; // Default to 20 degrees Celsius for any unknown season
        }
    }

    public void updateSeason() {
        LocalDate currentDate = LocalDate.now();

        int dayOfMonth = currentDate.getDayOfMonth();
        int monthValue = currentDate.getMonthValue();

        if ((monthValue == 3 && dayOfMonth >= 20) || (monthValue > 3 && monthValue < 6)) {
            setCurrentSeason(Season.SPRING);
        } else if ((monthValue == 6 && dayOfMonth >= 21) || (monthValue > 6 && monthValue < 9)) {
            setCurrentSeason(Season.SUMMER);
        } else if ((monthValue == 9 && dayOfMonth >= 23) || (monthValue > 9 && monthValue < 12)) {
            setCurrentSeason(Season.AUTUMN);
        } else {
            setCurrentSeason(Season.WINTER);
        }
        // Update the temperature based on the current season and daily variation
        double baseTemperature = calculateDefaultTemperature();
        long time = Bukkit.getWorld("world").getTime();

        // Daily temperature variation
        double dailyVariation = Math.sin((time / 10000.0) * Math.PI) * 5.0; // Adjust amplitude

        // Random factors
        double randomFactor = Math.random() * 2 - 1;
        dailyVariation += randomFactor;

        // Smoothing factor
        double smoothingFactor;
        if (time >= 1000 && time < 6000) { // Daytime
            smoothingFactor = 0.001;
        } else { // Nighttime
            smoothingFactor = 0.005;
        }

        // Update the current temperature
        currentTemperatureCelsius += smoothingFactor * (baseTemperature + dailyVariation - currentTemperatureCelsius);

        updateSeasonForAllPlayers();
    }


    public void updateSeasonForAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerSeason(player);
        }
    }

    public Season getPlayerSeason(Player player) {
        return playerSeasons.getOrDefault(player.getUniqueId().toString(), getCurrentSeason());
    }

    public void updatePlayerSeason(Player player) {
        playerSeasons.put(player.getUniqueId().toString(), getCurrentSeason());
    }

    private void scheduleRealTimeUpdater() {
        // Schedule a task to update seasons and temperature periodically
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateSeason, 0L, 1L);
    }
}
