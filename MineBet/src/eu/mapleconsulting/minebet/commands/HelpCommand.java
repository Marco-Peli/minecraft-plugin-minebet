package eu.mapleconsulting.minebet.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import eu.mapleconsulting.minebet.MineBet;

public class HelpCommand extends CommandPattern {

	private List<CommandInterface> availableCommands;
	private int commandsPerPage=6;
	private int pageNumber;
	
	public HelpCommand(MineBet plugin) {
		super("bet", "help");
		availableCommands=plugin.getCommandHandler().getCommands();
		setDescription("Visualizza i comandi di Minebet");
        setUsage("/bet help #pagina[2 pagine]");
        setArgumentRange(1, 2);
        setIdentifier("help");
        setPermission("bet.command.help");
	}

	@Override
	public boolean execute(Player executor, String[] args) {
		try{
			if(args.length==1){
				pageNumber=1;
			}else{
			pageNumber=Integer.parseInt(args[1].trim());
			}
			if(!(pageNumber==1||pageNumber==2)){
				executor.sendMessage(ChatColor.DARK_RED+
						"Numero pagina non valido");
				return true;
			}
			executor.sendMessage(ChatColor.RED+"MINEBET HELP PAGINA " +
					ChatColor.WHITE+""+pageNumber +""+ChatColor.GOLD+":");
			executor.sendMessage("-------------------------------");
			
			return displayHelpCommand(executor);
		}catch(NumberFormatException e){
			executor.sendMessage(ChatColor.DARK_RED+
					"Devi inserire valore intero come numero pagina");
		}
		return true;
		
	
	}

	private boolean displayHelpCommand(Player executor){
		if(pageNumber==1){
			for(int i=0; i<commandsPerPage;i++){
				if(!(availableCommands.get(i).getIdentifier().equalsIgnoreCase("help"))){
					executor.sendMessage(i+1+") "+ChatColor.GOLD+availableCommands.get(i).getUsage()
							+ " "+ ChatColor.WHITE+availableCommands.get(i).getDescription());
				}
			}
			}else if(pageNumber==2){
				for(int i=commandsPerPage; i<availableCommands.size();i++){
					if(!(availableCommands.get(i).getIdentifier().equalsIgnoreCase("help"))){
						executor.sendMessage(i+1+") "+ChatColor.GOLD+availableCommands.get(i).getUsage()
								+ " "+ ChatColor.WHITE+availableCommands.get(i).getDescription());
					}
				}
			}
		return true;
	}
	
}