package eu.mapleconsulting.minebet.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Book {
	public static ItemStack createBook(){
		ItemStack book=new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta= (BookMeta) book.getItemMeta();
		bookMeta.setPages("Per puntare su un evento utilizzando l'interfaccia grafica, "
				+ "devi prima aver impostato la tua puntata di default col comando /bet default <puntata>", 
				"Una volta scelta la tua puntata, impugna un bastone (stick) e, guardando in aria, "
				+ "premi il tasto destro, ti si aprirà il menu' degli eventi scommessa disponibili (avviati dagli admin!)", "Ogni evento e' "
				+ "rappresentato da una coppia di libri, e ogni libro rappresenta una delle due parti su cui puntare",
				"Scommetti sulla parte che ti interessa premendo il tasto destro "
				+ "del mouse sul libro che rappresenta quella parte, "
				+ "se hai sbagliato non ti proccupare, digita il comando /bet cancel <nome_evento>", "per annullare la scommessa su di esso! "
						+ "Non e' possibile piazzare piu scommesse sullo stesso evento, cancella la scommessa precedente per piazzarne una nuova!",
						"Per avere accesso alla lista completa dei comandi, digita /bet help");
		bookMeta.setAuthor("MineBet");
		bookMeta.setTitle("Guida a Minebet");
		book.setItemMeta(bookMeta);
		return book;
	}
}
