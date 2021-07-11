package eu.mapleconsulting.minebet.bet;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class Challenger {
	private double quote;
	private String name;
	private Bet bet;
	private Map<String, Double> bettersUUID;
	
	public Challenger(String name, double quote){
		this.name=name;
		this.quote=quote;
		bettersUUID=new HashMap<>();
	}

	public double getBetAmount(Player better){
		double noBet=0;
		if(bettersUUID.containsKey(better.getUniqueId().toString())) 
			return bettersUUID.get(better.getUniqueId().toString());
		else return noBet;
	}
	
	public int getBetNumber(){
		return bettersUUID.keySet().size();
	}
	
	public double getQuote() {
		return quote;
	}

	public String getName() {
		return name;
	}

	public Bet getBet() {
		return bet;
	}

	public Map<String, Double> getBettersUUID() {
		return bettersUUID;
	}
	
	public void addNewBet(Player executor, double bet){
		removeBet(executor);
		bettersUUID.put(executor.getUniqueId().toString(), bet);
	}
	
	public void removeBet(Player executor) {
		bettersUUID.remove(executor.getUniqueId().toString());
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
}
