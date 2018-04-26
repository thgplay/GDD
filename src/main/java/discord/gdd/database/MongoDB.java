package discord.gdd.database;
/*
Criado por WillianDev em 24.04.18
*/
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {
/*
Exemplo de implementacao com o mongodb
*/

    protected String _URL;
    protected String _USER;
    protected String _PASS;
    protected String _DATABASE;
    private MongoClient _CLIENT;
    private boolean _ENABLE;

    public MongoDB(String url, String user, String pass, String db){
        this._URL = url;
        this._USER = user;
        this._PASS = pass;
        this._DATABASE = db;
    }

    public void start() {
        try {
            //MANDA A PORRA DO MONGO DB CALAR A BOCA
            Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
            mongoLogger.setLevel(Level.OFF);
            _CLIENT = new MongoClient(
                    new MongoClientURI("mongodb://" + _USER + ":" + _PASS + "@" + _URL + "/" + _DATABASE));//

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

    public MongoDatabase getDataBase(String database) {
        return _CLIENT.getDatabase(database);
    }

}
