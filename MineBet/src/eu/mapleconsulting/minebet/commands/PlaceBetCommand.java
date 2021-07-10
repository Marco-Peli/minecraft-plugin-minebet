package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;

public class PlaceBetCommand extends CommandPattern {

	private MineBet plugin;

	public PlaceBetCommand(MineBet plugin) {
		super("bet", "placebet");
		this.plugin=plugin;
		setDescription("Piazza una scommessa su un evento.");
		setUsage("/bet placebet <nome_evento> <sfidante> <scommessa>");
		setArgumentRange(4, 4);
		setIdentifier("placebet");
		setPermission("bet.command.placebet");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		if(args.length==3){
			return placeDefaultBet(executor, args);
		}
		else return placeBet(executor, args);
	}
	
	private boolean placeDefaultBet(Player executor, String[] args){
		if(!plugin.getDefaultBetsManager().isDefaultBet(executor.getUniqueId().toString())){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Non hai ancora settato una piazzata di default, "+ ChatColor.GOLD+ "/bet default <quota>" 
						+ChatColor.DARK_RED+"per settarla");
			return true;
		}
		double placingBet= plugin.getDefaultBetsManager().getDefaultBets().get(executor.getUniqueId().toString());
		if(placingBet>plugin.getConfigManager().getMaxBet()){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"La quota da piazzare inserita e' troppo alta. Cambiala con /bet default. Massima quota consentita: " + ChatColor.WHITE+
						plugin.getConfigManager().getMaxBet());
			return true;
		}
		if(!plugin.getEcon().has(executor, placingBet)){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Non disponi del denaro necessario per piazzare questa scommessa.");
			return true;
		}
		return deployBet(executor, args, placingBet);
	}
	
	
	private boolean deployBet(Player executor, String[] args, double placingBet){
		Bet b;
		try {
			b = plugin.getBetHandler().getBetByName(args[1]);
			if(b.hasAlreadyBidden(executor)){
				return true;
			}
			if(!b.isValidChallengerName(args[2])){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
						"Non esiste nessuno sfidante con questo nome associato a questo evento.");
				return true;
			}
			
			if(b.isOpen()){
				b.getChallengerByName(args[2]).addNewBet(executor, placingBet);
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+
						"Hai correttamente piazzato una scommessa di "+ChatColor.WHITE+""+placingBet+ChatColor.GOLD+" sull'evento "+ChatColor.WHITE+args[1]);
				return true;
			}else
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
						"Le scommesse per questo evento sono chiuse.");
		} catch (BetNotFoundException e) {
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Il nome dell'evento non e' corretto.");
		}
		return true;
	}
	
	
	
	
	private boolean placeBet(Player executor, String[] args){
		double placingBet;
		try{
			placingBet=Double.parseDouble(args[3]);
			if(placingBet>plugin.getConfigManager().getMaxBet()){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
						"La quota da piazzare inserita e' troppo alta. Massima quota consentita: " + plugin.getConfigManager().getMaxBet());
				return true;
			}
		}catch(NumberFormatException e){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"La quota da piazzare inserita non e' valida.");
			return true;
		}

		if(!plugin.getEcon().has(executor, placingBet)){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Non disponi del denaro necessario per piazzare questa scommessa.");
			return true;
		}
		return deployBet(executor, args, placingBet);
	}

}
