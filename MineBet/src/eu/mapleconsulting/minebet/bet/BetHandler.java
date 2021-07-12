package eu.mapleconsulting.minebet.bet;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;

public class BetHandler {
	
	private List<Bet> betList;
	 
	public BetHandler(){
		betList=new ArrayList<>();
	}
	
	public void addNewBet(Bet bet){
		betList.add(bet);
	}
	public Bet getBetByName(String betName) throws BetNotFoundException{
		for(Bet b: betList){
			if(b.getName().equalsIgnoreCase(betName)){
				return b;
			}
		} throw new BetNotFoundException();
	}	
	
	public List<String> getBetEventNames(){
		List<String> betEventNames = new ArrayList<>();
		for(Bet b: betList){
			betEventNames.add(b.getName());
		}
		if(betEventNames.isEmpty()) return null;
		else return betEventNames;
	}
	
	
	public boolean removeBet(Player executor, String betName){
		for(Bet b: betList){
			if(b.getName().equalsIgnoreCase(betName)){
				betList.remove(b);
				return true;
			}
		}
		executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED + "No bet event with this name found.");
		return true;
	}

	public boolean isValidBetName(String betName){
		for(Bet b: betList){
			if(betName.equalsIgnoreCase(b.getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidBetPlacement(String betName, String challengerName){
		try{
			Bet b=getBetByName(betName);
			if(b.isValidChallengerName(challengerName)){
				return true;
			}else return false;
		}catch(BetNotFoundException e){
			return false;
		}
	}

	public List<Bet> getBetList() {
		return betList;
	}
}
