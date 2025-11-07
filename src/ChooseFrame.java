package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseFrame extends JFrame implements ActionListener{
    Main main = new Main();
    JComboBox chooseBox;
    JButton enter;
    JTextField username;
    
    ChooseFrame(){
        JPanel bgp = new JPanel(new BorderLayout());
        bgp.setSize(300, 200);
        bgp.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        JPanel top = new JPanel();
        top.setBackground(Color.white);
        JPanel center = new JPanel();
        center.setBackground(Color.white);
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.white);
        bgp.add(top, BorderLayout.NORTH);
        bgp.add(center, BorderLayout.CENTER);
        bgp.add(bottom, BorderLayout.SOUTH);
        String[] type = {"Select Inventory", "Goods Inventory", "Medicine Inventory"};

        chooseBox = new JComboBox(type);
        top.add(chooseBox);

        JLabel username_l = new JLabel("Username:");
        username = new JTextField();
        username.setPreferredSize(new Dimension(200, 30));
        center.add(username_l);
        center.add(username);

        enter = new JButton("ENTER");
        enter.addActionListener(this);
        enter.setFocusable(false);
        bottom.add(enter); 

        //Theme
        username.setFont(new Font("Arial", Font.BOLD, 14));
        username_l.setFont(new Font("Arial", Font.BOLD, 14));
        enter.setFont(new Font("Arial", Font.BOLD, 14));
        
        this.add(bgp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 70));
        this.setTitle("Denz Inventories");
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == enter) {
            String type = (String) chooseBox.getSelectedItem();
            if(type.equals("Goods Inventory")) {
                main.name = username.getText();
                new Goods();
                this.dispose();
            }

            if(type.equals("Medicine Inventory")) {
                main.name = username.getText();
                new Medicine();
                this.dispose();
            }
        }

    }
}
