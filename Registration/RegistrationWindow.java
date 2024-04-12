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
        setCustomIcon("rvtlogo.png");
        
        homeScreen = new JPanel(new CardLayout());

        JPanel registrationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        registrationPanel.add(usernameLabel, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridy = 1;
        registrationPanel.add(passwordLabel, gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        registrationPanel.add(usernameField, gbc);

        passwordField = new JPasswordField();
        gbc.gridy = 1;
        registrationPanel.add(passwordField, gbc);

        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        registrationPanel.add(registerButton, gbc);
        registerButton.addActionListener(this);

        loginButton = new JButton("Log In");
        gbc.gridy = 3;
        registrationPanel.add(loginButton, gbc);
        loginButton.addActionListener(this);

        homeScreen.add(registrationPanel, "registration");

        CardLayout cardLayout = (CardLayout) homeScreen.getLayout();
        cardLayout.show(homeScreen, "registration");

        add(homeScreen);
        setVisible(true);
    }

    private void setCustomIcon(String imagePath) 
    {
        ImageIcon icon = new ImageIcon(imagePath);
        setIconImage(icon.getImage());
    }
    
    //izveidojam jaunu ekrānu pēc reģistrācijas
    private JPanel createLoggedInPanel() 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton button1 = new JButton("Flappy Bird");
        JButton button2 = new JButton("Snake");

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

                //ja logged in, ļauj veidot jaunu ekrānu
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
