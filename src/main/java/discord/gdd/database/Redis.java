package discord.gdd.database;

import com.google.gson.Gson;
import discord.gdd.Main;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.concurrent.TimeUnit;

public class Redis {
/*
Criando api para redis :D - Wiljafor1
Usando video de exemplo : https://www.youtube.com/watch?v=Fx8kgg6aVzM
*/

    protected String _HOST;
    protected int _PORT;
    protected String _PASS;
    protected String _DATABASE;
    private Jedis _CLIENT;
    private boolean _ENABLE;
    private Gson gson;

    public Redis(String host, int port, String pass){
        this._HOST = host;
        this._PORT = port;
        this._PASS = pass;
        this.gson = new Gson();
        //this._DATABASE = db;
    }

    public void start() {
        try {
            _CLIENT = new Jedis(_HOST, _PORT, 5000);
            if(_PASS != null)_CLIENT.auth(_PASS);
            _ENABLE = true;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void stop() {
        if (_ENABLE) {
            _CLIENT.close();
            _ENABLE = false;
        }
    }

    public void setCache(String path,Object data, int time){//Depois alguem ajeita isso kk ou eu msm faco isso
        Pipeline pipeline = _CLIENT.pipelined();
        pipeline.set(path, ""+gson.toJsonTree(data));
        pipeline.expire(path, time);
        pipeline.expireAt(path, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(time+1));
        Main.getInstance().getServer().getConsoleSender().sendMessage(""+System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(time+1));
        pipeline.persist(path);
        pipeline.sync();
    }

}
