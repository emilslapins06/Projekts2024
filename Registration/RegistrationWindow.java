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

    //izveidojam reģistrācijas logu
    public RegistrationWindow() 
    {
        setTitle("Reģistrācijas logs");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 90, 90));

        ImageIcon icon = new ImageIcon("rvtlogo.png");
        setIconImage(icon.getImage());

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);


        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    //reģistrācija
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
        else if (e.getSource() == loginButton)
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try{
                BufferedReader csvReader = new BufferedReader(new FileReader("users.csv"));
                String row;
                boolean loggedIn = false;

                while((row = csvReader.readLine()) != null)
                {
                    String[] data = row.split(",");
                    if (data.length == 2 && data[0].equals(username) && data[1].equals(password))
                    {
                        loggedIn = true;
                        break;
                    }
                }

                csvReader.close();

                if(loggedIn == true)
                {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Incorrect username or password");
                }
                
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }
}