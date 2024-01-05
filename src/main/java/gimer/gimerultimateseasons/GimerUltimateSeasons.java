package gimer.gimerultimateseasons;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class GimerUltimateSeasons extends JavaPlugin {

    private SeasonManager seasonManager;

    @Override
    public void onEnable() {
        getLogger().info("Gimer Ultimate Seasons plugin has been enabled!");
        int pluginId = 20513;
        new Metrics(this, pluginId);
        seasonManager = new SeasonManager(this);
        getCommand("season").setExecutor(new SeasonCommand(seasonManager));
        getCommand("temperature").setExecutor(new TemperatureCommand(seasonManager));
        getServer().getPluginManager().registerEvents(new SeasonChangeListener(seasonManager), this);

        getLogger().info("Thanks for using Gimer Ultimate Seasons!");
        new BukkitRunnable() {
            @Override
            public void run() {
                seasonManager.updateSeasonForAllPlayers();
            }
        }.runTaskTimer(this, 0L, 72000L);

        new TemperatureDisplayTask(seasonManager).runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Gimer Ultimate Seasons plugin has been disabled!");
        getLogger().info("Thanks for using Gimer Ultimate Seasons!");
    }

    public SeasonManager getSeasonManager() {
        return seasonManager;
    }
}
