package eu.mapleconsulting.minebet;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import eu.mapleconsulting.minebet.commands.CommandInterface;
import eu.mapleconsulting.minebet.exceptions.BetNotFoundException;

public class MineBetTabListener implements TabCompleter {

	private MineBet plugin;

	public MineBetTabListener(MineBet plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1,
			String arg2, String[] arg3) {
		if(arg3.length==1){
			List<String> completers=new ArrayList<>();
			for(CommandInterface cmd: plugin.getCommandHandler().getCommands()){
				if(cmd.getIdentifier().toLowerCase().startsWith(arg3[0].toLowerCase()) && arg0.hasPermission(cmd.getPermission())){
					completers.add(cmd.getIdentifier());		
				}
			}
			return completers;
		}
		if(arg3.length==2 && (!arg3[0].equalsIgnoreCase("createvent")) && (!arg3[0].equalsIgnoreCase("default"))){
			List<String> completers=new ArrayList<>();
			for(String betEvent: plugin.getBetHandler().getBetEventNames()){
				if(betEvent.toLowerCase().startsWith(arg3[1].toLowerCase())){
					completers.add(betEvent);		
				}
			}
			return completers;
		}

		if(arg3.length==3 && (!arg3[0].equalsIgnoreCase("closevent"))){
			List<String> completers=new ArrayList<>();
			try {
				for(String challengerName: plugin.getBetHandler().getBetByName(arg3[1]).getChallengerNames()){
					if(challengerName.toLowerCase().startsWith(arg3[2].toLowerCase())){
						completers.add(challengerName);		
					}
				}
			} catch (BetNotFoundException e) {
				return null;
			}
			return completers;
		}
		return null;	
	}

}