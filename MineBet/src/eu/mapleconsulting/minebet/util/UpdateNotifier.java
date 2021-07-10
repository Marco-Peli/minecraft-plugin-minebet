package eu.mapleconsulting.minebet.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.minebet.MineBet;

public class UpdateNotifier extends BukkitRunnable {

	private Player p;
	private MineBet plugin;
	
	public UpdateNotifier(Player p, MineBet plugin) {
		this.plugin=plugin;
		this.p=p;
	}

	@Override
	public void run() {
			p.sendMessage(ChatColor.WHITE+"[MineBet] "+ ChatColor.GOLD+"Nuova versione di " 
					+ ChatColor.WHITE+ plugin.getPluginName() + ChatColor.GOLD+ " disponibile!");
			p.sendMessage(ChatColor.WHITE+"[MineBet] "+ ChatColor.GOLD+ "Versione corrente: "+
					ChatColor.AQUA+ plugin.getCurrentVersion() + ChatColor.GOLD+ ", versione aggiornata: "+
					ChatColor.AQUA+ plugin.getUpdatedVersion());
	}

}
