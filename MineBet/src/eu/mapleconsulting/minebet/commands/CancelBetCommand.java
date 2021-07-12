package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;

public class CancelBetCommand extends CommandPattern {

	private MineBet plugin;
	
	public CancelBetCommand(MineBet plugin) {
		super("bet", "cancelbet");
		this.plugin=plugin;
		setDescription("Cancel your bet on event <event>");
		setUsage("/bet cancel <bet_event_name>");
		setArgumentRange(2, 2);
		setIdentifier("cancel");
		setPermission("bet.command.cancel");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		Bet b;
		try {
			b = plugin.getBetHandler().getBetByName(args[1]);
			return b.cancelBet(executor);
		}catch(BetNotFoundException e){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Selected bet event does not exist.");
			return true;
		}
	
	}

}
