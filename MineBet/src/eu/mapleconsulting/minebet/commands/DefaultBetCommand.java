package eu.mapleconsulting.minebet.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.mapleconsulting.minebet.MineBet;

public class DefaultBetCommand extends CommandPattern {

	private MineBet plugin;
	
	public DefaultBetCommand(MineBet plugin) {
		super("bet", "default");
		this.plugin=plugin;
		setDescription("Set your default bet");
		setUsage("/bet default <default_bet_amount>");
		setArgumentRange(1, 2);
		setIdentifier("default");
		setPermission("bet.command.default");
	}

	@Override
	public boolean execute(Player executor, String[] args) {	
		if(args.length==1){
			if(plugin.getDefaultBetsManager().getDefaultBets().containsKey(executor.getUniqueId().toString())){
				return plugin.getDefaultBetsManager().displayDefaultBet(executor);
			}
		}else{
			try{
				double defaultBet=Double.parseDouble(args[1]);
				if(defaultBet>plugin.getConfigManager().getMaxBet()){
					executor.sendMessage(ChatColor.DARK_RED+
							"Your default bet is too high. Max allowed: " + plugin.getConfigManager().getMaxBet());
					return true;
				} else{
					return plugin.getDefaultBetsManager().addDefaultBet(executor, defaultBet);
				}
			}catch(NumberFormatException e){
				executor.sendMessage(ChatColor.DARK_RED+
						"The bet amount has to be a number.");
				return true;
			}
		}
		return false;
	}

}
