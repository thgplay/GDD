package discord.gdd.customentity.Type;

import java.lang.reflect.Field;

import discord.gdd.customentity.MobBase;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityPigZombie extends EntityPigZombie implements MobBase{

	public CustomEntityPigZombie(World world) {
		super(world);
	}



}
