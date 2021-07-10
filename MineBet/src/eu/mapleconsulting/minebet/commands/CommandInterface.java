package eu.mapleconsulting.minebet.commands;

import org.bukkit.entity.Player;

public interface CommandInterface {
	
	boolean execute(Player executor, String[] args);

    String getDescription();

    String getIdentifier();

    int getMaxArguments();
    
    String getCommandIntestation();

    int getMinArguments();

    String getName();

    String getPermission();

    String getUsage();

    boolean isThisCommand(String cmdName, String inputIdentifier);
    
    boolean isValidArgsRange(int argslength);
}
