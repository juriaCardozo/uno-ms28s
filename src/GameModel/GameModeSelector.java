package GameModel;

import Interfaces.GameConstants;
import Interfaces.SetGameTheme;

import javax.swing.*;

public class GameModeSelector implements GameConstants {

	// return if it's 2-Player's mode or PC-mode
  public int requestMode() {
    SetGameTheme.gameTheme();

    Object[] options = { "vs PC", "Jogador vs Jogador", "Cancelar" };

    int n = JOptionPane.showOptionDialog(null,
        "Escolha um modo de jogo para jogar!", "Modo de Jogo",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);

    if (n == 2 || n < 0)
      System.exit(1);

    return GAMEMODES[n];
  }
}
