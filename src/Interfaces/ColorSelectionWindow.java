package Interfaces;

import javax.swing.*;

public class ColorSelectionWindow {
    public String selectedPalette;

    public ColorSelectionWindow() {
        Object[] options = {"Padrão", "Acromatopsia", "Tritanomalia", "Deuteromalia", "Protanomalia"};
        int choice = JOptionPane.showOptionDialog(null, "Escolha uma paleta de cores:", "Seleção de Cores", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        selectedPalette = switch (choice) {
            case 1 -> "Acromatopsia";
            case 2 -> "Tritanomalia";
            case 3 -> "Deuteromalia";
            case 4 -> "Protanomalia";
            default -> "Padrao";
        };
    }

    public String getSelectedPalette() {
        return selectedPalette;
    }
}