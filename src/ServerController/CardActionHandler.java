package ServerController;

import GameModel.Game;
import Interfaces.GameConstants;
import View.UNOCard;

import java.util.Random;
import javax.swing.*;

import CardModel.WildCard;

import java.awt.*;
import java.util.ArrayList;

public class CardActionHandler implements GameConstants {
  private Game game;

  public CardActionHandler(Game game) {
    this.game = game;
  }

  // Validate if the played card is a valid move
  public boolean isValidMove(UNOCard playedCard, UNOCard topCard) {
    if (playedCard.getColor().equals(topCard.getColor())
        || playedCard.getValue().equals(topCard.getValue())) {
      return true;
    }

    if (playedCard.getType() == WILD) {
      return true;
    } else if (topCard.getType() == WILD) {
      Color wildColor = ((WildCard) topCard).getWildColor();
      return wildColor.equals(playedCard.getColor());
    }

    return false;
  }

  // Perform the action for an Action card
  public void performAction(UNOCard actionCard) {
    if (actionCard.getValue().equals(DRAW2PLUS)) {
      game.drawPlus(2);
    }
  }

  // Perform the action for a Wild card
  public boolean performWild(WildCard functionCard, int mode) {
    if (mode == 1 && game.isPCsTurn()) {
      int random = new Random().nextInt() % 4;
      functionCard.useWildColor(UNO_COLORS[Math.abs(random)]);
    } else {
      ArrayList<String> colors = new ArrayList<>();
      colors.add("VERMELHO");
      colors.add("AZUL");
      colors.add("VERDE");
      colors.add("AMARELO");

      String chosenColor = (String) JOptionPane.showInputDialog(null,
          "Escolha uma cor", "Carta Escolhe Cor",
          JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);

      if (chosenColor == null) {
        return false;
      }

      functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
    }

    if (functionCard.getValue().equals(W_DRAW4PLUS)) {
      game.drawPlus(4);
      game.switchTurn();
    }

    return true;
  }
}
