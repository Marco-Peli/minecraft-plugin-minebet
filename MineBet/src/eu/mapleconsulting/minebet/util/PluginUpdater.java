package eu.mapleconsulting.minebet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bukkit.scheduler.BukkitRunnable;

import eu.mapleconsulting.minebet.MineBet;

public class PluginUpdater extends BukkitRunnable {

	private double currentVersion;
	private String pluginName;
	private String updateServer;
	private int port;
	private Socket pluginSocket;
	private BufferedReader in;
	private PrintWriter out;
	private MineBet plugin;
	private final String REQUESTED_SERVICE="LOOKFORUPDATES";

	public PluginUpdater(MineBet plugin) {
		this.plugin=plugin;
		this.currentVersion=plugin.getCurrentVersion();
		this.pluginName=plugin.getPluginName();
		this.updateServer=plugin.getConfigManager().getUpdateServer();
		this.port=plugin.getConfigManager().getPort();
	}

	@Override
	public void run(){
			try {
				estabilishConnection();
				checkForUpdates();
			} catch (UnknownHostException e) {
				System.out.println("[DevilNotes] Indirizzo del server update non valido.");
			} catch (IOException e) {
				System.out.println("[DevilNotes] Errore durante la connessione al server update.");
			}
	}

	private void estabilishConnection() throws UnknownHostException, IOException{
		System.out.println("[MineBet] Ricerca nuove versioni di "+ pluginName+"...");
		pluginSocket = new Socket(updateServer, port);
		System.out.println("[MineBet] Connesso, ricerca nuove versioni di "+ pluginName+"...");
		in =new BufferedReader(new InputStreamReader(pluginSocket.getInputStream()));
		out=new PrintWriter(pluginSocket.getOutputStream(), true);
	}

	private void checkForUpdates() throws NumberFormatException, IOException{
		out.println(REQUESTED_SERVICE);
		out.println(pluginName);
		String line;
		double updatedVersion;
		while(!((line=in.readLine()).equals("END"))){
			updatedVersion=Double.parseDouble(line);
			if(updatedVersion>currentVersion){
				plugin.getConfigManager().setUpdateAvailable(true);
				plugin.setUpdatedVersion(updatedVersion);
				System.out.println("[MineBet] Nuova versione " + updatedVersion+" disponibile!");
			}else{
				System.out.println("[MineBet] E' gia' installata l'ultima versione di  " + pluginName);
				plugin.getConfigManager().setUpdateAvailable(false);
			}
			out.println("OK");
		}
	}

}
