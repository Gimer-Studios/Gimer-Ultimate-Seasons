package gimer.gimerultimateseasons;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SeasonChangeListener implements Listener {

    private final SeasonManager seasonManager;

    public SeasonChangeListener(SeasonManager seasonManager) {
        this.seasonManager = seasonManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Update the season for the player when they join
        seasonManager.updatePlayerSeason(event.getPlayer());
    }
}