package eu.mapleconsulting.minebet.util;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.minebet.MineBet;

public class SendBook extends BukkitRunnable {
	
	private MineBet plugin;
	private PlayerJoinEvent event;

	public SendBook(PlayerJoinEvent event, MineBet plugin) {
		this.plugin=plugin;
		this.event=event;
	}

	@Override
	public void run() {
		if(!event.getPlayer().hasPlayedBefore()){
			event.getPlayer().getInventory().addItem(plugin.getGuideBook());
			event.getPlayer().sendMessage(ChatColor.GOLD+"[Minebet] You received the short guide to MineBet for being logged for the first time!");
		}
	}
}
