package ServerController;

/*
Code created by Josh Braza
*/
import CardModel.WildCard;
import GameModel.Game;
import GameModel.GameModeSelector;
import GameModel.Player;
import Interfaces.GameConstants;
import View.*;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

public class Server implements GameConstants {
	private Game game;
	private Session session;
	private Stack<UNOCard> playedCards;
	public boolean canPlay;
	private final int mode;
	private Observer observer;
	private final GameModeSelector gameModeSelector;  
	private final CardActionHandler cardActionHandler;

	public Server() {
		gameModeSelector = new GameModeSelector(); 
		mode = gameModeSelector.requestMode();
		startGame();
		cardActionHandler = new CardActionHandler(game);
	}

	public void startGame() {
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
			infoPanel.setError("Não é a sua vez");
			infoPanel.repaint();
		} else {

			// Card validation
			if (cardActionHandler.isValidMove(clickedCard, peekTopCard())) {
				boolean cardConfirmed = true;
				// function cards ??
				switch (clickedCard.getType()) {
					case ACTION -> cardActionHandler.performAction(clickedCard);
					case WILD -> cardConfirmed = cardActionHandler.performWild((WildCard) clickedCard, mode);
					default -> {
					}
				}
				if (cardConfirmed) {
					playClickedCard(clickedCard);
				}
			} else {
				infoPanel.setError("Jogada inválida");
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
		System.out.println("Card: " + clickedCard.toString());
		clickedCard.removeMouseListener(CARDLISTENER);
		playedCards.add(clickedCard);
		game.removePlayedCard(clickedCard);
		if (clickedCard.getType() == WILD || clickedCard.getType() == ACTION) {
			// checkResults();
		} else {
			game.switchTurn();
		}
		clickedCard.setShowValue(true);
		session.updatePanel(clickedCard);
		checkResults();
	}

	// Check if the game is over
	private void checkResults() {

		if (game.isOver()) {
			canPlay = false;
			infoPanel.updateText("Fim do Jogo!");
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

		Object[] options = { "Nova Partida", "Cancelar" };

		int n = JOptionPane.showOptionDialog(null,
				"Como deseja prosseguir?", "A partida acabou!",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);

		if (n == 0) {
			observer.runFunc();
		} else {
			System.exit(1);
		}
	}
}