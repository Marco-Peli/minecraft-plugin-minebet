package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;
import eu.mapleconsulting.minebet.util.Utils;

public class CloseBetsCommand extends CommandPattern {

	private MineBet plugin;

	public CloseBetsCommand(MineBet plugin){
		super("bet", "closebets");
		this.plugin=plugin;
		setDescription("Chiude le scommesse per un evento");
		setUsage("/bet closebets <nome_evento>");
		setArgumentRange(2, 2);
		setIdentifier("closebets");
		setPermission("bet.command.closebets");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		Bet b;
		try {	
			b = plugin.getBetHandler().getBetByName(args[1]);
			if(b.isOpen()){
				b.setOpen(false);
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+ "Hai correttamente chiuso le scommesse per questo evento.");
				Utils.notifyNewBetCloseEvent(b.getName());
			}else{
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Le scommesse per questo evento sono gia' chiuse.");
			}
		}catch(BetNotFoundException e){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Il nome dell'evento non e' corretto.");
		}
		
		return true;
	}

}
