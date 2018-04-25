package discord.gdd.forge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import discord.gdd.Main;
import discord.gdd.utils.RunnableAPI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.messaging.PluginMessageListener;


/* NOMER: FORGE
*  CRIADOR: WILJAFOR1
*  DESCRICAO: CONSEGUIR LISTAR MOD, MANDAR COMANDO PARA ALGUM MOD ESPECIFICO
*/

public class ForgeAPI implements Listener, PluginMessageListener {

	private Main plugin = Main.getInstance();
	@Getter private static Manager manager = new Manager();
	
	public void setup() {
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "FML|HS");
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "FML|HS", this);
	}

	@EventHandler
	public void onChannel(PlayerRegisterChannelEvent event) {
		if (event.getChannel().equals("FORGE")) {
			event.getPlayer().sendPluginMessage(plugin, "FML|HS", new byte[] { -2, 0 });
			event.getPlayer().sendPluginMessage(plugin, "FML|HS", new byte[] { 0, 2, 0, 0, 0, 0 });
			event.getPlayer().sendPluginMessage(plugin, "FML|HS", new byte[] { 2, 0, 0, 0, 0 });
		}
	}
	
	@EventHandler
	public void kickmod(PlayerJoinEvent e) {
		RunnableAPI run = Main.getRunnable();
		run.createTaskLater(RunnableAPI.Type.SYNC, e.getPlayer().getUniqueId()+"kick", new Runnable() {
			@Override
			public void run() {
				if(getManager().getProcurarMod(e.getPlayer(), "Schematica") != null) {
					Mod m = getManager().getProcurarMod(e.getPlayer(), "Schematica");
					e.getPlayer().kickPlayer("�eNOMEDOSERVER \n �4Nosso Sistema Detectou uso de mod. \n �2Mod:"+m.getName()+" \n �4[Criado por Wiljafor1]");
				}
			}
		}, 20*2);
	}
	
	@EventHandler
	public void quitmod(PlayerQuitEvent e) {
		if(getManager().get(e.getPlayer()) != null) {
			getManager().delete(e.getPlayer());
		}
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] data) {
		if (data[0] == 2) {
			ArrayList<Mod> modlist = new ArrayList<Mod>();
			Map<String, String> mods = getData(data);
			for (Map.Entry<String, String> mod : mods.entrySet())
			{
			  String nome = mod.getKey();
			  String versao = mod.getValue();
			  Mod modforge = new Mod(nome, versao);
			  modlist.add(modforge);
			}
			getManager().add(player, modlist);
		}
	}
	
	private Map<String, String> getData(byte[] data) {
		Map<String, String> resultado = new HashMap<>();
		boolean store = false;
		String tempName = null;
		for (int i = 2; i < data.length; store = !store) {
			int end = i + data[i] + 1;
			byte[] range = Arrays.copyOfRange(data, i + 1, end);
			String string = new String(range, StandardCharsets.UTF_8);
			if (store) {
				resultado.put(tempName, string);
			} else {
				tempName = string;
			}
			i = end;
		}
		return resultado;
	}

	public static class Manager{
		private HashMap<Player, ArrayList<Mod>> modlistplayer = new HashMap<Player, ArrayList<Mod>>();
		/*
		 * FUNCAO: ADICIONA O PLAYER NO MANAGER.
		 */
		public void add(Player p, ArrayList<Mod> mods) {
			Main.getInstance().getServer().getPluginManager().callEvent(new EventReceiveModList(p, mods));
			modlistplayer.put(p, mods);
		}

		/*
		 * FUNCAO: PEGA TODOS OS MODS DO PLAYER
		 * RETORNA: LISTA DE MODS
		 */
		public ArrayList<Mod> get(Player p) {
			return modlistplayer.get(p);
		}

		/*
		 * FUNCAO: VERIFICA SE O PLAYER TEM UM MOD ESPECIFICO.
		 * RETORNA: OBJETO MOD
		 */
		public Mod getProcurarMod(Player p, String nome) {
			if (modlistplayer.get(p) != null) {
				ArrayList<Mod> mods = modlistplayer.get(p);
				for(Mod mod : mods) {
					if(mod.getName().contains(nome)) {
						return mod;
					}
				}
			}
			return null;
		}

		/*
		 * FUNCAO: TIRA O PLAYER NO MANAGER.
		 */
		public void delete(Player p) {
			modlistplayer.remove(p);
		}
	}
}