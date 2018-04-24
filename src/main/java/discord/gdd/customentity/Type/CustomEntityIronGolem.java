package discord.gdd.customentity.Type;

import discord.gdd.customentity.MobBase;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityIronGolem extends EntityIronGolem implements MobBase{

	Location loc;
	
	public CustomEntityIronGolem(World world) {
		super(world);
	}


}
