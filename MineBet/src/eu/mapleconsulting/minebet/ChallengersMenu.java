package eu.mapleconsulting.minebet;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.bet.Challenger;
import eu.mapleconsulting.minebet.commands.CommandInterface;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;


public class ChallengersMenu implements Listener {
	
	private Bet b;
	private final String challengersMenuName="Lista sfidanti";
	private final int inventorySize=9;
	private MineBet plugin;
	
	public ChallengersMenu(Bet b, MineBet plugin){
		this.b=b;
		this.plugin=plugin;
	}
	
	public ChallengersMenu(){
	
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Player better= (Player) event.getWhoClicked();
		try{
			if(ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(challengersMenuName)){
				if(event.getClick()==ClickType.RIGHT){
					if(event.getSlot()>=0||event.getSlot()<=inventorySize && event.getCurrentItem()!=null 
							&& event.getCurrentItem().hasItemMeta()){
								try {
									placeBet(event,better);
								} catch (BetNotFoundException e) {
									event.setCancelled(true);
									better.closeInventory();
									better.sendMessage(ChatColor.DARK_RED+"L'evento scommessa selezionato non esiste piu'.");
								}
							}
				}else{
					event.setCancelled(true);
					better.closeInventory();
					better.sendMessage(ChatColor.DARK_RED+"Devi clickare col tasto destro sui libri!");
				}

			}
			
			
		}catch(IndexOutOfBoundsException e){
			event.setCancelled(true);
			better.closeInventory();
			better.sendMessage(ChatColor.DARK_RED+"Devi clickare sui libri in alto per scommettere!");
		}
	}
	
	public void openPlaceBetMenu(Player better){
		Inventory placeBetMenu=Bukkit.createInventory(null, inventorySize, challengersMenuName);
		int i=0;
		for(Challenger c: b.getChallengers()){
			int challengerNum=1;
			ItemStack betButtonChallenger = new ItemStack(Material.BOOK);
			ItemMeta betDescription= betButtonChallenger.getItemMeta();
			betDescription.setDisplayName(ChatColor.RED+b.getName());
			betDescription.setLore(Arrays.asList(ChatColor.RED+"SFIDANTE "+challengerNum,ChatColor.GREEN+ c.getName(),
					ChatColor.GREEN +"Quota: "+ ChatColor.WHITE+c.getQuote(), ChatColor.GREEN+"#Scommesse: " + ChatColor.WHITE+c.getBetNumber(),
					 ChatColor.GREEN+"La tua scommessa: " + ChatColor.GOLD+c.getBetAmount(better), "Stato Scommesse: " +b.displayStatus(),
					ChatColor.AQUA+
					"Click destro per scommettere su " +ChatColor.WHITE+c.getName()));
			betButtonChallenger.setItemMeta(betDescription);
			placeBetMenu.setItem(i, betButtonChallenger);
			challengerNum++;
			i++;	
		}
		
		//e finalmente...inventario
		better.openInventory(placeBetMenu);
	}
	
	private void placeBet(InventoryClickEvent event, Player better) throws BetNotFoundException{
		Bet b=plugin.getBetHandler().getBetByName(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		String challengerName=ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1));
		better.closeInventory();
		String[] args={"placebet", b.getName(), challengerName };
		CommandInterface cmd=plugin.getCommandHandler().getCommandById("placebet");
		cmd.execute(better, args);
	}
	
	
}


