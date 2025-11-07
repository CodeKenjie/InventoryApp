package src;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.sql.*;

public class Goods extends JFrame implements ActionListener{
    // variables set
    Main main = new Main();
    String url = "jdbc:sqlite:Inventory.db";
    String jdbc = "org.sqlite.JDBC";
    JButton add;
    JButton delete;
    JButton update;
    JButton imprt;
    JButton exprt;
    JButton clear;
    JButton search;
    JButton log;
    JButton logout;
    JTextField item_tf;
    JTextField note_tf;
    JTextField category_tf;
    JTextField search_tf;
    JSpinner expmonth;
    JSpinner expday;
    JSpinner expyear;
    JSpinner month;
    JSpinner day;
    JSpinner year;
    JSpinner quantity_sp;
    JTable ItemTable;
    DefaultTableModel model;
    DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
    int cm = LocalDate.now().getMonthValue();
    int cd = LocalDate.now().getDayOfMonth();
    int cy = LocalDate.now().getYear();

    String[] cols = {"Id", "Date", "Item Name", "Category", "Quantity", "Notes", "Expiration Date"};

    // THEME variables
    Color bg = new Color(255, 255, 255);
    Color fc = new Color(0, 0, 0);
    Font font = new Font("Arial", Font.BOLD, 14);

