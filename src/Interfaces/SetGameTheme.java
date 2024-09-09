package Interfaces;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SetGameTheme {
    public static void gameTheme() {
    Color corTexto = Color.white;
    Color corFundoModais = new Color(30, 36, 40);
    Color selectedColor = new UNOConstants().getBLUE("padrao");
    Font defaultFont = new Font("Arial", Font.BOLD, 14);

    UIManager.put("Panel.background", corFundoModais);
    UIManager.put("Panel.messageForeground", corTexto);

    UIManager.put("OptionPane.background", corFundoModais);
    UIManager.put("OptionPane.messageForeground", corTexto);

    UIManager.put("Button.font", defaultFont);
    UIManager.put("Button.foreground", corTexto);
    UIManager.put("Button.background", selectedColor);
    UIManager.put("Button.border", new EmptyBorder(new JButton().getBorder().getBorderInsets(new JButton())));

    UIManager.put("TextField.background", Color.WHITE);
    UIManager.put("TextField.font", new Font("Helvetica", Font.PLAIN, 16));
    UIManager.put("TextField.border", BorderFactory.createLineBorder(Color.WHITE, 4));

    UIManager.put("ComboBox.font", defaultFont);
    UIManager.put("ComboBox.background", Color.WHITE);
    UIManager.put("ComboBox.foreground", Color.BLACK);
    UIManager.put("ComboBox.selectionForeground", corTexto);
    UIManager.put("ComboBox.selectionBackground", selectedColor);
    UIManager.put("ComboBox.border", BorderFactory.createLineBorder(selectedColor, 2));
}

}
