package eu.mapleconsulting.minebet.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Book {
	public static ItemStack createBook(){
		ItemStack book=new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta= (BookMeta) book.getItemMeta();
		bookMeta.setPages("To place a bet on an event with the graphical interfac, "
				+ "you have to set your default bet with /bet default <bet_amount>", 
				"Once set the bet default amount, bear a stick (stick) and, looking in the air, "
				+ "right click, a menu listing all running events will open", "Each event is composed of "
				+ "a group of books, and each book represents an opponent you can place a bet on",
				"Bet on the desired opponent by right clicking on the book that represents it, "
				+ "if you make a mistake don't worry, type /bet cancel <event_name>", "to cancel the bet on it! "
						+ "If you place a bet on a different opponent while having a bet on another one, the old bet will be deleted!",
						"To get help about all commands, type /bet help");
		bookMeta.setAuthor("MineBet");
		bookMeta.setTitle("Guide to MineBet");
		book.setItemMeta(bookMeta);
		return book;
	}
}