    Goods(){
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };
        model.setColumnIdentifiers(cols);
        cr.setHorizontalAlignment(JLabel.CENTER);
        ItemTable = new JTable(model);
        ItemTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent args0){
                int i = ItemTable.getSelectedRow();
                String str = (String) model.getValueAt(i, 1);
                String str2 = (String) model.getValueAt(i, 6);
                String[] date = str.split("/");
                String[] edate = str2.split("/");
                int imonth = Integer.valueOf(date[0]); 
                int iday = Integer.valueOf(date[1]); 
                int iyear = Integer.valueOf(date[2]); 
                int emonth = Integer.valueOf(edate[0]); 
                int eday = Integer.valueOf(edate[1]); 
                int eyear = Integer.valueOf(edate[2]); 
                item_tf.setText(model.getValueAt(i, 2).toString());
                category_tf.setText(model.getValueAt(i, 3).toString());
                note_tf.setText(model.getValueAt(i, 5).toString());
                month.setValue(imonth);
                day.setValue(iday);
                year.setValue(iyear);
                expmonth.setValue(emonth);
                expday.setValue(eday);
                expyear.setValue(eyear);
            }
        });
        ItemTable.getColumnModel().getColumn(0).setCellRenderer(cr);
        ItemTable.getColumnModel().getColumn(1).setCellRenderer(cr);
        ItemTable.getColumnModel().getColumn(3).setCellRenderer(cr);
        ItemTable.getColumnModel().getColumn(4).setCellRenderer(cr);
        ItemTable.getColumnModel().getColumn(6).setCellRenderer(cr);
        ItemTable.getColumnModel().getColumn(0).setMinWidth(30);
        ItemTable.getColumnModel().getColumn(0).setMaxWidth(100);
        ItemTable.getColumnModel().getColumn(1).setMinWidth(150);
        ItemTable.getColumnModel().getColumn(1).setMaxWidth(200);
        ItemTable.getColumnModel().getColumn(2).setMinWidth(150);
        ItemTable.getColumnModel().getColumn(2).setMaxWidth(500);
        ItemTable.getColumnModel().getColumn(3).setMinWidth(150);
        ItemTable.getColumnModel().getColumn(4).setMinWidth(70);
        ItemTable.getColumnModel().getColumn(4).setMaxWidth(130);
        ItemTable.getColumnModel().getColumn(6).setMinWidth(150);
        ItemTable.getColumnModel().getColumn(6).setMaxWidth(200);
        
        JPanel MainPanel = new JPanel(null);
        JScrollPane mpsp = new JScrollPane(MainPanel);
        mpsp.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        mpsp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        mpsp.setViewportView(MainPanel);
        MainPanel.setPreferredSize(new Dimension(1000, 260));
        JScrollPane sp = new JScrollPane(ItemTable);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        sp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        sp.setViewportView(ItemTable);
        JPanel TablePanel = new JPanel(new BorderLayout());
        TablePanel.setPreferredSize(new Dimension(1000, 310));
        TablePanel.add(sp, BorderLayout.CENTER);
        JScrollPane msp = new JScrollPane(TablePanel);
        msp.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        msp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        msp.setViewportView(TablePanel);
        
        JPanel actBtns = new JPanel(new GridLayout(1, 6, 5, 0));
        actBtns.setBounds(10, 200, 600, 50);
        add = new JButton("Add");
        add.addActionListener(this);
        add.setFocusable(false);
        delete = new JButton("Delete");
        delete.addActionListener(this);
        delete.setFocusable(false);
        update = new JButton("Update");
        update.addActionListener(this);
        update.setFocusable(false);
        imprt = new JButton("In");
        imprt.addActionListener(this);
        imprt.setFocusable(false);
        exprt = new JButton("Out");
        exprt.addActionListener(this);
        exprt.setFocusable(false);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        clear.setFocusable(false);
        actBtns.add(add);
        actBtns.add(delete);
        actBtns.add(update);
        actBtns.add(imprt);
        actBtns.add(exprt);
        actBtns.add(clear);
        MainPanel.add(actBtns);
        
        JPanel logs = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        log = new JButton("LOGS");
        log.setFocusable(false);
        log.addActionListener(this);
        logout = new JButton("logout");
        logout.addActionListener(this);
        logout.setFocusable(false);
        logs.add(logout);
        logs.add(log);

        JLabel item_l = new JLabel("Item Name:");
        item_l.setBounds(10, 13, 100, 24);
        item_tf = new JTextField();
        item_tf.setBounds(93, 10, 290, 30);
        item_tf.setPreferredSize(new Dimension(150, 30));
        MainPanel.add(item_l);
        MainPanel.add(item_tf);

        JLabel date_l = new JLabel("Date:");
        date_l.setBounds(50, 130, 50, 24);
        month = new JSpinner(new SpinnerNumberModel(cm, 1, 12, 1));
        month.setBounds(93, 130, 50, 24);
        day = new JSpinner(new SpinnerNumberModel(cd, 1, 31, 1));
        day.setBounds(148, 130, 50, 24);
        year = new JSpinner(new SpinnerNumberModel(cy, 2000, 99999, 1));
        year.setBounds(203, 130, 60, 24);
        MainPanel.add(date_l);
        MainPanel.add(month);
        MainPanel.add(day);
        MainPanel.add(year);

        JLabel expiration_l = new JLabel("Exp Date:");
        expiration_l.setBounds(22, 160, 150, 24);
        expmonth = new JSpinner(new SpinnerNumberModel(cm, 1, 12, 1));
        expmonth.setBounds(93, 160, 50, 24);
        expday = new JSpinner(new SpinnerNumberModel(cd, 1, 31, 1));
        expday.setBounds(148, 160, 50, 24);
        expyear = new JSpinner(new SpinnerNumberModel(cy, cy, 99999, 1));
        expyear.setBounds(203, 160, 60, 24);
        MainPanel.add(expiration_l);
        MainPanel.add(expmonth);
        MainPanel.add(expday);
        MainPanel.add(expyear);


        JLabel category_l = new JLabel("Category:");
        category_l.setBounds(20, 53, 100, 24);
        category_tf = new JTextField();
        category_tf.setBounds(93, 50, 290, 30);
        MainPanel.add(category_l);
        MainPanel.add(category_tf);

        JLabel quantity_l = new JLabel("Quantity:");
        quantity_l.setBounds(390, 13, 100, 24);
        quantity_sp = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        quantity_sp.setBounds(460, 10, 50, 30);
        MainPanel.add(quantity_l);
        MainPanel.add(quantity_sp);

        JLabel note_l = new JLabel("Note:");
        note_l.setBounds(50, 93, 50, 24);
        note_tf = new JTextField();
        note_tf.setBounds(93, 90, 290, 30);
        MainPanel.add(note_l);
        MainPanel.add(note_tf);
        
        search_tf = new JTextField();
        search_tf.setBounds(790, 222, 200, 24);
        search = new JButton("SEARCH");
        search.addActionListener(this);
        search.setFocusable(false);
        search.setBounds(680, 220, 100, 28);
        MainPanel.add(search);
        MainPanel.add(search_tf);

        //Themes
        logs.setBackground(bg);
        TablePanel.setBackground(bg);
        MainPanel.setBackground(bg);
        actBtns.setBackground(bg);

        ItemTable.setFont(font);
        ItemTable.setRowHeight(30);
        search_tf.setFont(font);
        note_tf.setFont(font);
        item_tf.setFont(font);
        category_tf.setFont(font);
        
        this.loadDB(model);
        this.add(mpsp, BorderLayout.NORTH);
        this.add(msp, BorderLayout.CENTER);
        this.add(logs, BorderLayout.SOUTH);
        this.setTitle("Goods/Item InventorySystem");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMaximumSize(new Dimension(1000, 610));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true); 
    }
    
    public void loadDB(DefaultTableModel model){
        try{
            Class.forName(jdbc);
            Connection conn = DriverManager.getConnection(url);
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM items");

            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("Date"), rs.getString("Item"), rs.getString("Category"), rs.getInt("Quantity"), rs.getString("Note"), rs.getString("Expiration")});
            }
        } catch (ClassNotFoundException err) {
            err.printStackTrace();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            String item_name = item_tf.getText();
            String cdate = String.valueOf(month.getValue()) + "/" + String.valueOf(day.getValue()) + "/" + String.valueOf(year.getValue());
            String xdate = String.valueOf(expmonth.getValue()) + "/" + String.valueOf(expday.getValue()) + "/" + String.valueOf(expyear.getValue());
            int quantity = (int) quantity_sp.getValue();

            if (item_tf.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please Put Complete Information");
            } else if (quantity_sp.getValue().equals(0)) { 
                JOptionPane.showMessageDialog(null, "Your quantity is 0");
            } else {
                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("INSERT INTO items(Date, Item, Category, Quantity, Note, Expiration) VALUES(?, ?, ?, ?, ?, ?)"); 

                    prep.setString(1, cdate);
                    prep.setString(2, item_name);
                    prep.setString(3, category_tf.getText());
                    prep.setInt(4, quantity);
                    prep.setString(5, note_tf.getText());
                    prep.setString(6, xdate);
                    prep.executeUpdate();

                    conn.close();

                    model.setRowCount(0);
                    this.loadDB(model);
                    item_tf.setText("");
                    category_tf.setText("");
                    note_tf.setText("");
                    quantity_sp.setValue(0);
                    month.setValue(cm);
                    day.setValue(cd);
                    year.setValue(cy);
                    expmonth.setValue(cm);
                    expday.setValue(cd);
                    expyear.setValue(cy);
                    JOptionPane.showMessageDialog(null, item_name + "was Added to your Inventory");
                } catch (ClassNotFoundException err) {
                    err.printStackTrace();
                } catch (SQLException err) {
                    err.printStackTrace();
                }

                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("INSERT INTO itemslogs(Date, Name, Item, Action, Quantity) VALUES(?, ?, ?, ?, ?)");
                    prep.setString(1, cdate);
                    prep.setString(2, main.name);
                    prep.setString(3, item_name);
                    prep.setString(4, "Added");
                    prep.setInt(5, quantity);
                    prep.executeUpdate();

                    conn.close();
                } catch(ClassNotFoundException err) {
                    err.printStackTrace();
                } catch(SQLException err) {
                    err.printStackTrace();
                }
            }
        }

        if (e.getSource() == delete){
            int selrow = ItemTable.getSelectedRow();
            int row = (int) ItemTable.getValueAt(selrow, 0); 
            int quantity =  (int) ItemTable.getValueAt(selrow, 4); 
            String item_name = item_tf.getText();
            String cdate = String.valueOf(month.getValue()) + "/" + String.valueOf(day.getValue()) + "/" + String.valueOf(year.getValue());

            System.out.print(row);

            if (selrow > -1) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + String.valueOf(model.getValueAt(selrow, 2)) + "?", "Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == 0) {
                    try {
                        Class.forName(jdbc);
                        Connection conn = DriverManager.getConnection(url);
                        PreparedStatement prep = conn.prepareStatement("DELETE FROM items WHERE id = ?");
                        
                        prep.setInt(1, row);
                        prep.executeUpdate();
                        conn.close();

                        model.setRowCount(0);
                        this.loadDB(model);

                        JOptionPane.showMessageDialog(null, "Successfully deleted");
                    } catch(ClassNotFoundException err) {
                        err.printStackTrace();
                    } catch(SQLException err) {
                        err.printStackTrace();
                    }

                    try {
                        Class.forName(jdbc);
                        Connection conn = DriverManager.getConnection(url);
                        PreparedStatement prep = conn.prepareStatement("INSERT INTO itemslogs(Date, Name, Item, Action, Quantity) VALUES(?, ?, ?, ?, ?)");
                        prep.setString(1, cdate);
                        prep.setString(2, main.name);
                        prep.setString(3, item_name);
                        prep.setString(4, "Deleted");
                        prep.setInt(5, quantity);
                        prep.executeUpdate();

                        conn.close();
                    } catch(ClassNotFoundException err) {
                        err.printStackTrace();
                    } catch(SQLException err) {
                        err.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Select what you want to delete");
            }
        }

        if (e.getSource() == update) {
            int selrow = ItemTable.getSelectedRow();
            int row = (int) model.getValueAt(selrow, 0);
            int quantity =  (int) ItemTable.getValueAt(selrow, 4); 
            String item_name = item_tf.getText();
            String cdate = String.valueOf(month.getValue()) + "/" + String.valueOf(day.getValue()) + "/" + String.valueOf(year.getValue());
            String xdate = String.valueOf(expmonth.getValue()) + "/" + String.valueOf(expday.getValue()) + "/" + String.valueOf(expyear.getValue());

            if (selrow > -1) {
                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("UPDATE items SET Date = ?, Item = ?, Category = ?, Note = ?, Expiration = ? WHERE id = ?");
                    
                    prep.setString(1, cdate);
                    prep.setString(2, item_name);
                    prep.setString(3, category_tf.getText());
                    prep.setString(4, note_tf.getText());
                    prep.setString(5, xdate);
                    prep.setInt(6, row);
                    
                    prep.executeUpdate();

                    conn.close();
                    model.setRowCount(0);
                    this.loadDB(model);
                    item_tf.setText("");
                    category_tf.setText("");
                    note_tf.setText("");
                    quantity_sp.setValue(0);
                    month.setValue(cm);
                    day.setValue(cd);
                    year.setValue(cy);
                    expmonth.setValue(cm);
                    expday.setValue(cd);
                    expyear.setValue(cy);
                    
                    JOptionPane.showMessageDialog(null, String.valueOf(model.getValueAt(selrow, 2)) + " was Updated Successfully");
                } catch(ClassNotFoundException err){
                    err.printStackTrace();
                } catch(SQLException err) {
                    err.printStackTrace();
                }

                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("INSERT INTO itemslogs(Date, Name, Item, Action, Quantity) VALUES(?, ?, ?, ?, ?)");
                    prep.setString(1, cdate);
                    prep.setString(2, main.name);
                    prep.setString(3, item_name);
                    prep.setString(4, "Update");
                    prep.setInt(5, quantity);
                    prep.executeUpdate();

                    conn.close();
                } catch(ClassNotFoundException err) {
                    err.printStackTrace();
                } catch(SQLException err) {
                    err.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Select what you want to update");
            }
        }

        if (e.getSource() == imprt) {
            int selrow = ItemTable.getSelectedRow();
            int selId = (int) model.getValueAt(selrow, 0);
            int selQuantity = (int) model.getValueAt(selrow, 4);
            int currentQuant = (int) quantity_sp.getValue(); 
            int quantity = selQuantity + currentQuant;
            String item_name = item_tf.getText();
            String cdate = String.valueOf(month.getValue()) + "/" + String.valueOf(day.getValue()) + "/" + String.valueOf(year.getValue());

            if (selrow > -1) {
                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("UPDATE items SET Quantity = ? WHERE id = ?");

                    prep.setInt(1, quantity);
                    prep.setInt(2, selId);
                    prep.executeUpdate();
                    
                    conn.close();
                    model.setRowCount(0);
                    this.loadDB(model);

                    quantity_sp.setValue(0);
                    JOptionPane.showMessageDialog(null, String.valueOf(currentQuant) + " item/s Imported to " + String.valueOf(ItemTable.getValueAt(selrow, 2)));
                } catch(ClassNotFoundException err){
                    err.printStackTrace();
                } catch(SQLException err){
                    err.printStackTrace();
                }

                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("INSERT INTO itemslogs(Date, Name, Item, Action, Quantity) VALUES(?, ?, ?, ?, ?)");
                    prep.setString(1, cdate);
                    prep.setString(2, main.name);
                    prep.setString(3, item_name);
                    prep.setString(4, "In/Imported");
                    prep.setInt(5, currentQuant);
                    prep.executeUpdate();

                    conn.close();
                } catch(ClassNotFoundException err) {
                    err.printStackTrace();
                } catch(SQLException err) {
                    err.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Select what you want to import");
            }
        }

        if (e.getSource() == exprt) {
            int selrow = ItemTable.getSelectedRow();
            int selId = (int) model.getValueAt(selrow, 0);
            int selQuantity = (int) model.getValueAt(selrow, 4);
            int currentQuant = (int) quantity_sp.getValue(); 
            String item_name = item_tf.getText();
            String cdate = String.valueOf(month.getValue()) + "/" + String.valueOf(day.getValue()) + "/" + String.valueOf(year.getValue());

            if (selrow > -1 && currentQuant <= selQuantity) {  
                    int quantity = selQuantity - currentQuant;
                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("UPDATE items SET Quantity = ? WHERE id = ?");

                    prep.setInt(1, quantity);
                    prep.setInt(2, selId);
                    prep.executeUpdate();
                    
                    conn.close();
                    model.setRowCount(0);
                    this.loadDB(model);

                    quantity_sp.setValue(0);
                    JOptionPane.showMessageDialog(null, String.valueOf(currentQuant) + " item/s Exported from " + String.valueOf(ItemTable.getValueAt(selrow, 2)));
                } catch(ClassNotFoundException err){
                    err.printStackTrace();
                } catch(SQLException err){
                    err.printStackTrace();
                }

                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    PreparedStatement prep = conn.prepareStatement("INSERT INTO itemslogs(Date, Name, Item, Action, Quantity) VALUES(?, ?, ?, ?, ?)");
                    prep.setString(1, cdate);
                    prep.setString(2, main.name);
                    prep.setString(3, item_name);
                    prep.setString(4, "Out/Exported");
                    prep.setInt(5, currentQuant);
                    prep.executeUpdate();

                    conn.close();
                } catch(ClassNotFoundException err) {
                    err.printStackTrace();
                } catch(SQLException err) {
                    err.printStackTrace();
                }
            } else if (currentQuant > selQuantity) {
                JOptionPane.showMessageDialog(null, "Insuficient amount to Export");
            } else if (selrow > -1){
                JOptionPane.showMessageDialog(null, "Please Select what you want to Export");
            }
        }

        if (e.getSource() == search) {
            String key = search_tf.getText();

            if (!key.equals("")) {
                try {
                    Class.forName(jdbc);
                    Connection conn = DriverManager.getConnection(url);
                    Statement stat = conn.createStatement();
                    PreparedStatement prep = conn.prepareStatement("SELECT * FROM items WHERE id = ? OR Date LIKE ? OR Item LIKE ? OR Category LIKE ? OR Quantity = ? OR Note LIKE ? OR Expiration LIKE ?;");
                    prep.setString(1, key);
                    prep.setString(2, "%" + key + "%");
                    prep.setString(3, "%" + key + "%");
                    prep.setString(4, "%" + key + "%");
                    prep.setString(5, key);
                    prep.setString(6, "%" + key + "%");
                    prep.setString(7, "%" + key + "%");
                    ResultSet rs = prep.executeQuery(); 
                    model.setRowCount(0);

                    while (rs.next()){
                        model.addRow(new Object[]{rs.getInt("id"), rs.getString("Date"), rs.getString("Item"), rs.getString("Category"), rs.getInt("Quantity"), rs.getString("Note"), rs.getString("Expiration")});
                    }
                    conn.close();
                } catch (ClassNotFoundException err){
                    err.printStackTrace();
                } catch (SQLException err) {
                    err.printStackTrace();
                }
            } else {
                model.setRowCount(0);
                this.loadDB(model);
            }
        }

        if (e.getSource() == clear) {
            item_tf.setText("");
            category_tf.setText("");
            note_tf.setText("");
            quantity_sp.setValue(0);
            month.setValue(cm);
            day.setValue(cd);
            year.setValue(cy);
            expmonth.setValue(cm);
            expday.setValue(cd);
            expyear.setValue(cy);
        }

        if(e.getSource() == log) {
            new ItemLogs();
        }

        if(e.getSource() == logout) {
            new ChooseFrame();
            this.dispose();
        }
    }
}
