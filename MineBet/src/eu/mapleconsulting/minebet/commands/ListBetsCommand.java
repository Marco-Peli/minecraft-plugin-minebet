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
		setDescription("Visualizza tutti gli eventi scommessa in corso");
		setUsage("/bet listbets");
		setArgumentRange(1, 1);
		setIdentifier("listbets");
		setPermission("bet.command.listbets");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		if(plugin.getBetHandler().getBetList().isEmpty()){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.RED+ "Nessun evento scommessa in corso");
			return true;
		}
		return displayBets(executor);
	}
	
	public boolean displayBets(Player executor){
			int i=1;
			for (Bet b: plugin.getBetHandler().getBetList()){
						if(executor.hasPermission("bet.command.createvent")){
							executor.sendMessage(i+") " +ChatColor.GOLD+"Evento: " + ChatColor.WHITE+b.getName()+ChatColor.GOLD+ChatColor.GOLD+
									"Sfidanti: " + ChatColor.WHITE+b.getChallengersAsString()+ChatColor.GOLD+", puntate: "
									+ChatColor.WHITE+""+b.getBetNumber()+ChatColor.GOLD+", by " +ChatColor.WHITE+b.getCreator());
						}
						executor.sendMessage(i+") " +ChatColor.GOLD+"Evento: " + ChatColor.WHITE+b.getName()+ChatColor.GOLD+ChatColor.GOLD+
								"Sfidanti: " + ChatColor.WHITE+b.getChallengersAsString()+ChatColor.GOLD+", puntate: "
								+ChatColor.WHITE+""+b.getBetNumber());				
				i++;
			}
		
		return true;
	}

}
