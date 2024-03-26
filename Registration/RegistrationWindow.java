import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationWindow extends JFrame implements ActionListener 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private JPanel homeScreen;

    //izveidojam pirmo ekrānu ar reģistrāciju
    public RegistrationWindow() 
    {
        setTitle("Reģistrācijas logs");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        homeScreen = new JPanel(new CardLayout());

        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);

        registrationPanel.add(usernameLabel);
        registrationPanel.add(usernameField);
        registrationPanel.add(passwordLabel);
        registrationPanel.add(passwordField);
        registrationPanel.add(registerButton);
        registrationPanel.add(loginButton);

        homeScreen.add(registrationPanel, "registration");

        CardLayout cardLayout = (CardLayout) homeScreen.getLayout();
        cardLayout.show(homeScreen, "registration");

        add(homeScreen);
        setVisible(true);
    }

    //izveidojam jaunu ekrānu pēc reģistrācijas
    private JPanel createLoggedInPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        panel.add(button1);
        panel.add(button2);

        return panel;
    }

    @Override
    //zemāk izpildās darbības, ja tiek uzspiesta register poga
    public void actionPerformed(ActionEvent e) {
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
                    showLoggedInPanel();
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
        //zemāk izpildās darbības, ja tiek uzspiesta log in poga
        else if (e.getSource() == loginButton) 
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("users.csv"));
                String row;
                boolean loggedIn = false;

                while ((row = csvReader.readLine()) != null) 
                {
                    String[] data = row.split(",");
                    if (data.length == 2 && data[0].equals(username) && data[1].equals(password)) 
                    {
                        loggedIn = true;
                        break;
                    }
                }

                csvReader.close();

                if (loggedIn) 
                {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    showLoggedInPanel();
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Incorrect username or password");
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }

    //parāda jaunu ekrānu pēc reģistrācijas
    private void showLoggedInPanel() 
    {
        JPanel loggedInPanel = createLoggedInPanel();
        homeScreen.add(loggedInPanel, "loggedIn");
        CardLayout cardLayout = (CardLayout) homeScreen.getLayout();
        cardLayout.show(homeScreen, "loggedIn");
    }

}