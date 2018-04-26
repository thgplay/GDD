package discord.gdd.database;

import redis.clients.jedis.Jedis;

public class Redis {
/*
Criando api para redis :D - Wiljafor1
Usando video de exemplo : https://www.youtube.com/watch?v=Fx8kgg6aVzM
*/

    protected static String _HOST;
    protected static int _PORT;
    protected static String _PASS;
    protected static String _DATABASE;
    private static Jedis _CLIENT;
    private static boolean _ENABLE;

    public Redis(String host, int port, String pass){
        this._HOST = host;
        this._PORT = port;
        this._PASS = pass;
        //this._DATABASE = db;
    }

    public static void start() {
        try {
            _CLIENT = new Jedis(_HOST, _PORT, 5000);
            if(_PASS != null)_CLIENT.auth(_PASS);
            _ENABLE = true;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static void stop() {
        if (_ENABLE) {
            _CLIENT.close();
        }
    }

}
