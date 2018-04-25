package discord.gdd.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/*
Criado pelo xpMatt - Membro da GDD
*/

class ScoreBoard {
	
	Player recebidor = null;
	String titulo = ChatColor.GRAY+"Titulo indefinido.";
	
	public ScoreBoard(Player recebidor, String titulo) {
		definirRecebidor(recebidor);
		definirTitulo(titulo);
	}
	
	public void definirRecebidor(Player recebidor) {
		this.recebidor = recebidor;
	}
	
	public void definirTitulo(String titulo) {
		if (titulo.length() > 40) {
			this.titulo = ChatColor.translateAlternateColorCodes('&', titulo.substring(0, 40));
		} else {
			this.titulo = ChatColor.translateAlternateColorCodes('&', titulo);
		}
	}
	
	public void definirLinha(String texto, int linha) {
	    if(b(c(), linha)) {
	        if(a(c(), linha).equalsIgnoreCase(texto)) return;
	        if(!(a(c(), linha).equalsIgnoreCase(texto))) c().getScoreboard().resetScores(a(c(), linha));
	    }
	    c().getScore(texto).setScore(linha);
	}
	
	public void enviar() {
		if (recebidor == null) {
			return;
		}
			
		if(recebidor.getScoreboard().equals(Bukkit.getServer().getScoreboardManager().getMainScoreboard()))
			recebidor.setScoreboard(Bukkit.getServer().getScoreboardManager().getNewScoreboard());
				
		c().setDisplayName(titulo);
			    
		if (c().getDisplaySlot() != DisplaySlot.SIDEBAR) c().setDisplaySlot(DisplaySlot.SIDEBAR);
		recebidor.setScoreboard(recebidor.getScoreboard());
		
	}
	
	private static String a(Objective o, int score) {
	    if(o == null) return null;
	    if(!b(o, score)) return null;
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
	    }
	    return null;
	}
	private static boolean b(Objective o, int score) {
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return true;
	    }
	    return false;
	}
	private Objective c() {
		Scoreboard score = recebidor.getScoreboard();
		return score.getObjective(recebidor.getName()) == null ? score.registerNewObjective(recebidor.getName(), "dummy") : score.getObjective(recebidor.getName());
	}
}
