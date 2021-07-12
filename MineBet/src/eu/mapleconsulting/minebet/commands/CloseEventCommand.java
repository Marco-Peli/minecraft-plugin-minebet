package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.bet.Challenger;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;
import eu.mapleconsulting.minebet.exceptions.EventClosedException;

public class CloseEventCommand extends CommandPattern {

	private MineBet plugin;

	public CloseEventCommand(MineBet plugin) {
		super("bet", "closevent");
		this.plugin=plugin;
		setDescription("Close and event and declares winners and losers");
		setUsage("/bet closevent <bet_event_name> <winner>");
		setArgumentRange(3, 3);
		setIdentifier("closevent");
		setPermission("bet.command.closevent");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		Bet b;
		try {
			b = plugin.getBetHandler().getBetByName(args[1]);
			boolean isEventOpen = b.isOpen();
			if(isEventOpen){
				b.setOpen(true);
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Le scommesse per questo evento sono ancora aperte, non puoi ancora terminarlo!");
				return true;
			}
			if(!b.isValidChallengerName(args[2])){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Nessun vincitore corrispondente all'evento scelto.");
				return true;
			}else{
				return endEvent(executor, args, b);
			}
		}catch(BetNotFoundException e){
			executor.sendMessage(ChatColor.DARK_RED+
					"Il nome dell'evento non e' corretto.");
		}
		return true;
	}
	
	private boolean endEvent(Player executor, String[] args, Bet b){
		Challenger cwinner=b.getChallengerByName(args[2]);
		b.payWinners(cwinner);
		b.notifyLoosers(cwinner);
		plugin.getBetHandler().getBetList().remove(b);
		return true;
	}
	
}
