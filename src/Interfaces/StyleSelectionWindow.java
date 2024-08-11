package Interfaces;

import javax.swing.*;

public class StyleSelectionWindow {

    public boolean selectedStyle;

    public StyleSelectionWindow() {
        Object[] options = {"Tradicional", "Minimalista"};
        int choice = JOptionPane.showOptionDialog(null, "Escolha um estilo de baralho:", "Seleção de Estilos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        selectedStyle = choice == 1;
    }

    public boolean getSelectedStyle() {
        return selectedStyle;
    }
}
