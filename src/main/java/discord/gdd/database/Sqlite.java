package discord.gdd.database;
/*
Criado pelo Frach - R.I.P. Avicii#6234 - Membro da GDD
*/

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Sqlite {
    private Connection conn;
    private File file;
    private Statement stmt;

    private Sqlite(final File f) {
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

    public static Sqlite load(final File f) {
        return new Sqlite(f);
    }

    public static Sqlite load(final String f) {
        return new Sqlite(new File(f));
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
