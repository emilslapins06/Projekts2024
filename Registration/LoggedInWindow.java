import javax.swing.*;
import java.awt.*;

public class LoggedInWindow extends JPanel {
    public LoggedInWindow() {
        setLayout(new GridLayout(1, 2));

        JButton button1 = new JButton("Flappy Bird");
        JButton button2 = new JButton("Snake");

        add(button1);
        add(button2);
    }
}