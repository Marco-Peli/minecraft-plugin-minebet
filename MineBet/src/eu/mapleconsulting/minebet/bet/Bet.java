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
		for (String opponentName: getChallengerNames())
		{
			challengers += opponentName + " ";
		}
		challengers = challengers.trim();
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
			return ChatColor.GREEN+"OPEN";
		} else return ChatColor.RED+"CLOSED";
	}

	public void notifyLoosers(Challenger cwinner){
		for(Challenger c: challengers){
			if(!c.equals(cwinner)){
				for(String looserPlayerUUID: c.getBettersUUID().keySet()){
					Player p;
					double bet=c.getBettersUUID().get(looserPlayerUUID);
					p= Bukkit.getPlayer(UUID.fromString(looserPlayerUUID));
					p.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+"You lost in event "+ ChatColor.WHITE+ this.name
							+ ChatColor.GOLD+", you lose " + ChatColor.WHITE+""+bet+ChatColor.GOLD+ " "+ 
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
			p.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+"You won in event "+ ChatColor.WHITE+ cwinner.getBet().getName()
					+ ChatColor.GOLD+", you get " + ChatColor.WHITE+""+payment+ChatColor.GOLD+ " "+ 
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
						"You successfuly removed your bet of " + 
						ChatColor.WHITE+ bet + " " + ChatColor.GOLD + 
						plugin.getEcon().currencyNamePlural()+ ChatColor.GOLD+ " on event " + ChatColor.WHITE+ this.name);
				plugin.getEcon().depositPlayer(executor, bet);
				return true;
			}
		}
		executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
				"You have no bets on this event");
		return true;
	}

	public void placeBet(String challengerName, double amount, Player better){
		if(hasAlreadyBidden(better))
		{
			refundBetter(better);
		}
		for(Challenger c: challengers)
		{
			c.removeBet(better);
		}
		plugin.getEcon().withdrawPlayer(better, amount);
		getChallengerByName(challengerName).addNewBet(better, amount);
	}

	private void refundBetter(Player better)
	{
		for(Challenger c: this.challengers){
			if(c.getBettersUUID().containsKey(better.getUniqueId().toString())){
				plugin.getEcon().depositPlayer(better, c.getBetAmount(better));
			}
		}
	}

	public boolean hasAlreadyBidden(Player executor){
		for(Challenger c: this.challengers){
			if(c.getBettersUUID().containsKey(executor.getUniqueId().toString())){
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

	public boolean isOpen(){
		return this.open;
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
