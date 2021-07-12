package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.MineBet;

public class GuideBookCommand extends CommandPattern {

	private MineBet plugin;
	public GuideBookCommand(MineBet plugin) {
		super("bet", "guidebook");
		this.plugin=plugin;
		setDescription("Receive a brief guide to MineBet");
		setUsage("/bet guidebook");
		setArgumentRange(1, 2);
		setIdentifier("guidebook");
		setPermission("bet.command.guidebook");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		if(!executor.getInventory().contains(plugin.getGuideBook())){
		executor.getInventory().addItem(plugin.getGuideBook());
		executor.sendMessage(ChatColor.GOLD+"[Minebet] You successfully received the short guide to MineBet!");
		return true;
		}else {
			executor.sendMessage(ChatColor.DARK_RED+"[Minebet] You already have the MineBet short guide!");
			return true;
		}
	}

}
