package discord.gdd.customentity.Type;

import discord.gdd.Main;
import discord.gdd.customentity.MobBase;

import discord.gdd.customentity.MobUtils;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityMushroomCow extends EntityMushroomCow implements MobBase{

	public CustomEntityMushroomCow(World world) {
		super(world);
	}

	public CustomEntityMushroomCow(World world, MobUtils.TipoDeInteligencia tipo){
		super(world);
		Main.getMobUtils().setarPathfinder(this,goalSelector,targetSelector,tipo);
	}

}
