package GameModel;
/*
Code created by Josh Braza
*/

import CardModel.*;
import GameModel.Managers.AudioManager;
import GameModel.Managers.CardManager;
import Interfaces.GameConstants;
import View.PlayerIcon;
import View.UNOCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class Game implements GameConstants {

	private final  Player[] players;
	private boolean isOver;
	private final int GAMEMODE;

	private PC pc;
	
	private final CardManager cardManager;
	private final AudioManager audioManager;

	public Game(int mode){

		GAMEMODE = mode;
		audioManager = AudioManager.getInstance();

		//Create players
		String nomeJogadorUm;
        String nomeJogadorDois;

		if (GAMEMODE == vsPC) {
			nomeJogadorUm = solicitarNomeJogador("Escolha um nome para o Jogador 1:");
			nomeJogadorDois = "PC";
			pc = new PC(); // Criar o PC
			System.out.println("Game mode é vs PC.");
		} else {
			nomeJogadorUm = solicitarNomeJogador("Escolha um nome para o Jogador 1:");
			nomeJogadorDois = solicitarNomeJogador("Escolha um nome para o Jogador 2:");
			System.out.println("Game mode é Player vs Player.");
		}

		PlayerIcon playerIconUm;
		if (GAMEMODE == vsPC) {
			playerIconUm = playerIconOptionList[new Random().nextInt(playerIconOptionList.length)];
		} else {
			playerIconUm = showIconSelectionDialog(nomeJogadorUm);
		}

		PlayerIcon playerIconDois = showIconSelectionDialog(nomeJogadorDois);

		if (GAMEMODE == vsPC) {
			pc = new PC();
			System.out.println("Game mode é vs PC.");
		} else {
			System.out.println("Game mode é Player vs Player.");
		}

		Player player1 = (GAMEMODE==vsPC) ? pc : new Player(nomeJogadorUm);
		Player player2 = new Player(nomeJogadorDois);

		player1.setPlayerIcon(playerIconUm);
		player2.setPlayerIcon(playerIconDois);

		audioManager.controlBackgroundMusic();

		player2.toggleTurn();				//Initially, player2's turn

		players = new Player[]{player1, player2};

		cardManager = new CardManager(players);

		isOver = false;
	}

	private String solicitarNomeJogador(String mensagem) {
		String nomeJogador;
		do {
			nomeJogador = JOptionPane.showInputDialog(null, mensagem, "Nome do Jogador", JOptionPane.PLAIN_MESSAGE);
			if (nomeJogador == null || nomeJogador.isEmpty()) {
				JOptionPane.showMessageDialog(null, "O nome do jogador não pode ser vazio. Por favor, insira um nome válido.");
			}
		} while (nomeJogador == null || nomeJogador.isEmpty());
		return nomeJogador;
	}

	private PlayerIcon showIconSelectionDialog(String playerName) {
		JPanel iconPanel = new JPanel(new GridLayout(1, playerIconOptionList.length, 10, 10));
		final PlayerIcon[] selected = {playerIconOptionList[new Random().nextInt(playerIconOptionList.length)]};

		for (PlayerIcon icon : playerIconOptionList) {
			JButton button = new JButton(icon);
			button.setPreferredSize(new Dimension(50, 30));
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selected[0] = icon;
					JOptionPane.getRootFrame().dispose();
				}
			});
			iconPanel.add(button);
		}

		JOptionPane.showOptionDialog(
				null,
				iconPanel,
				"Escolha um ícone para " + playerName,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				new Object[]{},
				null
		);

		return selected[0]; // Retorna o ícone selecionado ou valor aleatório se o dialog for fechado
	}

	private void playAudio(String audioFilePath) { //pescar carta
		audioManager.playSoundEffect(audioFilePath);
	}

	private void playCardSound() { //jogar carta
		playAudio("src/Sounds/depositphotos_414403158-track-short-recording-footstep-dry-grass.wav");
	}

	public Player[] getPlayers() {
		return players;
	}

	public UNOCard getCard() {
		return cardManager.drawCard();
	}

	//Modularização na implementação caso o jogador não diga uno quando estiver com 2 cartas
	public void removePlayedCard(UNOCard playedCard) {
		cardManager.removePlayedCard(playedCard);
		playCardSound();
		for(Player player : players){
			if(player.isMyTurn()){
				handleUNOState(player);
				break;
			}
		}
	}
	
	private void handleUNOState(Player player) {
		if (player.getTotalCards() == 1 && !player.getSaidUNO()) {
			applyUNOConsequences(player);
		} else if (player.getTotalCards() > 2) {
			player.setSaidUNOFalse();
		}
	}
	
	private void applyUNOConsequences(Player player) {
		infoPanel.setError(player.getName() + " esqueceu de dizer UNO!");
		player.obtainCard(getCard());
		player.obtainCard(getCard());
	}
	

	private void sayUNO(Player player) {
		System.out.println(player.getName() + "Jogador esqueceu de dizer uno");
		infoPanel.setError(player.getName() + " Esqueceu de dizer UNO!");
		player.obtainCard(getCard());
		player.obtainCard(getCard());
	}

	//give player a card
	public void drawCard(UNOCard topCard) {
		System.out.println("Comprando carta para o jogador atual.");
		boolean canPlay = false;

		for (Player p : players) {
			if (p.isMyTurn()) {
				UNOCard newCard = getCard();
				p.obtainCard(newCard);
				System.out.println("Carta comprada: " + newCard.toString());

				canPlay = canPlay(topCard, newCard);
				infoPanel.repaint();

				if (pc != null && pc.isMyTurn()) {
					if (canPlay) {
						playPC(topCard);
					} else {
						switchTurn();
					}
				}
				break;
			}
		}

		playAudio("src/Sounds/depositphotos_431797418-track-heavily-pushing-releasing-spacebar-keyboard.wav");

		if (!canPlay && GAMEMODE != vsPC) {
			switchTurn();
		}
	}

	public void switchTurn() {
		System.out.println("Trocando o turno");
		for (Player p : players) {
			p.toggleTurn();
		}

		if (GAMEMODE != vsPC) {
			setCardsVisibility(false);

			confirmNextPlayerTurn();

			setCardsVisibilityForCurrentPlayer(true);
		}

		whoseTurn();
	}

	private void confirmNextPlayerTurn() {
		infoPanel.repaint();

		String message = "Confirme que o próximo jogador está pronto para jogar.";
		int option = JOptionPane.showConfirmDialog(null, message, "Confirmação de Turno", JOptionPane.OK_CANCEL_OPTION);

		if (option != JOptionPane.OK_OPTION) {
			JOptionPane.showMessageDialog(null, "O turno não foi confirmado. Por favor, confirme para continuar.");
			confirmNextPlayerTurn();
		}
	}

	private void setCardsVisibility(boolean visible) {
		for (Player player : this.getPlayers()) {
			for (UNOCard card : player.getCards()) {
				card.setShowValue(visible);
			}
		}
	}

	private void setCardsVisibilityForCurrentPlayer(boolean visible) {
		for (Player player : this.getPlayers()) {
			if (player.isMyTurn()) {
				for (UNOCard card : player.getCards()) {
					card.setShowValue(visible);
				}
			}
		}
	}

	public void drawPlus(int times) {
		System.out.println("Comprando " + times + " cartas como penalidade.");
		for (Player p : players) {
			if (!p.isMyTurn()) {
				for (int i = 1; i <= times; i++)
					p.obtainCard(getCard());
			}
		}
	}

	//response whose turn it is
	public void drawwPlus(int times){
		cardManager.drawPlus(times);
	}

	public void whoseTurn() {

		for (Player p : players) {
			if (p.isMyTurn()){
				infoPanel.updateText("Vez de " + p.getName());
				System.out.println("Vez de " + p.getName());
			}
		}
		infoPanel.setDetail(playedCardsSize(), remainingCards());
		infoPanel.repaint();
	}

	//return if the game is over
	public boolean isOver() {

		if(cardManager.remainingCards() == 0){
			isOver= true;
			audioManager.stopBackgroundMusic();//parar de tocar a musica de fundo
			return isOver;
		}

		for (Player p : players) {
			if (!p.hasCards()) {
				isOver = true;
				audioManager.stopBackgroundMusic();//parar de tocar a musica de fundo
				break;
			}
		}

		return isOver;
	}

	public int remainingCards() {
		return cardManager.remainingCards();
	}

	public int[] playedCardsSize() {
		int[] nr = new int[2];
		int i = 0;
		for (Player p : players) {
			nr[i] = p.totalPlayedCards();
			i++;
		}
		return nr;
	}

	//Check if this card can be played
	private boolean canPlay(UNOCard topCard, UNOCard newCard) {

		// Color or value matches
		if (topCard.getColor().equals(newCard.getColor())
				|| topCard.getValue().equals(newCard.getValue()))
			return true;
			// if chosen wild card color matches
		else if (topCard.getType() == WILD)
			return ((WildCard) topCard).getWildColor().equals(newCard.getColor());

			// suppose the new card is a wild card
		else if (newCard.getType() == WILD)
			return true;

		// else
		return false;
	}

	//Check whether the player said or forgot to say UNO
	public void checkUNO() {
		for (Player p : players) {
			if (p.isMyTurn()) {
				if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
					sayUNO(p);
				}
			}
		}
	}

	public void setSaidUNO() {
		for (Player p : players) {
			if (p.isMyTurn()) {
				if (p.getTotalCards() == 2) {
					p.saysUNO();
					infoPanel.setError(p.getName() + " disse UNO!");
				}
			}
		}
	}

	public boolean isPCsTurn(){
        return pc.isMyTurn();
    }

	//if it's PC's turn, play it for pc
	public void playPC(UNOCard topCard) {

		if (pc.isMyTurn()) {
			boolean done = pc.play(topCard);

			if(!done)
				drawCard(topCard);
		}
	}
}
