package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;
import eu.mapleconsulting.minebet.util.Utils;

public class OpenBetsCommand extends CommandPattern {

	private MineBet plugin;

	public OpenBetsCommand(MineBet plugin){
		super("bet", "openbets");
		this.plugin=plugin;
		setDescription("Open bets for an event");
		setUsage("/bet openbets <event_name>");
		setArgumentRange(2, 2);
		setIdentifier("openbets");
		setPermission("bet.command.openbets");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		Bet b;
		try {
			b = plugin.getBetHandler().getBetByName(args[1]);
			if(!b.isOpen()){
				b.setOpen(true);
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+ "Bets for this event are now allowed.");
				Utils.notifyNewBetOpenEvent(b.getName());
			}else{
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Bets for this event are already open.");
			}
		}catch(BetNotFoundException e){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Selected bet event does not exist.");
		}	
		return true;
	}

}