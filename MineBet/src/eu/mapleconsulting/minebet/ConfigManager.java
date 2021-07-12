package eu.mapleconsulting.minebet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import eu.mapleconsulting.minebet.util.Utils;

public class ConfigManager {

	private final String CONFIG_NAME="config.yml";
	private File configFile;
	private YamlConfiguration customConfig;
	private String configFolderPath;
	private String updateServer;
	private String updateFolderPath;
	private int port;
	private double maxBet;
	private int maxEventLength;
	private int maxBetEvents;
	private int maxChallengersPerBet;
	private boolean lookForUpdates;
	private boolean updateAvailable=false;
	private boolean isUpdating=false;

	//valori default config
	private final double DEFAULT_MAX_BET=200;
	private final int DEFAULT_MAX_EVENT_NAME_LENGTH=20;
	private final int DEFAULT_MAX_BET_EVENTS=5;
	private final int DEFAULT_MAX_CHALLENGERS_PER_BET=3;
	/*private final int DEFAULT_PORT=59666;
	private final String DEFAULT_UPDATE_SERVER="devilkurt.homepc.it";
	private final boolean DEFAULT_LOOK_FOR_UPDATES=true;*/
	
	//percorsi valori deafult config
	private final String MAX_BET_PATH= "Piazzata massima";
	private final String MAX_CHALLENGERS_PER_BET_PATH="Numero massimo di sfidanti per evento scommessa";
	private final String MAX_EVENT_NAME_LENGTH_PATH="Lunghezza nome evento";
	private final String MAX_BET_EVENTS_PATH="Numero massimo di eventi scommessa";
	//private final String DEFAULT_UPDATE_SERVER_PATH="server_update";
	//private final String DEFAULT_PORT_PATH="porta_server_update";
	//private final String DEFAULT_LOOK_FOR_UPDATES_PATH="ricerca aggiornamenti";
	
	
	public ConfigManager(){
		createFolderPaths();
		loadConfig();
	}

	private synchronized void createFolderPaths(){
		configFolderPath="plugins"+File.separator+"MinebetFiles" + File.separator;
		updateFolderPath= "plugins"+File.separator+"MinebetFiles" + File.separator +"Update"+File.separator;
		File configFolder = new File (configFolderPath);
		if(!configFolder.exists()){
			configFolder.mkdirs();
		}
		
		File updateFolder = new File (updateFolderPath);
		if(!updateFolder.exists()){
			updateFolder.mkdirs();
		}

	}

	private void loadConfig(){
		configFile = new File(configFolderPath + CONFIG_NAME);
		customConfig = YamlConfiguration.loadConfiguration(configFile);
		if(!configFile.exists()){
			try {
				createDefaultCfg();
			} catch (IOException e) {
				Utils.printConsoleMsg("Unable to create configuration file");
			}
		}
		try {
			readConfigData();
		} catch (FileNotFoundException e) {
			Utils.printConsoleMsg("No configuratuion file found");
		}
	}

	private void createDefaultCfg() throws IOException{
		Utils.printConsoleMsg("Creating configuration file for first boot...");
		customConfig.set(MAX_BET_PATH, DEFAULT_MAX_BET);
		customConfig.set(MAX_EVENT_NAME_LENGTH_PATH, DEFAULT_MAX_EVENT_NAME_LENGTH);
		customConfig.set(MAX_BET_EVENTS_PATH, DEFAULT_MAX_BET_EVENTS);
		customConfig.set(MAX_CHALLENGERS_PER_BET_PATH, DEFAULT_MAX_CHALLENGERS_PER_BET);
		/*customConfig.set(DEFAULT_LOOK_FOR_UPDATES_PATH, DEFAULT_LOOK_FOR_UPDATES);
		customConfig.set(DEFAULT_UPDATE_SERVER_PATH,DEFAULT_UPDATE_SERVER);
		customConfig.set(DEFAULT_PORT_PATH, DEFAULT_PORT);*/
		customConfig.save(configFile);
		Utils.printConsoleMsg("Configuration file for first boot created!");
	}

	private void readConfigData() throws FileNotFoundException{
		maxBet=customConfig.getDouble(MAX_BET_PATH);
		maxEventLength=customConfig.getInt(MAX_EVENT_NAME_LENGTH_PATH);
		maxBetEvents=customConfig.getInt(MAX_BET_EVENTS_PATH);
		maxChallengersPerBet=customConfig.getInt(MAX_CHALLENGERS_PER_BET_PATH);
		/*lookForUpdates=customConfig.getBoolean(DEFAULT_LOOK_FOR_UPDATES_PATH);
		updateServer = customConfig.getString(DEFAULT_UPDATE_SERVER_PATH);
		port=customConfig.getInt(DEFAULT_PORT_PATH);*/
	}
	

	public double getMaxBet() {
		return maxBet;
	}

	public int getMaxEventLength() {
		return maxEventLength;
	}

	public int getMaxBetEvents() {
		return maxBetEvents;
	}

	public String getConfigFolderPath() {
		return configFolderPath;
	}

	public int getMaxChallengersPerBet() {
		return maxChallengersPerBet;
	}

	public String getUpdateServer() {
		return updateServer;
	}

	public int getPort() {
		return port;
	}

	public boolean isLookForUpdates() {
		return lookForUpdates;
	}

	public boolean isUpdateAvailable() {
		return updateAvailable;
	}

	public void setUpdateAvailable(boolean b) {
		updateAvailable=b;
		
	}

	public String getUpdateFolderPath() {
		return updateFolderPath;
	}

	public boolean isUpdating() {
		return isUpdating;
	}

	public void setUpdating(boolean isUpdating) {
		this.isUpdating = isUpdating;
	}
}


