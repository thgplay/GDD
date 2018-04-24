package discord.gdd.Utils;
import discord.gdd.Main;
import lombok.Getter;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

/*
Criado por Wiljafor1
*/

public class HologramApi
{
    @Getter static HologramApi instance;

    public ArmorStand criarHolograma(Location loc, String texto) {
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        a.setSmall(true);
        a.setMarker(true);
        a.setVisible(false);
        a.setGravity(false);
        a.setCustomName(texto);
        a.setCustomNameVisible(true);
        return a;
    }
    
    public void criarHologramaTemp(Location loc, int time, String... s) {
        for (String str : s) {
            ArmorStand a = criarHolograma(loc.add(0.0, 0.08, 0.0), str);
            doAction(() -> a.remove(), time);
        }
    }
    
    public void doAction(Runnable r, int i)
    {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), r, i);
    }

    public void criarHologramaPacket(Player p,String text, int time,Location loc) {
    	CraftWorld nms = ((CraftWorld)p.getWorld());
    	EntityArmorStand a = new EntityArmorStand(nms.getHandle(), loc.getX(), loc.getY(), loc.getZ());
    	a.setCustomName(text);
    	a.setCustomNameVisible(true);
    	a.setInvisible(true);
    	a.setGravity(false);
    	((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(a));
    	RunnableAPI run = Main.getRunnable();
    	run.createTaskLater(RunnableAPI.Type.ASYNC, text+"holo", new Runnable() {
			@Override
			public void run() {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(a.getBukkitEntity().getEntityId()));
			}
		}, 20*time);
    }
}