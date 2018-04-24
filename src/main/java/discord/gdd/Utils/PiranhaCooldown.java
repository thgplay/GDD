package discord.gdd.Utils;

import java.util.concurrent.TimeUnit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/*
Criado pelo Frach - R.I.P. Avicii#6234 - Membro da GDD
*/

public class PiranhaCooldown {
	private static Table<String, String, Long> cooldowns;

	static {
		PiranhaCooldown.cooldowns = HashBasedTable.create();
	}

	public void addCooldown(final String uuid, final String key, final long delay, final TimeUnit unit) {
		PiranhaCooldown.cooldowns.put(uuid, key, (System.currentTimeMillis() + unit.toMillis(delay)));
	}

	public boolean containsCooldown(final String uuid, final String key) {
		return PiranhaCooldown.cooldowns.contains((Object) uuid, (Object) key);
	}

	public boolean removeCooldown(final String uuid, final String key) {
		if (!PiranhaCooldown.cooldowns.contains((Object) uuid, (Object) key)) {
			return false;
		}
		PiranhaCooldown.cooldowns.remove((Object) uuid, (Object) key);
		return true;
	}

	public long getCooldown(final String uuid, final String key) {
		if (PiranhaCooldown.cooldowns.contains((Object) uuid, (Object) key)) {
			return -1L;
		}
		return (long) PiranhaCooldown.cooldowns.get((Object) uuid, (Object) key);
	}

	public long getMillisLeft(final String player, final String key) {
		if (!PiranhaCooldown.cooldowns.contains((Object) player, (Object) key)) {
			return 0L;
		}
		if ((long) PiranhaCooldown.cooldowns.get((Object) player, (Object) key) <= System.currentTimeMillis()) {
			PiranhaCooldown.cooldowns.remove((Object) player, (Object) key);
			return 0L;
		}
		return (long) PiranhaCooldown.cooldowns.get((Object) player, (Object) key) - System.currentTimeMillis();
	}

	public int getSecondsLeft(final String playerName, final String key) {
		return (int) TimeUnit.MILLISECONDS.toSeconds(this.getMillisLeft(playerName, key)) + 1;
	}

	public String getTimeLeft(final String player, final String key) {
		final StringBuilder sb = new StringBuilder();
		final int totalSeconds = this.getSecondsLeft(player, key);
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		int aux = totalSeconds;
		if (totalSeconds >= 3600) {
			hours = totalSeconds / 3600;
			aux -= hours * 3600;
			sb.append((hours + hours == 1) ? (String.valueOf(String.valueOf(hours)) + " hora ")
					: (String.valueOf(String.valueOf(hours)) + " horas "));
		}
		if (aux >= 60) {
			minutes = aux / 60;
			aux -= minutes * 60;
			sb.append((minutes + minutes == 1) ? (String.valueOf(String.valueOf(minutes)) + " minuto ")
					: (String.valueOf(String.valueOf(minutes)) + " minutos "));
		}
		if (aux > 0) {
			seconds = aux;
			aux -= seconds;
			sb.append((seconds + seconds == 1) ? (String.valueOf(String.valueOf(seconds)) + " segundo")
					: (String.valueOf(String.valueOf(seconds)) + " segundos"));
		}
		return sb.toString().substring(0, sb.toString().length());
	}
}
