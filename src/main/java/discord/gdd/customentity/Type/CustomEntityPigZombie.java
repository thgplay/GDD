package discord.gdd.customentity.Type;


import discord.gdd.Main;
import discord.gdd.customentity.MobBase;
import discord.gdd.customentity.MobUtils;

import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityPigZombie extends EntityPigZombie implements MobBase{

	public CustomEntityPigZombie(World world) {
		super(world);
	}

    public CustomEntityPigZombie(World world, MobUtils.TipoDeInteligencia tipo){
        super(world);
        Main.getMobUtils().setarPathfinder(this,goalSelector,targetSelector,tipo);
    }

}
