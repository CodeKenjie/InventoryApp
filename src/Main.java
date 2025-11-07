package src;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.io.File;

public class Main {
    public static String name;

    public void CreateSQL(){
        String sql1 = "CREATE TABLE IF NOT EXISTS items (id INTEGER PRIMARY KEY AUTOINCREMENT, Date, Item, Category, Quantity INTEGER, Note, Expiration)";
        String sql2 = "CREATE TABLE IF NOT EXISTS itemslogs (id INTEGER PRIMARY KEY AUTOINCREMENT, Date, Name, Item, Action, Quantity INTEGER)";
        String sql3 = "CREATE TABLE IF NOT EXISTS medicines (id INTEGER PRIMARY KEY AUTOINCREMENT, Date, Medicine, Category, Quantity INTEGER, Note, Expiration)";
        String sql4 = "CREATE TABLE IF NOT EXISTS medicinelogs (id INTEGER PRIMARY KEY AUTOINCREMENT, Date, Name, Medicine, Action, Quantity INTEGER)";
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Inventory.db");
            Statement stat = conn.createStatement();
            stat.addBatch(sql1);
            stat.addBatch(sql2);
            stat.addBatch(sql3);
            stat.addBatch(sql4);
            stat.executeBatch();
            conn.close();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }    
    }
    
    public static void main(String[] args){
        Main sql = new Main();
        File items = new File("Inventory.db");

        if (!items.exists()){
            sql.CreateSQL();
        }

        new ChooseFrame();
    }
    
}
