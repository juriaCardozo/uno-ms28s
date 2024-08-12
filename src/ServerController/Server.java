package ServerController;

import CardModel.WildCard;
import GameModel.Game;
import GameModel.Player;
import Interfaces.GameConstants;
import View.Session;
import View.UNOCard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Server implements GameConstants {
	private Game game;
	private Session session;
	private Stack<UNOCard> playedCards;
	public boolean canPlay;
	private final int mode;
	private Observer observer;

	public Server() {

		mode = requestMode();
		startGame();
	}

	public final void startGame() {
		game = new Game(mode);
		playedCards = new Stack<>();

		// First Card
		UNOCard firstCard = game.getCard();
		while (firstCard.getValue().equals(W_COLORPICKER) ||
				firstCard.getValue().equals(W_DRAW4PLUS)) {
			firstCard = game.getCard();
		}

		modifyFirstCard(firstCard);

		playedCards.add(firstCard);
		session = new Session(game, firstCard);

		game.whoseTurn();
		canPlay = true;
	}

	// return if it's 2-Player's mode or PC-mode
	private int requestMode() {

		Object[] options = { "vs PC", "Manual", "Cancel" };

		int n = JOptionPane.showOptionDialog(null,
				"Choose a Game Mode to play", "Game Mode",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

		if (n == 2 || n < 0)
			System.exit(1);

		return GAMEMODES[n];
	}

	// coustom settings for the first card
	private void modifyFirstCard(UNOCard firstCard) {
		firstCard.removeMouseListener(CARDLISTENER);
		if (firstCard.getType() == WILD) {
			int random = new Random().nextInt() % 4;
			try {
				((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
			} catch (Exception ex) {
				System.out.println("something wrong with modifyFirstcard");
			}
		}
	}

	// return Main Panel
	public Session getSession() {
		return this.session;
	}

	// request to play a card
	public void playThisCard(UNOCard clickedCard) {

		// Check player's turn
		if (!isHisTurn(clickedCard)) {
			infoPanel.setError("It's not your turn");
			infoPanel.repaint();
		} else {

			// Card validation
			if (isValidMove(clickedCard)) {
				boolean cardConfirmed = true;
				// function cards ??
				switch (clickedCard.getType()) {
					case ACTION -> performAction(clickedCard);
					case WILD -> cardConfirmed = performWild((WildCard) clickedCard);
					default -> {
                                }
				}
				if (cardConfirmed) {
					playClickedCard(clickedCard);
				}
			} else {
				infoPanel.setError("invalid move");
				infoPanel.repaint();
			}

		}

		if (mode == vsPC && canPlay) {
			if (game.isPCsTurn()) {
				game.playPC(peekTopCard());
			}
		}
	}

	private void playClickedCard(UNOCard clickedCard) {
		clickedCard.removeMouseListener(CARDLISTENER);
		playedCards.add(clickedCard);
		game.removePlayedCard(clickedCard);

		game.switchTurn();
		clickedCard.setShowValue(true);
		session.updatePanel(clickedCard);
		checkResults();
	}

	// Check if the game is over
	private void checkResults() {

		if (game.isOver()) {
			canPlay = false;
			infoPanel.updateText("GAME OVER");
			gameOverNewSession();
		}
	}

	// check player's turn
	public boolean isHisTurn(UNOCard clickedCard) {

		for (Player p : game.getPlayers()) {
			if (p.hasCard(clickedCard) && p.isMyTurn())
				return true;
		}
		return false;
	}

	// check if it is a valid card
	public boolean isValidMove(UNOCard playedCard) {
		UNOCard topCard = peekTopCard();

		if (playedCard.getColor().equals(topCard.getColor())
				|| playedCard.getValue().equals(topCard.getValue())) {
			return true;
		}

		else if (playedCard.getType() == WILD) {
			return true;
		} else if (topCard.getType() == WILD) {
			Color color = ((WildCard) topCard).getWildColor();
			if (color.equals(playedCard.getColor()))
				return true;
		}
		return false;
	}

	// ActionCards
	private void performAction(UNOCard actionCard) {
		// Draw2PLUS
		if (actionCard.getValue().equals(DRAW2PLUS))
			game.drawPlus(2);

		game.switchTurn();
	}

	private boolean performWild(WildCard functionCard) {

		if (mode == 1 && game.isPCsTurn()) {
			int random = new Random().nextInt() % 4;
			functionCard.useWildColor(UNO_COLORS[Math.abs(random)]);
		} else {

			ArrayList<String> colors = new ArrayList<>();
			colors.add("RED");
			colors.add("BLUE");
			colors.add("GREEN");
			colors.add("YELLOW");

			String chosenColor = (String) JOptionPane.showInputDialog(null,
					"Choose a color", "Wild Card Color",
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

	public void requestCard() {
		game.drawCard(peekTopCard());

		if (mode == vsPC && canPlay) {
			if (game.isPCsTurn())
				game.playPC(peekTopCard());
		}

		session.refreshPanel();
	}

	public UNOCard peekTopCard() {
		return playedCards.peek();
	}

	public void submitSaidUNO() {
		game.setSaidUNO();
	}

	public void setObserver(Observer newObserver) {
		observer = newObserver;
	}

	public Observer observer() {
		return observer;
	}

	public void gameOverNewSession() {

		Object[] options = { "New round", "Cancel" };

		int n = JOptionPane.showOptionDialog(null,
				"Choose how to proceed", "select",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);

		if (n == 0) {
			observer.runFunc();
		} else {
			System.exit(1);
		}
	}
}