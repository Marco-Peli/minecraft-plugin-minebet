package eu.mapleconsulting.minebet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DefaultBetsManager {
	private final String prefix="bets.";
	private File betsFile;
	private final double useless=0;
	private Map<String, Double> defaultBets;
	private FileConfiguration betsConfig;
	private final String BETS_FILENAME="DefaultBets.yml";
	private MineBet plugin;
	
	public DefaultBetsManager(MineBet plugin){
		this.plugin=plugin;
		try {
			betsFile=new File(this.plugin.getConfigManager().getConfigFolderPath()+ BETS_FILENAME);
			betsConfig = YamlConfiguration.loadConfiguration(betsFile);
			createBetsFile();
			loadBets();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadBets(){
		defaultBets=new HashMap<>();
		for(String uuid: betsConfig.getConfigurationSection(prefix).getKeys(false)){
			String betsPath=prefix+uuid;
			double defaultBet=betsConfig.getDouble(betsPath);
			defaultBets.put(uuid,defaultBet);
		}
		
	}
	
	public boolean addDefaultBet(Player better, double defaultBet){
		betsConfig.set(prefix+better.getUniqueId().toString(), defaultBet);
		defaultBets.put(better.getUniqueId().toString(), defaultBet);
		try {
			betsConfig.save(betsFile);
			//loadBets();
		} catch (IOException e) {
			e.printStackTrace();
		}
		better.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+ "L'ammontare della tua puntata di default e' ora di: " +
				ChatColor.WHITE+ defaultBets.get(better.getUniqueId().toString()) + ChatColor.GOLD+ " "+plugin.getEcon().currencyNamePlural());
		return true;
	}
	
	public boolean displayDefaultBet(Player executor){
		if(!isDefaultBet(executor.getUniqueId().toString())){
			executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.DARK_RED+
					"Non hai ancora inserito nessuna puntata di default");
			return true;
		}
		executor.sendMessage(ChatColor.WHITE+"[MineBet] "+ChatColor.GOLD+ "L'ammontare della tua scommessa di default e': " +
					ChatColor.WHITE+ defaultBets.get(executor.getUniqueId().toString()) + ChatColor.GOLD+ " "+plugin.getEcon().currencyNamePlural());
		return true;
	}
	
	private void createBetsFile() throws IOException{
		if(!betsFile.exists()){
			betsConfig.set(prefix+"Lista delle puntate di default", useless);
			betsConfig.save(betsFile);
			System.out.println("[MineBet] Registro puntate default creato");
		}
	}
	
	public boolean isDefaultBet(String playerUUID){
		return defaultBets.containsKey(playerUUID);
	}

	public Map<String, Double> getDefaultBets() {
		return defaultBets;
	}
}
