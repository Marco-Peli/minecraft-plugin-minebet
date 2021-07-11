package eu.mapleconsulting.minebet;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mapleconsulting.minebet.bet.Bet;
import eu.mapleconsulting.minebet.bet.Challenger;
import eu.mapleconsulting.minebet.commands.CommandInterface;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;

public class BetEventsMenu implements Listener {

	private MineBet plugin;
	private final String betEventsMenuName="Eventi MineBet";
	private final int inventorySize=45;

	public BetEventsMenu(MineBet plugin){
		this.plugin=plugin;
	}
	@EventHandler
	public void interact(PlayerInteractEvent event){
		Player player=event.getPlayer();
		Action action=event.getAction();
		if(player.getItemInHand().getType()== Material.STICK && (action.equals(Action.RIGHT_CLICK_AIR))){
			if(!plugin.getBetHandler().getBetList().isEmpty()){
				openBetEventsMenu(player);
			}else player.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+ "Nessun evento scommessa in corso");
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Inventory eventsMenu=event.getInventory();
		Player better= (Player) event.getWhoClicked();
		try{
			if(ChatColor.stripColor(event.getView().getTitle()).equalsIgnoreCase(betEventsMenuName)){
				if(event.getClick()==ClickType.RIGHT){
					if(event.getSlot()>=0||event.getSlot()<=inventorySize && event.getCurrentItem()!=null 
							&& event.getCurrentItem().hasItemMeta()){
								try {
									placeBet(event,better);
								} catch (BetNotFoundException e) {
									event.setCancelled(true);
									better.closeInventory();
									better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"L'evento scommessa selezionato non esiste piu'.");
								}
							}
				}else{
					event.setCancelled(true);
					better.closeInventory();
					better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"Devi clickare col tasto destro sui libri!");
				}

			}
		}catch(IndexOutOfBoundsException e){
			event.setCancelled(true);
			better.closeInventory();
			better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"Devi clickare sui libri in alto per scommettere!");
		}
	}


	private void openBetEventsMenu(Player p){
		Inventory betEventsMenu= Bukkit.createInventory(null, inventorySize, ChatColor.RED+ betEventsMenuName);
		int i=0;
		for(Bet b : plugin.getBetHandler().getBetList()){
			int challengerNum=1;
			for(Challenger c: b.getChallengers()){
				ItemStack betButtonChallenger = new ItemStack(Material.BOOK);
				ItemMeta betDescription= betButtonChallenger.getItemMeta();
				betDescription.setDisplayName(ChatColor.RED+b.getName());
				betDescription.setLore(Arrays.asList(ChatColor.RED+"SFIDANTE "+challengerNum,ChatColor.GREEN+ c.getName(),
						ChatColor.GREEN +"Quota: "+ ChatColor.WHITE+c.getQuote(), ChatColor.GREEN+"#Scommesse: " + ChatColor.WHITE+c.getBetNumber(),
						ChatColor.GREEN+"La tua scommessa: " + ChatColor.GOLD+c.getBetAmount(p), "Stato Scommesse: " +b.displayStatus(),
						ChatColor.AQUA+
						"Click destro per scommettere su " +ChatColor.WHITE+c.getName()));
				betButtonChallenger.setItemMeta(betDescription);
				betEventsMenu.setItem(i, betButtonChallenger);
				challengerNum++;
				i++;	
			}
			i++;
		}
		p.openInventory(betEventsMenu);
	}
	
	private void placeBet(InventoryClickEvent event, Player better) throws BetNotFoundException{
		Bet b=plugin.getBetHandler().getBetByName(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		String challengerName=ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1));
		better.closeInventory();
		String[] args={"placebet", b.getName(), challengerName };
		CommandInterface cmd=plugin.getCommandHandler().getCommandById("placebet");
		if(better.hasPermission(cmd.getPermission())){
		cmd.execute(better, args);
		}else{
			better.sendMessage(ChatColor.WHITE+"[MineBet] "+
						ChatColor.DARK_RED+ plugin.getCommandHandler().getInvalidPermissions());
		}
	}
}
