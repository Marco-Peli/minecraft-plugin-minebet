package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;
import eu.mapleconsulting.minebet.exceptions.EventClosedException;
import eu.mapleconsulting.minebet.util.Utils;

public class CloseBetsCommand extends CommandPattern {

	private MineBet plugin;

	public CloseBetsCommand(MineBet plugin){
		super("bet", "closebets");
		this.plugin=plugin;
		setDescription("CLose bets for event <event_name>");
		setUsage("/bet closebets <event_name>");
		setArgumentRange(2, 2);
		setIdentifier("closebets");
		setPermission("bet.command.closebets");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		Bet b;
		try {	
			b = plugin.getBetHandler().getBetByName(args[1]);
			boolean isEventOpen = b.isOpen();
			if(isEventOpen == true){
				b.setOpen(false);
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+ "You successfully closed bets for this event.");
				Utils.notifyNewBetCloseEvent(b.getName());
			}
			else
			{
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Bets are already closed for this event");
			}
		}catch(BetNotFoundException e){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Selected bet event name is not correct.");
		}
		
		return true;
	}

}
