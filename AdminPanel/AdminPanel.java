package AdminPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPanel extends JPanel implements ActionListener {
    public JButton button1;
    public JButton button2;
    public JButton button3;

    public AdminPanel() 
    {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(button1, gbc);

        gbc.gridx = 1;
        add(button2, gbc);

        gbc.gridx = 2;
        add(button3, gbc);

    }

        @Override
        public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == button1) 
        {
            System.out.println("Button 1 clicked");
        } 
        else if (e.getSource() == button2) 
        {
            System.out.println("Button 2 clicked");
        } 
        else if (e.getSource() == button3) 
        {
            System.out.println("Button 3 clicked");
        }   
    }
}