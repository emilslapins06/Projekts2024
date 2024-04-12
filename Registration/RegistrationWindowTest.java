import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationWindowTest {

    @Test
    public void testRegistrationWithValidData() {
        RegistrationWindow window = new RegistrationWindow();
        window.usernameField.setText("testUser");
        window.passwordField.setText("testPassword");

        window.registerButton.doClick();

        assertTrue(window.isRegistrationSuccessful());
    }

    @Test
    public void testRegistrationWithEmptyFields() {
        RegistrationWindow window = new RegistrationWindow();
        window.usernameField.setText("");
        window.passwordField.setText("");

        window.registerButton.doClick();

        assertFalse(window.isRegistrationSuccessful());
    }
}