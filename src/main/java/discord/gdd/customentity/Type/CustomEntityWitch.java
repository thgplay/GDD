package discord.gdd.customentity.Type;

import java.lang.reflect.Field;

import discord.gdd.customentity.MobBase;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityWitch extends EntityWitch implements MobBase{

	public CustomEntityWitch(World world) {
		super(world);
	}


}
