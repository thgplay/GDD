import discord.gdd.Main;

import java.util.ArrayList;
import java.util.List;

public class ExemploJsonConfig {
    /**
     * Adicionar valores padrao na config, caso estes nao existam
     */
    public ExemploJsonConfig() {
        Main.getConfig().addDefault("magnata", "Heroslender");
        Main.getConfig().addDefault("tag.chat", "&a[Magnata] ");
        List<String> lista = new ArrayList<>();
        lista.add("Item 1");
        lista.add("Item 2");
        lista.add("Item 213");
        Main.getConfig().addDefault("exemplo.lista", lista);

        // Recarregar a config
        Main.getConfig().reload();
    }

    /**
     * pegar uma String da config
     * @return
     */
    public String getMagnata(){
        return Main.getConfig().getString("magnata", "Ninguem");
    }

    /**
     * pegar uma lista de Strings da config
     * @return
     */
    public List<String> getLista(){
        return Main.getConfig().getStringList("exemplo.lista");
    }
}
