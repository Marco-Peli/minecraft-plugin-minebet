package eu.mapleconsulting.minebet;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.mapleconsulting.minebet.util.SendBook;
import eu.mapleconsulting.minebet.util.UpdateNotifier;

public class OnJoinListener implements Listener {

	private MineBet plugin;
	
	public OnJoinListener(MineBet plugin){
		this.plugin=plugin;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
        new SendBook(event, this.plugin).runTaskLaterAsynchronously(plugin, 10);
        if(event.getPlayer().hasPermission("bet.command.update") && plugin.getConfigManager().isUpdateAvailable()){
        	new UpdateNotifier(event.getPlayer(), plugin).runTaskLater(plugin, 10);
        }
    }
}
