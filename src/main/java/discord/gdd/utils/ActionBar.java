package discord.gdd.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/*
Criado pelo Reityy#1427 - Membro da GDD
*/

public class ActionBar {
	
    public static void sendAction(Player p, String message){
    	 Class<?> playOutChat, baseComponent, chatMsg;
    	 Constructor<?> chatConstructor, playOutConstructor;
    	 
    	 try {
    		 message = ChatColor.translateAlternateColorCodes('&', message);
    		 
        	 playOutChat = getNMSClass("PacketPlayOutChat");
        	 baseComponent = getNMSClass("IChatBaseComponent");
        	 chatMsg = getNMSClass("ChatMessage");
        	 
        	 chatConstructor = chatMsg.getDeclaredConstructor(String.class, Object[].class);
        	 playOutConstructor = playOutChat.getConstructor(baseComponent, Byte.TYPE);
        	 
        	 Object ichatbc = chatConstructor.newInstance(message, new Object[0]);
        	 Object packet = playOutConstructor.newInstance(ichatbc, (byte)2);
        	 sendPacket(p, packet);
        	 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private static void sendPacket(Player p, Object packet){
		try {
			Object handle = p.getClass().getMethod("getHandle").invoke(p);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Class<?> getNMSClass(String name){
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}

