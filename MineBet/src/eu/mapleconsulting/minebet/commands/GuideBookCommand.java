package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.MineBet;

public class GuideBookCommand extends CommandPattern {

	private MineBet plugin;
	public GuideBookCommand(MineBet plugin) {
		super("bet", "guidebook");
		this.plugin=plugin;
		setDescription("Ricevi una breve guida all'interfaccia di minebet");
		setUsage("/bet guidebook");
		setArgumentRange(1, 2);
		setIdentifier("guidebook");
		setPermission("bet.command.guidebook");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		if(!executor.getInventory().contains(plugin.getGuideBook())){
		executor.getInventory().addItem(plugin.getGuideBook());
		executor.sendMessage(ChatColor.GOLD+"[Minebet] Hai ricevuto la breve guida all'interfaccia di Minebet!");
		return true;
		}else {
			executor.sendMessage(ChatColor.DARK_RED+"[Minebet] Possiedi gia' la guida all'interfaccia di!");
			return true;
		}
	}

}
