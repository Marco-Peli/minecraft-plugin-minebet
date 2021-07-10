package eu.mapleconsulting.minebet;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import eu.mapleconsulting.minebet.bet.BetHandler;
import eu.mapleconsulting.minebet.commands.*;
import eu.mapleconsulting.minebet.util.Book;
import eu.mapleconsulting.minebet.util.PluginUpdater;

public class MineBet extends JavaPlugin {

	private CommandHandler commandHandler;
	private ConfigManager configManager;
	private ItemStack guideBook;
	private DefaultBetsManager defaultBetsManager;
	private BetHandler betHandler;
    public static Economy econ = null; 
    private final double currentVersion=1.10;
    private double updatedVersion;
	private final String pluginName="MineBet";
	private PluginUpdater updater;

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }
    
	public void onEnable() {
		configManager=new ConfigManager();
		defaultBetsManager=new DefaultBetsManager(this);
		betHandler=new BetHandler();
		guideBook=Book.createBook();
		betHandler=new BetHandler();
		addCommands();
		registerTabCompleter();
		setupEconomy();
		registerPlayerListener();
		lookForUpdates();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandHandler.deploy(sender, cmd, label, args);
	}
	
	public void onDisable(){
		getLogger().info("Minebet Stopped");
	}
	
	private void lookForUpdates(){
		if(configManager.isLookForUpdates()){
			updater=new PluginUpdater(this);
			updater.runTaskTimerAsynchronously(this, 10, 1728000);
		}
	}

	private void registerTabCompleter(){
		getCommand("bet").setTabCompleter(new MineBetTabListener(this));
	}
	
	private void registerPlayerListener(){
			getServer().getPluginManager().registerEvents(new BetEventsMenu(this), this);
			getServer().getPluginManager().registerEvents(new OnJoinListener(this), this);
	}
	
	private void addCommands(){
		commandHandler=new CommandHandler();
		commandHandler.addCommand(new CreateBetEventCommand(this));
		commandHandler.addCommand(new CloseEventCommand(this));
		commandHandler.addCommand(new OpenBetsCommand(this));
		commandHandler.addCommand(new CloseBetsCommand(this));
		commandHandler.addCommand(new PlaceBetCommand(this));
		commandHandler.addCommand(new CancelBetCommand(this));
		commandHandler.addCommand(new ListBetsCommand(this));
		commandHandler.addCommand(new HelpCommand(this));
		commandHandler.addCommand(new DefaultBetCommand(this));
		commandHandler.addCommand(new GuideBookCommand(this));
		//commandHandler.addCommand(new UpdateCommand(this));
	}
	
	public Economy getEcon() {
		return econ;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	public DefaultBetsManager getDefaultBetsManager() {
		return defaultBetsManager;
	}

	public ItemStack getGuideBook() {
		return guideBook;
	}

	public BetHandler getBetHandler() {
		return betHandler;
	}

	public double getCurrentVersion() {
		return currentVersion;
	}

	public String getPluginName() {
		return pluginName;
	}

	public double getUpdatedVersion() {
		return updatedVersion;
	}

	public void setUpdatedVersion(double updatedVersion) {
		this.updatedVersion = updatedVersion;
	}

}
