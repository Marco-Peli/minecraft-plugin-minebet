package eu.mapleconsulting.minebet.commands;

import org.bukkit.entity.Player;

public abstract class CommandPattern implements CommandInterface{
	
	private String intestation;
	private String name;
    private String description;
    private String usage;
    private String permission;
    private String identifier;
    private int minArguments;
    private int maxArguments;

    public CommandPattern(String intestation,String name) {
        this.name = name;
        this.intestation=intestation;
        description = "";
        usage = "";
        permission = "";
        identifier="";
        minArguments = 0;
        maxArguments = 0;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public String getCommandIntestation(){
    	return intestation;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int getMaxArguments() {
        return maxArguments;
    }

    @Override
    public int getMinArguments() {
        return minArguments;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public boolean isThisCommand(String cmdName, String inputIdentifier) {
            return (inputIdentifier.equalsIgnoreCase(identifier) 
            		&& cmdName.equalsIgnoreCase(intestation));
    }
    
    @Override
    public boolean isValidArgsRange(int argsLength){
    	if(minArguments==maxArguments) return argsLength==minArguments;
    	return (argsLength>=minArguments && argsLength<=maxArguments);
    }

    
    public void setArgumentRange(int min, int max) {
        this.minArguments = min;
        this.maxArguments = max;
    }
    
    public void sendInvalidArgsMessage(Player executor)
    {
    	
    }
    public void setDescription(String description) {
        this.description = description;
    }
    

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setUsage(String usage) {
        this.usage = usage;
}
}
