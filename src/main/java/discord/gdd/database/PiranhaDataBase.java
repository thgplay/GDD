package discord.gdd.database;

import java.io.*;
import java.sql.*;

/*
Criado pelo Frach - R.I.P. Avicii#6234 - Membro da GDD
*/

public class PiranhaDataBase
{
    private Connection conn;
    private File file;
    private Statement stmt;
    
    private PiranhaDataBase(final File f) {
        this.file = f;
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.file);
            this.stmt = this.conn.createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private PiranhaDataBase(final String urlconn) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(urlconn);
            this.stmt = this.conn.createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PiranhaDataBase load(final File f) {
        return new PiranhaDataBase(f);
    }
    
    public static PiranhaDataBase load(final String f) {
        return new PiranhaDataBase(new File(f));
    }
    
    public static PiranhaDataBase load(final String host, final String database, final String user, final String pass) {
        return new PiranhaDataBase("jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + pass);
    }
    
    public void update(final String q) {
        try {
            this.stmt.executeUpdate(q);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet query(final String q) {
        try {
            return this.stmt.executeQuery(q);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public void close() {
        try {
            this.stmt.close();
            this.conn.close();
        }
        catch (Exception ex) {}
    }
    
    public boolean isConnected() {
        try {
            return this.stmt != null && this.conn != null && !this.stmt.isClosed() && !this.conn.isClosed();
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public Connection getConnection() {
        return this.conn;
    }
}
