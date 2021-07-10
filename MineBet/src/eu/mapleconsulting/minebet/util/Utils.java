package eu.mapleconsulting.minebet.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

	public Utils() {
		
	}
	
	public static void notifyNewBetEvent(String betName){
		for(Player p: Bukkit.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.GOLD+"[MineBet] "+ChatColor.WHITE+"E' stato creato l'evento scommessa " + ChatColor.GOLD+betName+ ChatColor.WHITE+"!");
		}
	}
	public static void notifyNewBetCloseEvent(String betName){
		for(Player p: Bukkit.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.GOLD+"[MineBet] "+ChatColor.WHITE+"Le scommesse per l'evento " + ChatColor.GOLD+betName+ ChatColor.WHITE+" sono state chiuse.");
		}
	}
	
	public static void notifyNewBetOpenEvent(String betName){
		for(Player p: Bukkit.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.GOLD+"[MineBet] "+ChatColor.WHITE+"Le scommesse per l'evento " + ChatColor.GOLD+betName+ ChatColor.WHITE+" sono state aperte.");
		}
	}
}
