package discord.gdd.config.json.serializers;

import com.google.gson.*;
import discord.gdd.config.json.exceptions.InvalidWorldException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LocationSerializer implements JsonSerializer<Location>, JsonDeserializer<Location> {
    @Override
    public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, String> loc = new HashMap<>();
        loc.put("world", src.getWorld().getName());
        loc.put("x", src.getX() + "");
        loc.put("y", src.getY() + "");
        loc.put("z", src.getZ() + "");
        loc.put("yaw", src.getYaw() + "");
        loc.put("pitch", src.getPitch() + "");
        return context.serialize(loc);
    }

    @Override
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObj = json.getAsJsonObject();
        if (!jObj.has("world")
                || !jObj.has("x") || !jObj.has("y") || !jObj.has("z")
                || !jObj.has("yaw") || !jObj.has("pitch"))
            throw new JsonParseException("Localização invalida!");

        return new Location(getWorld(jObj.get("world").getAsString()),
                jObj.get("x").getAsDouble(),
                jObj.get("y").getAsDouble(),
                jObj.get("z").getAsDouble(),
                jObj.get("yaw").getAsFloat(),
                jObj.get("pitch").getAsFloat()
                );
    }

    private World getWorld(String nome) throws InvalidWorldException {
        World world = Bukkit.getWorld(nome);
        if (world == null) throw new InvalidWorldException(nome);
        return world;
    }
}
