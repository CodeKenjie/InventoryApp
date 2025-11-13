package src;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class MedicineLogs extends JFrame implements ActionListener {
    DefaultTableModel model;
    DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
    JTable table;
    JButton clear;
    JButton save;
    Color bg = new Color(255, 255, 255);
    Color fc = new Color(0, 0, 0);
    Font font = new Font("Arial", Font.BOLD, 14);
    
    MedicineLogs(){
        cr.setHorizontalAlignment(JLabel.CENTER);
        String[] cols = {"id", "Date", "Name", "Medicine Name", "Action", "Quantity"};
        JPanel center = new JPanel(new BorderLayout());
        center.setPreferredSize(new Dimension(1000, 500));
        JScrollPane cs = new JScrollPane(center);
        cs.setViewportView(center);
        JPanel bottom = new JPanel();
        clear = new JButton("CLEAR ALL LOGS");
        clear.addActionListener(this);
        clear.setFocusable(false);
        save = new JButton("save");
        save.setFocusable(false);
        save.addActionListener(this);
        bottom.add(save);
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
            ResultSet rs = stat.executeQuery("SELECT * FROM medicinelogs");

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("Date"), rs.getString("Name"), rs.getString("Medicine"), rs.getString("Action"), rs.getInt("Quantity")});
            }
        } catch(ClassNotFoundException err) {
            err.printStackTrace();
        } catch(SQLException err) {
            err.printStackTrace();
        }
    }
   
    public void saveTableToCSV(JTable table, String fileloc) {
        try {
            FileWriter csv = new FileWriter(fileloc);
            TableModel model = table.getModel();

            csv.write("Medicine Log  Report \n\n");

            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(escapeCSV(model.getColumnName(i)));
                if (i < model.getColumnCount() - 1) {
                    csv.write(",");
                }
            }
            csv.write("\n");
            
            for (int rows = 0; rows < model.getRowCount(); rows++) {
                for(int cols = 0; cols < model.getColumnCount(); cols++) {
                    Object cell = model.getValueAt(rows, cols);
                    csv.write(escapeCSV(cell == null ? "" : cell.toString()));
                    if (cols < model.getColumnCount()) {
                        csv.write(",");
                    }
                }
                csv.write("\n");
            }

            csv.flush();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static String escapeCSV(String value) {
        if(value.contains(",") || value.contains("\"") || value.contains("\n")){
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        } else {
            return value;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == clear){
            try{
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:Inventory.db");
                Statement stat = conn.createStatement();
                stat.executeUpdate("DELETE FROM medicinelogs");

                conn.close();
                model.setRowCount(0);
                this.loadDB(model);
            } catch(ClassNotFoundException err) {
                err.printStackTrace();
            } catch(SQLException err) {
                err.printStackTrace();
            }
        }

        if (e.getSource() == save) {
            JFileChooser choosePath = new JFileChooser();

            if (choosePath.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String filePath = choosePath.getSelectedFile().getAbsolutePath();
                if(!filePath.endsWith(".csv")){
                    filePath += ".csv";
                }
                saveTableToCSV(table, filePath);
            }
            
        }
    }
}
