package eu.mapleconsulting.minebet.bet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.MineBet;

public class Bet {
	private List<Challenger> challengers;
	private String dateOfCreation;
	private boolean open;
	private String name;
	private String creator;
	private MineBet plugin;
	
	public Bet(MineBet plugin, List<Challenger> challengers, String dateOfCreation,
			boolean open, String name, String creator) {
		super();
		this.challengers = challengers;
		this.dateOfCreation = dateOfCreation;
		this.open = open;
		this.name = name;
		this.creator=creator;
		this.plugin=plugin;
	}
	
	public void setNewBetInChallengers(){
		for(Challenger c: challengers){
			c.setBet(this);
		}
	}
	
	public List<String> getChallengerNames(){
		List<String> challengerNames=new ArrayList<>();
		for(Challenger c: challengers){
			challengerNames.add(c.getName());
		}
		return challengerNames;
	}
	
	public boolean isValidChallengerName(String name){
	    for(String cname: getChallengerNames()){
	    	if(cname.equalsIgnoreCase(name)){
				return true;
	    }
	    }
	    return false;
	}
	
	public Challenger getChallengerByName(String name){
		for(Challenger c: challengers){
			if(c.getName().equalsIgnoreCase(name)){
				return c;
			}
		}
		return null;
	}
	
	public String getChallengersAsString(){
		String challengers="";
		for(int i=0; i<(getChallengerNames().size()-1); i++){
			challengers=challengers+getChallengerNames().get(i)+", ";
		}
		challengers=challengers+challengers+getChallengerNames().get((getChallengerNames().size()-1));
		return challengers;
	}
	
	public int getBetNumber(){
		int bets=0;
		for(Challenger c: challengers){
			bets+=c.getBetNumber();
		}
		return bets;
	}
	
	public String displayStatus(){
		if(open){
			return ChatColor.GREEN+"APERTE";
		} else return ChatColor.RED+"CHIUSE";
	}
	
	public void notifyLoosers(Challenger cwinner){
		for(Challenger c: challengers){
			if(!c.equals(cwinner)){
				for(String looserPlayerUUID: c.getBettersUUID().keySet()){
					Player p;
					double bet=c.getBettersUUID().get(looserPlayerUUID);
					p= Bukkit.getPlayer(UUID.fromString(looserPlayerUUID));
					p.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+"Hai perso nell'evento "+ ChatColor.WHITE+ this.name
							+ ChatColor.GOLD+", perdi " + ChatColor.WHITE+""+bet+ChatColor.GOLD+ " "+ 
							plugin.getEcon().currencyNamePlural());
					
				}
			}
		}
	}
	
	public void payWinners(Challenger cwinner){
		for(String winnerPlayerUUID: cwinner.getBettersUUID().keySet()){
			Player p;
			double payment=cwinner.getBettersUUID().get(winnerPlayerUUID)*cwinner.getQuote();
			p= Bukkit.getPlayer(UUID.fromString(winnerPlayerUUID));
			p.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+"Hai vinto nell'evento "+ ChatColor.WHITE+ cwinner.getBet().getName()
					+ ChatColor.GOLD+", ricevi " + ChatColor.WHITE+""+payment+ChatColor.GOLD+ " "+ 
					plugin.getEcon().currencyNamePlural());
			p.playSound(p.getLocation(),Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 10, 1);
			plugin.getEcon().depositPlayer(p, payment);
		}
	}
	
	public boolean cancelBet(Player executor){
		for(Challenger c: this.challengers){
			if(c.getBettersUUID().containsKey(executor.getUniqueId().toString())){
				double bet=c.getBettersUUID().get(executor.getUniqueId().toString());
				c.getBettersUUID().remove(executor.getUniqueId().toString());
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+
						"Hai rimosso correttamente tua scommessa di " + 
							ChatColor.WHITE+ bet + " " + ChatColor.GOLD + 
							plugin.getEcon().currencyNamePlural()+ ChatColor.GOLD+ " sull'evento " + ChatColor.WHITE+ this.name);
				return true;
			}
		}
		executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
				"Non hai piazzato nessuna scommessa su questo evento");
		return true;
	}
	
	public boolean hasAlreadyBidden(Player executor){
		for(Challenger c: challengers){
			if(c.getBettersUUID().containsKey(executor.getUniqueId().toString())){
				executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"Hai gia' piazzato una scommessa su questo evento!");
				return true;
			}
		}
		return false;
	}

	public List<Challenger> getChallengers() {
		return challengers;
	}

	public String getDateOfCreation() {
		return dateOfCreation;
	}

	public boolean isOpen() {
		return open;
	}

	public String getName() {
		return name;
	}

	public String getCreator() {
		return creator;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
