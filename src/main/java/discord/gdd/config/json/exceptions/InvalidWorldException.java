package discord.gdd.config.json.exceptions;

import com.google.gson.JsonParseException;

public class InvalidWorldException extends JsonParseException {
    public InvalidWorldException(String worldName) {
        super("O mundo '" + worldName + "' não existe no servidor!");
    }
}
