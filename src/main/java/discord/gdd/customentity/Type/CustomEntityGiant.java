package discord.gdd.customentity.Type;


import discord.gdd.Main;
import discord.gdd.customentity.MobBase;
import discord.gdd.customentity.MobUtils;

import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityGiant extends EntityGiantZombie implements MobBase{

	public CustomEntityGiant (World world) {
		super(world);
	}

	public CustomEntityGiant(World world, MobUtils.TipoDeInteligencia tipo){
		super(world);
		Main.getMobUtils().setarPathfinder(this,goalSelector,targetSelector,tipo);
	}

}
