package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;

public class ListBetsCommand extends CommandPattern {

	private MineBet plugin;

	public ListBetsCommand(MineBet plugin) {
		super("bet", "listbets");
		this.plugin=plugin;
		setDescription("Displayes all ongoing bet events");
		setUsage("/bet listbets [gui]");
		setArgumentRange(1, 2);
		setIdentifier("listbets");
		setPermission("bet.command.listbets");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		if(plugin.getBetHandler().getBetList().isEmpty()){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.RED+ "No ongoing bet event");
			return true;
		}
		switch(args.length)
		{
		case 1:
			return displayBets(executor);
		case 2:
			String guiKeyWord = "gui";
			if(guiKeyWord.equalsIgnoreCase(args[1]))
			{
				plugin.getBetMenuListener().openBetEventsMenu(executor);
			}
			else
			{
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"Invalid arguments, correct use:");
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD + getUsage());
			}
		}
		
		return true;
	}

	public boolean displayBets(Player executor){
		int i=1;
		for (Bet b: plugin.getBetHandler().getBetList()){
			executor.sendMessage(i+") " +ChatColor.GOLD+"Event name: " + ChatColor.WHITE+b.getName()+ChatColor.GOLD+ChatColor.GOLD+
					", Opponents: " + ChatColor.WHITE+b.getChallengersAsString()+ChatColor.GOLD+", bets: "
					+ChatColor.WHITE+""+b.getBetNumber());				
			i++;
		}

		return true;
	}

}
