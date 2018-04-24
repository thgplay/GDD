package discord.gdd.customentity.Type;

import discord.gdd.Main;
import discord.gdd.customentity.MobBase;
import discord.gdd.customentity.MobUtils;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.World;

public class CustomEntityOcelot extends EntityOcelot implements MobBase {

    public CustomEntityOcelot(World world) {
        super(world);
    }

    public CustomEntityOcelot(World world, MobUtils.TipoDeInteligencia tipo){
        super(world);
        Main.getMobUtils().setarPathfinder(this,goalSelector,targetSelector,tipo);
    }

}
