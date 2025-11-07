package src;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ItemLogs extends JFrame implements ActionListener {
    DefaultTableModel model;
    DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
    JTable table;
    JButton clear;
    Color bg = new Color(255, 255, 255);
    Color fc = new Color(0, 0, 0);
    Font font = new Font("Arial", Font.BOLD, 14);
    
    ItemLogs(){
        cr.setHorizontalAlignment(JLabel.CENTER);
        String[] cols = {"id", "Date", "Name", "Item Name", "Action", "Quantity"};
        JPanel center = new JPanel(new BorderLayout());
        center.setPreferredSize(new Dimension(1000, 500));
        JScrollPane cs = new JScrollPane(center);
        cs.setViewportView(center);
        JPanel bottom = new JPanel();
        clear = new JButton("CLEAR ALL LOGS");
        clear.addActionListener(this);
        clear.setFocusable(false);
        bottom.add(clear);

        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        model.setColumnIdentifiers(cols);
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setCellRenderer(cr);
        table.getColumnModel().getColumn(1).setCellRenderer(cr);
        table.getColumnModel().getColumn(2).setCellRenderer(cr);
        table.getColumnModel().getColumn(3).setCellRenderer(cr);
        table.getColumnModel().getColumn(4).setCellRenderer(cr);
        table.getColumnModel().getColumn(5).setCellRenderer(cr);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
        JScrollPane sc = new JScrollPane(table);
        sc.setViewportView(table);
        center.add(sc, BorderLayout.CENTER);

        //Themes
        center.setBackground(bg);
        bottom.setBackground(bg);

        table.setRowHeight(30);
        table.setFont(font);

        this.loadDB(model);
        this.add(cs);
        this.add(bottom, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void loadDB(DefaultTableModel model){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Inventory.db");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM itemslogs");

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("Date"), rs.getString("Name"), rs.getString("Item"), rs.getString("Action"), rs.getInt("Quantity")});
            }
        } catch(ClassNotFoundException err) {
            err.printStackTrace();
        } catch(SQLException err) {
            err.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == clear){
            try{
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:Inventory.db");
                Statement stat = conn.createStatement();
                stat.executeUpdate("DELETE FROM itemslogs");

                conn.close();
                model.setRowCount(0);
                this.loadDB(model);
            } catch(ClassNotFoundException err) {
                err.printStackTrace();
            } catch(SQLException err) {
                err.printStackTrace();
            }
        }
    }
}
