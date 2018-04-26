package discord.gdd.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

import java.util.Arrays;
import java.util.List;

public class JSONMessage {
    private JsonObject json;

    public JSONMessage() {
        initiateData();
    }

    public JSONMessage(String text) {
        initiateData();
        json.addProperty("text", text);
    }

    public JSONMessage(String text, ChatColor color) {
        initiateData();
        json.addProperty("text", text);
        json.addProperty("color", color.name().toLowerCase());
    }

    private void initiateData() {
        json = new JsonObject();
        json.add("extra", new JsonArray());
    }


    public void addText(String text) {
        addText(text, ChatColor.WHITE);
    }

    public void addText(String text, ChatColor color) {
        JsonObject data = new JsonObject();
        data.addProperty("text", text);
        data.addProperty("color", color.name().toLowerCase());
        getExtra().add(data);
    }

    private JsonArray getExtra() {
        if (!json.has("extra")) json.add("extra", new JsonArray());
        return (JsonArray) json.get("extra");
    }

    public void addInsertionText(String text, ChatColor color, String insertion) {
        JsonObject o = new JsonObject();
        o.addProperty("text", text);
        o.addProperty("color", color.name().toLowerCase());
        o.addProperty("insertion", insertion);
        getExtra().add(o);
    }

    public void addURL(String text, ChatColor color, String url) {
        JsonObject o = new JsonObject();
        o.addProperty("text", text);
        o.addProperty("color", color.name().toLowerCase());

        JsonObject u = new JsonObject();
        u.addProperty("action", "open_url");
        u.addProperty("value", url);

        o.add("clickEvent", u);
        getExtra().add(o);
    }

    public void addHoverText(List<String> lines, String hoveredText) {
        addHoverText(lines, hoveredText, ChatColor.WHITE, true);
    }

    public void addHoverText(List<String> lines, String hoveredText, ChatColor color, boolean bold) {
        JsonObject o = new JsonObject();
        o.addProperty("text", hoveredText);
        o.addProperty("color", color.name().toLowerCase());
        o.addProperty("bold", bold);
        JsonObject u = new JsonObject();
        u.addProperty("action", "show_text");

        String lore = JSONArray.toJSONString(lines);
        lore = lore.replace(":", "|").replace("\\", "").replace(",", "\n").replace("\"", "").replace("'", ",").replace("_", " ");
        lore = lore.substring(1, lore.length() - 1);
        u.addProperty("value", lore);
        o.add("hoverEvent", u);
        getExtra().add(o);

    }


    public void addSuggestCommand(String text, ChatColor color, String cmd) {
        JsonObject o = new JsonObject();
        o.addProperty("text", text);
        o.addProperty("color", color.name().toLowerCase());

        JsonObject u = new JsonObject();
        u.addProperty("action", "suggest_command");
        u.addProperty("value", cmd);

        o.add("clickEvent", u);
        getExtra().add(o);
    }

    public void addRunCommand(String text, ChatColor color, String cmd, String hoverData) {
        JsonObject o = new JsonObject();
        o.addProperty("text", text);
        o.addProperty("color", color.name().toLowerCase());

        JsonObject u = new JsonObject();
        u.addProperty("action", "run_command");
        u.addProperty("value", cmd);
        o.add("clickEvent", u);
        JsonObject a = new JsonObject();
        a.addProperty("action", "show_text");
        a.addProperty("value", hoverData);
        o.add("hoverEvent", a);
        getExtra().add(o);
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public void sendToPlayer(Player p) {
        Class<?> IChatBase = Reflection.getMinecraftClass("IChatBaseComponent");

        Class<?> CraftPlayer = Reflection.getMinecraftClass("CraftPlayer");
        Class<?> PacketClass = Reflection.getMinecraftClass("Packet");
        Class<?> ChatSerializer = Arrays.stream(IChatBase.getDeclaredClasses()).filter(P -> "ChatSerializer".equals(P.getSimpleName())).findFirst().get();

        Object PlayerCast = CraftPlayer.cast(p);

        Object EntityPlayer = Reflection.getMethod(CraftPlayer,"getHandle").invoke(PlayerCast);

        Object Connectton = Reflection.getField("EntityPlayer","playerConnection", Object.class).get(EntityPlayer);

        Object OPacket = Reflection.getConstructor("PacketPlayOutChat",IChatBase)
                .invoke(Reflection.getMethod(ChatSerializer,"a",String.class)
                        .invoke(json.toString()));

        Reflection.getMethod(Connectton.getClass(),"sendPacket", PacketClass).invoke(Connectton,OPacket);



        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(json.toString())));
    }

    public void setColor(ChatColor color) {
        json.addProperty("color", color.name().toLowerCase());
    }

    public void setText(String text) {
        json.addProperty("text", text);
    }

}
