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
	private final String betEventsMenuName="MineBet events";
	private final int inventorySize=45;

	public BetEventsMenu(MineBet plugin){
		this.plugin=plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
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
									better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"The selected bet event doesn't exist anymore.");
								}
							}
				}else{
					event.setCancelled(true);
					better.closeInventory();
					better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"You have to right click on books.");
				}

			}
		}catch(IndexOutOfBoundsException e){
			event.setCancelled(true);
			better.closeInventory();
			better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+"You have to right click on books to place a bet!");
		}
		catch(NullPointerException e)
		{
			
		}
	}


	public void openBetEventsMenu(Player p){
		Inventory betEventsMenu= Bukkit.createInventory(null, inventorySize, ChatColor.RED+ betEventsMenuName);
		int i=0;
		for(Bet b : plugin.getBetHandler().getBetList()){
			int challengerNum=1;
			for(Challenger c: b.getChallengers()){
				ItemStack betButtonChallenger = new ItemStack(Material.BOOK);
				ItemMeta betDescription= betButtonChallenger.getItemMeta();
				betDescription.setDisplayName(ChatColor.RED+b.getName());
				betDescription.setLore(Arrays.asList(ChatColor.RED+"OPPONENT "+challengerNum,ChatColor.GREEN+ c.getName(),
						ChatColor.GREEN +"Quota: "+ ChatColor.WHITE+c.getQuote(), ChatColor.GREEN+"#Bets: " + ChatColor.WHITE+c.getBetNumber(),
						ChatColor.GREEN+"Your bet: " + ChatColor.GOLD+c.getBetAmount(p), "Bet status: " +b.displayStatus(),
						ChatColor.AQUA+
						"Right-click to bet on " +ChatColor.WHITE+c.getName()));
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
