import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationWindow extends JFrame implements ActionListener 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegistrationWindow() 
    {
        setTitle("Registration Window"); // Setting title here
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);

        add(panel); // Adding panel to the frame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == registerButton) 
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) 
            {
                try {
                    FileWriter csvWriter = new FileWriter("users.csv", true);
                    csvWriter.append(username + "," + password + "\n");
                    csvWriter.close();
                    JOptionPane.showMessageDialog(this, "Registration Successful!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: Failed to register.");
                    ex.printStackTrace();
                }
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Username or Password cannot be empty.");
            }
        }
    }
}