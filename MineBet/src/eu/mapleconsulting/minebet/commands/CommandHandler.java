package eu.mapleconsulting.minebet.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler {

	private final String notPlayerMessage="Solo un giocatore puo' eseguire questo comando";
	private final String invalidArgsMessage="Argomenti non validi, uso corretto:";
	private final String invalidCommand="Comando non valido, /bet help per i comandi disponibili";
	private final String invalidPermissions="Non hai i permessi per eseguire questo comando";
	private List<CommandInterface> commands;

	public CommandHandler() {
		commands = new ArrayList<>();
	}

	public boolean deploy(CommandSender sender, Command executedCommand
			, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;	
			if(args.length==0) { player.sendMessage(invalidCommand); return true;}
			for (CommandInterface cmd : commands) {
				if (cmd.isThisCommand(executedCommand.getName(), args[0])) {
					if (!cmd.isValidArgsRange(args.length)) {
					player.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+invalidArgsMessage);
					player.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+cmd.getUsage());
					return true;
					}else if(player.hasPermission(cmd.getPermission())){
						return cmd.execute(player,args);
					}else{
						player.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+invalidPermissions);
						return true;
					}
				}
			}player.sendMessage(invalidCommand);	
		}else{
			sender.sendMessage(notPlayerMessage);
		}
		return true;
	}

	public void addCommand(CommandInterface command){
		this.commands.add(command);
	}

	/**
	 * @return the commands
	 */
	public List<CommandInterface> getCommands() {
		return commands;
	}
	
	public String getInvalidPermissions() {
		return invalidPermissions;
	}

	public List<String> getCommandsIdentifier(){
		List<String> identifiers=new ArrayList<>();
		for(CommandInterface c: commands){
			identifiers.add(c.getIdentifier());
		}
		return identifiers;
	}
	
	public CommandInterface getCommandById(String identifier){
		for(CommandInterface cmd: commands){
			if(cmd.getIdentifier().equalsIgnoreCase(identifier)){
				return cmd;
			}
		}
		return null;
	}

}
