package discord.gdd.customentity.Type;

import discord.gdd.Main;
import discord.gdd.customentity.MobBase;
import discord.gdd.customentity.MobUtils;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityIronGolem extends EntityIronGolem implements MobBase{
	
	public CustomEntityIronGolem(World world) {
		super(world);
	}

	public CustomEntityIronGolem(World world, MobUtils.TipoDeInteligencia tipo){
		super(world);
		Main.getMobUtils().setarPathfinder(this,goalSelector,targetSelector,tipo);
	}

}
