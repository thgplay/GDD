package discord.gdd.forge;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//Evento de quando o server receber a lista de mods do cliente
public class EventReceiveModList extends Event {
	private Player player;
	private ArrayList<Mod> mods;

	public EventReceiveModList(Player player, ArrayList<Mod> mods) {
		this.player = player;
		this.mods = mods;
	}

	private final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Mod> getMods() {
		return mods;
	}

}
