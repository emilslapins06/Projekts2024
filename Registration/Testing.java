import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Testing
{
    @Test
    public void testRegistration() {
        RegistrationWindow window = new RegistrationWindow();
        window.usernameField.setText("testUser");
        window.passwordField.setText("testPassword");
        window.registerButton.doClick();
        assertTrue(window.isRegistrationSuccessful());
    }
}