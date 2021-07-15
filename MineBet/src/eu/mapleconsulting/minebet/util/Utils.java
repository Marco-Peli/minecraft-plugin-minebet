package eu.mapleconsulting.minebet.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

	public Utils() {
		
	}
	
	public static void notifyNewBetEvent(String betName){
		for(Player p: Bukkit.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.GOLD+"[MineBet-Notify] "+ChatColor.WHITE+"The bet event " + ChatColor.GOLD+betName+ ChatColor.WHITE+" has been created!");
		}
	}
	public static void notifyNewBetCloseEvent(String betName){
		for(Player p: Bukkit.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.GOLD+"[MineBet-Notify] "+ChatColor.WHITE+"Bets for event " + ChatColor.GOLD+betName+ ChatColor.WHITE+" are now closed.");
		}
	}
	
	public static void notifyNewBetOpenEvent(String betName){
		for(Player p: Bukkit.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.GOLD+"[MineBet-Notify] "+ChatColor.WHITE+"Bets for event " + ChatColor.GOLD+betName+ ChatColor.WHITE+" are now opened.");
		}
	}
	
	public static void printConsoleMsg(String msg)
	{
		System.out.println("[MineBet] " + msg);
	}
}
