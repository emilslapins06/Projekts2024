//package Registration;
import Putns.FlappyBird;
import AdminPanel.AdminPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationWindow extends JFrame implements ActionListener 
{
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton registerButton;
    public JButton loginButton;
    public JPanel homeScreen;

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
    gbc.anchor = GridBagConstraints.WEST;
    registrationPanel.add(usernameLabel, gbc);

    JLabel passwordLabel = new JLabel("Password:");
    gbc.gridy = 1;
    registrationPanel.add(passwordLabel, gbc);

    usernameField = new JTextField(20);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.WEST;
    registrationPanel.add(usernameField, gbc);

    passwordField = new JPasswordField(20);
    gbc.gridy = 1;
    registrationPanel.add(passwordField, gbc);

    registerButton = new JButton("Register");
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.CENTER;
    registrationPanel.add(registerButton, gbc);
    registerButton.addActionListener(this);

    loginButton = new JButton("Log In");
    gbc.gridx = 0;
    gbc.gridy = 3;
    registrationPanel.add(loginButton, gbc);
    loginButton.addActionListener(this);

    registerButton.setBackground(Color.WHITE);
    loginButton.setBackground(Color.WHITE);

    UIManager.put("Button.background", Color.WHITE);
    UIManager.put("Button.foreground", Color.BLACK);
    UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));

    homeScreen.add(registrationPanel, "registration");

    CardLayout cardLayout = (CardLayout) homeScreen.getLayout();
    cardLayout.show(homeScreen, "registration");

    add(homeScreen);
    setVisible(true);
    }

    private void setCustomIcon(String imagePath) 
    {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        setIconImage(icon.getImage());
    }
    
    //izveidojam jaunu ekrānu pēc reģistrācijas
    private JPanel createLoggedInPanel(String username) 
    {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome to our application, " + username + "!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setLayout(new GridLayout(1, 3));

        JButton button1 = new JButton("Flappy Bird");
        JButton button2 = new JButton("Snake");
        JButton button3 = new JButton("Admin Tools");

        if (username.equals("admin")) {
            button3.addActionListener(e -> launchAdminPanel());
        } else {
            button3.setEnabled(false);  
        }

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launchFlappyBird();
            }
        });

        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launchSnake();
            }
        });

        button3.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launchAdminPanel();
            }
        });

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        panel.add(label, BorderLayout.NORTH);

        return panel;
    }

    private void launchFlappyBird()
    {
        int boardHeight = 640;
        int boardWidth = 360;
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.setVisible(true);
    }

    public void launchSnake()
    {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
	    frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    private void launchAdminPanel()
    {
        int boardHeight = 140;
        int boardWidth = 650;
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        AdminPanel adminPanel = new AdminPanel();
        frame.add(adminPanel);
        frame.setVisible(true);
    }

    private boolean containsSpecialChar(String str) 
    {
        String specialCharacters = "!@#$%^&*()-_+={}[]|:;\"'<>?,./~`";
        for (char ch : str.toCharArray()) 
        {
            if (specialCharacters.contains(String.valueOf(ch))) 
            {
                return true;
            }
        }
        return false;
    }

    @Override
    //zemāk izpildās darbības, ja tiek uzspiesta register poga
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) 
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!username.isEmpty() && !password.isEmpty() && !containsSpecialChar(username)) 
            {
                try {
                    FileWriter csvWriter = new FileWriter("users.csv", true);
                    csvWriter.append(username + "," + password + "\n");
                    csvWriter.close();
                    JOptionPane.showMessageDialog(this, "Registration Successful!");
                    showLoggedInPanel(username);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: Failed to register.");
                    ex.printStackTrace();
                }
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Username or Password cannot be empty and Username can't have special characters.");
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

                //ja logged/registered in, ļauj veidot jaunu ekrānu
                if (loggedIn) 
                {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    showLoggedInPanel(username);
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
    private void showLoggedInPanel(String username) 
    {
        JPanel loggedInPanel = createLoggedInPanel(username);
        setTitle("Galvenais Logs");
        homeScreen.add(loggedInPanel, "loggedIn");
        CardLayout cardLayout = (CardLayout) homeScreen.getLayout();
        cardLayout.show(homeScreen, "loggedIn");
    }

}