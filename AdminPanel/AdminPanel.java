package AdminPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        button1 = new JButton("Clear User Registry");
        button2 = new JButton("Sort User Registry");
        button3 = new JButton("View User Registry");

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

    public void clearCSVFile() {
        try 
        {
            FileWriter csvWriter = new FileWriter("users.csv", false);
            csvWriter.close();
            JOptionPane.showMessageDialog(this, "User Registry cleared.");
        } 
        catch (IOException e) 
        {
            System.err.println("Error: Failed to clear User Registry.");
            e.printStackTrace();
        }
    }

    public void sortCSVFile() {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            
            // sakārto alfabētiski
            Collections.sort(lines, Comparator.comparing(s -> s.split(",")[0])); // Assuming the first column is the user's name

            // no jauna pārraksta userus csv failā
            FileWriter writer = new FileWriter("users.csv");
            for (String sortedLine : lines) {
                writer.write(sortedLine + "\n");
            }
            writer.close();

            JOptionPane.showMessageDialog(this, "User Registry sorted", "Sort", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Error: Failed to sort User Registry.");
            e.printStackTrace();
        }
    }

    public void viewCSVFile()
    {
        try {
            // nolasa user registry
            BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            // izveido messagebox kas parāda userus
            JOptionPane.showMessageDialog(this, sb.toString(), "User Registry", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Error: Failed to read User Registry file.");
            e.printStackTrace();
        }
    }

        @Override
        public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == button1) 
        {
            clearCSVFile();
        } 
        else if (e.getSource() == button2) 
        {
            sortCSVFile();
        } 
        else if (e.getSource() == button3) 
        {
            viewCSVFile();
        }   
    }
}