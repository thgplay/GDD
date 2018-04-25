package discord.gdd.database;
/*
Criado pelo Frach - R.I.P. Avicii#6234 - Membro da GDD
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Mysql {
    private Connection conn;
    private Statement stmt;

    private Mysql(final String urlconn) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(urlconn);
            this.stmt = this.conn.createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Mysql load(final String host, final String database, final String user, final String pass) {
        return new Mysql("jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + pass);
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
