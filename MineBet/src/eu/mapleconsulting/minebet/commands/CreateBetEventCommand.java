package eu.mapleconsulting.minebet.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.MineBet;
import eu.mapleconsulting.minebet.bet.Challenger;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;
import eu.mapleconsulting.minebet.util.Utils;

public class CreateBetEventCommand extends CommandPattern {

	private MineBet plugin;
	
	public CreateBetEventCommand(MineBet plugin){
		super("bet", "createvent");
		this.plugin=plugin;
		setDescription("Creates a new bet event.");
        setUsage("/bet createvent <event_name> <opponent_n> <quotation_n>, max " +plugin.getConfigManager().getMaxChallengersPerBet() +" sfidanti");
        setArgumentRange(6, (plugin.getConfigManager().getMaxChallengersPerBet()*2)+2);
        setIdentifier("createvent");
        setPermission("bet.command.createvent");
	}
	@Override
	public boolean execute(Player executor, String[] args) {
		//controllo condizioni
		if(!(plugin.getBetHandler().getBetList().size()<plugin.getConfigManager().getMaxBetEvents())){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "You reached the maximum of concurrent bet events, max: " +
					plugin.getConfigManager().getMaxBetEvents()+ " events.");
			return true;
		}
		
		if(args.length%2!=0){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "One or more opponents or quotations are missing.");
			return true;
		}
		
		try{
			for(int quoteIndex=3; quoteIndex<args.length; quoteIndex+=2){
			Double.parseDouble(args[quoteIndex]);
			}
			} catch(NumberFormatException e){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Invalid quotations.");
				return true;
		    }
		for (Bet b: plugin.getBetHandler().getBetList()){
			if(b.getName().equalsIgnoreCase(args[1])){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "The event name has been already taken.");
				return true;
			}
		}
		if(isBetNameLengthInvalid(args, executor)){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Error, max event name length: " + ChatColor.WHITE + "" +
					plugin.getConfigManager().getMaxEventLength() + "" + ChatColor.DARK_RED+ " chars");
			return true;
		}
		
		if(isDuplicates(args)){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Opponents names cannot be the same");
			return true;
		}
		
		//creazione lista di challenger
		List<Challenger> challengers=createChallengers(args);
		
		//formattazione data
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();	
		DateFormat preciseDateFormat = new SimpleDateFormat("HH:mm");
		Date preciseDate = new Date();
		String dateString=dateFormat.format(date);
		String preciseDateString=preciseDateFormat.format(preciseDate);
		
		//creazione nuovo evento scommessa
		
		plugin.getBetHandler().addNewBet(new Bet(plugin, challengers, dateString+" "+preciseDateString, true, args[1], executor.getName()));
		executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+ "Bet event " + ChatColor.WHITE + args[1] +
				ChatColor.GOLD+ " successfully created!");
		try {
			plugin.getBetHandler().getBetByName(args[1]).setNewBetInChallengers();
			Utils.notifyNewBetEvent(plugin.getBetHandler().getBetByName(args[1]).getName());
		} catch (BetNotFoundException e) {
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Unknown error.");
		}
		
		return true;
	}
	
	private boolean isDuplicates(String[] args){
		for(int i=2; i<args.length;i=i+2){
			for(int j=4; j<args.length;j=j+2){
				if(j!=i && args[i].equalsIgnoreCase(args[j])){
					return true;
				}
			}
			
		}
		return false;
	}
	
	private boolean isBetNameLengthInvalid(String[] args, Player executor){
		if(args[1].length()>plugin.getConfigManager().getMaxEventLength()){
			return true;
		}else return false;
	}
	
	private List<Challenger> createChallengers(String[] args){
		List<Challenger> challengers=new ArrayList<>();
		for(int quoteIndex=3; quoteIndex<args.length; quoteIndex+=2){
			int nameIndex=quoteIndex-1;
			double quote=Double.parseDouble(args[quoteIndex]);
			challengers.add(new Challenger(args[nameIndex], quote));
			}
		return challengers;
	}

}
