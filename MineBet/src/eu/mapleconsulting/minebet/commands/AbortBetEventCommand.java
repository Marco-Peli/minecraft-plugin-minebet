package eu.mapleconsulting.minebet.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.bet.Challenger;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;

public class AbortBetEventCommand extends CommandPattern {

	private MineBet plugin;

	public AbortBetEventCommand(MineBet plugin) {
		super("bet", "abort");
		this.plugin=plugin;
		setDescription("Delete a bet event and refund the betters");
		setUsage("/bet abort <nome_evento>");
		setArgumentRange(2, 2);
		setIdentifier("abort");
		setPermission("bet.command.abort");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try {
			Bet b=plugin.getBetHandler().getBetByName(args[1]);
			for(Challenger c: b.getChallengers()){
				if(!c.getBettersUUID().keySet().isEmpty()){
					for(String toBeRefundedUUID: c.getBettersUUID().keySet()){
						Player refunded=Bukkit.getPlayer(UUID.fromString(toBeRefundedUUID));
						plugin.getEcon().depositPlayer(refunded, c.getBettersUUID().get(refunded.getUniqueId().toString()));
						refunded.sendMessage(ChatColor.WHITE+"[MineBet] "+ ChatColor.GOLD+"Event " + ChatColor.WHITE+args[1]+ChatColor.GOLD+" was deleted before time,"
								+ " you get a refund of " + ChatColor.WHITE+""+c.getBettersUUID().get(refunded.getUniqueId().toString())+ChatColor.GOLD+" " +plugin.getEcon().currencyNamePlural());
					}
				}
			}
			plugin.getBetHandler().getBetList().remove(b);
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+
					"Event "+ ChatColor.WHITE+args[1]+ ChatColor.GOLD+" has been successfully dleted and betters refunded!");
			return true;
		} catch (BetNotFoundException e) {
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"The selected bet event does not exist.");
			return true;
		}
	}

}
