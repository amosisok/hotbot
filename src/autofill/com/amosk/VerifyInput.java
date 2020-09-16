package autofill.com.amosk;

import javax.swing.*;
import java.awt.*;

public class VerifyInput extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            long num = Long.parseLong(text);
        } catch (NumberFormatException e) {
            input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            return false;
        }

        input.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        return true;
    }
}
