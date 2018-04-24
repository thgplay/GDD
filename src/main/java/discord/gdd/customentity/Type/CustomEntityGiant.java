package discord.gdd.customentity.Type;

import java.lang.reflect.Field;

import discord.gdd.customentity.MobBase;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityGiant extends EntityGiantZombie implements MobBase{

	public CustomEntityGiant (World world) {
		super(world);
	}


}
