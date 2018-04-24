package discord.gdd.customentity;

import discord.gdd.Utils.Reflection;
import discord.gdd.customentity.Type.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MobUtils {

    Class EntityTypes = Reflection.getMinecraftClass("EntityTypes");
    Class EntityInsentient = Reflection.getMinecraftClass("EntityInsentient");
    Class EntityIronGolem = Reflection.getMinecraftClass("EntityIronGolem");
    Class EntityCreeper = Reflection.getMinecraftClass("EntityCreeper");


    /*
    Reflection da classes das entidades do minecfraft server
     */
    Class EntitySkeleton = Reflection.getMinecraftClass("EntitySkeleton");
    Class EntitySpider = Reflection.getMinecraftClass("EntitySpider");
    Class EntityGiantZombie = Reflection.getMinecraftClass("EntityGiantZombie");
    Class EntityZombie = Reflection.getMinecraftClass("EntityZombie");
    Class EntityPigZombie = Reflection.getMinecraftClass("EntityPigZombie");
    Class EntityEnderman = Reflection.getMinecraftClass("EntityEnderman");
    Class EntityCaveSpider = Reflection.getMinecraftClass("EntityCaveSpider");
    Class EntityBlaze = Reflection.getMinecraftClass("EntityBlaze");
    Class EntityWither = Reflection.getMinecraftClass("EntityWither");
    Class EntityWitch = Reflection.getMinecraftClass("EntityWitch");
    Class EntityPig = Reflection.getMinecraftClass("EntityPig");
    Class EntitySheep = Reflection.getMinecraftClass("EntitySheep");
    Class EntityCow = Reflection.getMinecraftClass("EntityCow");
    Class EntityChicken = Reflection.getMinecraftClass("EntityChicken");
    Class EntityWolf = Reflection.getMinecraftClass("EntityWolf");
    Class EntityMushroomCow = Reflection.getMinecraftClass("EntityMushroomCow");
    Class EntitySnowman = Reflection.getMinecraftClass("EntitySnowman");
    Class EntityOcelot = Reflection.getMinecraftClass("EntityOcelot");
    Class EntityHorse = Reflection.getMinecraftClass("EntityHorse");
    Class EntityVillager = Reflection.getMinecraftClass("EntityVillager");
    Class EntityGuardian = Reflection.getMinecraftClass("EntityGuardian");
    Class EntityRabbit = Reflection.getMinecraftClass("EntityRabbit");
    Class EntityEndermite = Reflection.getMinecraftClass("EntityEndermite");

    public MobUtils getInstance() {
        return new MobUtils();
    }

    public void registerEntity(String name, int id, Class nmsClass,
                               Class customClass) {
        try {

            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)) {
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Aqui registra todos os custom entity.
     */

    public void registrarTodos() {
        registerEntity("IronGolem_Custom", 99, EntityIronGolem, CustomEntityIronGolem.class);
        registerEntity("Creeper_Custom", 50, EntityCreeper, CustomEntityCreeper.class);
        registerEntity("Skeleton_Custom", 51, EntitySkeleton, CustomEntitySkeleton.class);
        registerEntity("Spider_Custom", 52, EntitySpider, CustomEntitySpider.class);
        registerEntity("GiantZombie_Custom", 53, EntityGiantZombie, CustomEntityGiant.class);
        registerEntity("Zombie_Custom", 54, EntityZombie, CustomEntityZombie.class);
        registerEntity("PigZombie_Custom", 57, EntityPigZombie, CustomEntityPigZombie.class);
        registerEntity("Enderman_Custom", 58, EntityEnderman, CustomEntityEnderman.class);
        registerEntity("CaveSpider_Custom", 59, EntityCaveSpider, CustomEntityCaveSpider.class);
        registerEntity("Blaze_Custom", 61, EntityBlaze, CustomEntityBlaze.class);
        registerEntity("Wither_Custom", 64, EntityWither, CustomEntityWither.class);
        registerEntity("Witch_Custom", 66, EntityWitch, CustomEntityWitch.class);
        registerEntity("Pig_Custom", 90, EntityPig, CustomEntityPig.class);
        registerEntity("Sheep_Custom", 91, EntitySheep, CustomEntitySheep.class);
        registerEntity("Cow_Custom", 92, EntityCow, CustomEntityCow.class);
        registerEntity("Chicken_Custom", 93, EntityChicken, CustomEntityChicken.class);
        registerEntity("Wolf_Custom", 95, EntityWolf, CustomEntityWolf.class);
        registerEntity("MushroomCow_Custom", 96, EntityMushroomCow, CustomEntityMushroomCow.class);
        registerEntity("Snowman_Custom", 97, EntitySnowman, CustomEntitySnowGolem.class);
        registerEntity("Ocelot_Custom", 98, EntityOcelot, CustomEntityOcelot.class);
        registerEntity("Horse_Custom", 100, EntityHorse, CustomEntityHorse.class);
        registerEntity("Villager_Custom", 120, EntityVillager, CustomEntityVillager.class);
        registerEntity("Guardian_Custom", 68, EntityGuardian, CustomEntityGuardian.class);
        registerEntity("Rabbit_Custom", 101, EntityRabbit, CustomEntityRabbit.class);
        registerEntity("Endermite_Custom", 67, EntityEndermite, CustomEntityEndermite.class);
    }

    /*
        Pathfinder --
    */

}
