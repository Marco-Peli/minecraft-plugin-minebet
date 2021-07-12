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
		setDescription("Place a bet on an event.");
		setUsage("/bet placebet <event_name> <opponent> <bet_amount>");
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
					"You have not set a default bet amount yet, "+ ChatColor.GOLD+ "/bet default <bet_amount>" 
						+ChatColor.DARK_RED+" to set");
			return true;
		}
		double placingBet= plugin.getDefaultBetsManager().getDefaultBets().get(executor.getUniqueId().toString());
		if(placingBet>plugin.getConfigManager().getMaxBet()){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Bet amount is too high. change it with /bet default. Max bet amount: " + ChatColor.WHITE+
						plugin.getConfigManager().getMaxBet());
			return true;
		}
		if(!plugin.getEcon().has(executor, placingBet)){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"You have not enough money to place this bet.");
			return true;
		}
		return deployBet(executor, args, placingBet);
	}
	
	
	private boolean deployBet(Player executor, String[] args, double placingBet){
		Bet b;
		try {
			String betName = args[1];
			String challengerName = args[2];
			b = plugin.getBetHandler().getBetByName(betName);

			if(!b.isValidChallengerName(challengerName)){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
						"Opponent name invalid.");
				return true;
			}
			
			if(b.isOpen()){
				b.placeBet(challengerName, placingBet, executor);
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+
						"You successfully placed a bet of "+ChatColor.WHITE+""+placingBet+ChatColor.GOLD+" on event "+ChatColor.WHITE+betName);
				return true;
			}else
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
						"Bets for this event are closed.");
		} catch (BetNotFoundException e) {
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"This event does not exist.");
		}
		return true;
	}
	
	
	
	
	private boolean placeBet(Player executor, String[] args){
		double placingBet;
		try{
			placingBet=Double.parseDouble(args[3]);
			if(placingBet>plugin.getConfigManager().getMaxBet()){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
						"Bet amount is too high. change it with /bet default. Max bet amount: " + plugin.getConfigManager().getMaxBet());
				return true;
			}
		}catch(NumberFormatException e){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Bet amount must be a number.");
			return true;
		}

		if(!plugin.getEcon().has(executor, placingBet)){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"You have not enough money to place this bet.");
			return true;
		}
		return deployBet(executor, args, placingBet);
	}

}
