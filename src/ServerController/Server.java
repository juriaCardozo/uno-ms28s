package ServerController;

/*
Code created by Josh Braza
*/
import CardModel.WildCard;
import GameModel.Game;
import GameModel.Player;
import Interfaces.GameConstants;
import Interfaces.setGameTheme;
import View.*;
import java.awt.*;
import java.util.ArrayList;
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

	public Server() {

		mode = requestMode();
		startGame();
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

	// return if it's 2-Player's mode or PC-mode
	private int requestMode() {

		setGameTheme.gameTheme();

		Object[] options = { "vs PC", "Jogador vs Jogador", "Cancelar" };

		int n = JOptionPane.showOptionDialog(null,
				"Escolha um modo de jogo para jogar!", "Modo de Jogo",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
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
			infoPanel.setError("Não é a sua vez");
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
		clickedCard.removeMouseListener(CARDLISTENER);
		playedCards.add(clickedCard);
		game.removePlayedCard(clickedCard);

		System.out.println("mudou de turno");
		clickedCard.setShowValue(true);
		session.updatePanel(clickedCard);
		if (mode == vsPC && canPlay) {
			game.switchTurn();
		} else if ( clickedCard.getType() == ACTION) { //!
			game.switchTurn();
		} else if (!checkResults()) {
			confirmNextPlayerTurn(clickedCard);
		}

	}

	// Check if the game is over
	private boolean checkResults() {
		if (game.isOver()) {
			canPlay = false;
			infoPanel.updateText("Fim do Jogo!");
			gameOverNewSession();
			return true;
		}
		return false;
	}

	private void confirmNextPlayerTurn(UNOCard clickedCard) {
		setCardsVisibility(false);

		// Mostrar panel de confirmação
		String message = "Confirme que o próximo jogador está pronto para jogar.";
		int option = JOptionPane.showConfirmDialog(null, message, "Confirmação de Turno", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			// Exibir as cartas após a confirmação do próximo turno
			game.switchTurn();
			session.refreshPanel(); // Atualizar o estado do jogo após a mudança de turno
		} else {
			JOptionPane.showMessageDialog(null, "O turno não foi confirmado. Por favor, confirme para continuar.");
			confirmNextPlayerTurn(clickedCard); // Repetir até que seja confirmado
		}

	}

	private void setCardsVisibility(boolean visible) {
		for (Player player : game.getPlayers()) {
			for (UNOCard card : player.getCards()) {
				card.setShowValue(visible); // Método que define a visibilidade da carta
			}
		}
		session.refreshPanel(); // Atualiza o painel após alterar a visibilidade
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