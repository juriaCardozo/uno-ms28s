package GameModel;
/*
Code created by Josh Braza
*/

import CardModel.*;
import GameModel.Audio.AudioManager;
import Interfaces.GameConstants;
import View.UNOCard;
import java.util.Stack;
import javax.swing.JOptionPane;

//import static Interfaces.UNOConstants.WILD;

public class Game implements GameConstants {

	private final  Player[] players;
	private boolean isOver;
	private final int GAMEMODE;

	private PC pc;
	private final Dealer dealer;
	private final Stack<UNOCard> cardStack;

	private final AudioManager audioManager;

	public Game(int mode){

		GAMEMODE = mode;
		audioManager = new AudioManager();
		//Create players
		String name = (GAMEMODE==MANUAL) ? JOptionPane.showInputDialog(null, "Escolha um nome para o Jogador 1:",
				"Nome do Jogador", JOptionPane.PLAIN_MESSAGE) : "PC";
		String name2 = JOptionPane.showInputDialog(null, "Escolha um nome para o Jogador 2:",
				"Nome do Jogador", JOptionPane.PLAIN_MESSAGE);
		name = name == null || name.isEmpty() ? "Jogador 1" : name;
		name2 = name2 == null || name2.isEmpty() ? "Jogador 2" : name2;

		if(GAMEMODE==vsPC)
			pc = new PC();

		Player player1 = (GAMEMODE==vsPC) ? pc : new Player(name);
		Player player2 = new Player(name2);

		controlBackgroundMusic();

		player2.toggleTurn();				//Initially, player2's turn

		players = new Player[]{player1, player2};

		//Create Dealer
		dealer = new Dealer();
		cardStack = dealer.shuffle();
		dealer.spreadOut(players);

		isOver = false;
	}

	public boolean volumeUp() {
		return audioManager.volumeUp();
	}

	public boolean volumeDown() {
		return audioManager.volumeDown();
	}

	public final boolean controlBackgroundMusic() {
		if(audioManager.isPlaying()){
			audioManager.stopBackgroundMusic();
			return false;
		}
		else{
			audioManager.playBackgroundMusic("src/Sounds/Run-Amok_chosic.com_.wav");
			return true;
		}
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
		return dealer.getCard();
	}

	//Modularização na implementação caso o jogador não diga uno quando estiver com 2 cartas
	public void removePlayedCard(UNOCard playedCard) {
		for (Player player : players) {
			if (player.hasCard(playedCard)) {
				player.removeCard(playedCard);
				playCardSound();
				handleUNOState(player);
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
		boolean canPlay = false;

		for (Player p : players) {
			if (p.isMyTurn()) {
				UNOCard newCard = getCard();
				p.obtainCard(newCard);
	
				if (GAMEMODE == MANUAL) {
					canPlay = canPlay(topCard, newCard);
	
					if (!canPlay) {
						switchTurn();
					}
				} else if (pc.isMyTurn()) {
					canPlay = canPlay(topCard, newCard);
	
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

		if (!canPlay)
			switchTurn();
	}

	public void switchTurn() {
		for (Player p : players) {
			p.toggleTurn();
		}
		whoseTurn();
	}

	//Draw cards x times
	public void drawPlus(int times) {
		for (Player p : players) {
			if (!p.isMyTurn()) {
				for (int i = 1; i <= times; i++)
					p.obtainCard(getCard());
			}
		}
	}

	//response whose turn it is
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

		if(cardStack.isEmpty()){
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
		return cardStack.size();
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
