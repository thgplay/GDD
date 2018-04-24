package discord.gdd.customentity.Type;


import discord.gdd.Main;
import discord.gdd.customentity.MobBase;
import discord.gdd.customentity.MobUtils;

import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityPig extends EntityPig implements MobBase{

	public CustomEntityPig(World world) {
		super(world);
	}

    public CustomEntityPig(World world, MobUtils.TipoDeInteligencia tipo){
        super(world);
        Main.getMobUtils().setarPathfinder(this,goalSelector,targetSelector,tipo);
    }

}
