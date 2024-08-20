package GameModel;
/*
Code created by Josh Braza 
*/
import java.util.LinkedList;

import View.UNOCard;

public class Player {
	
	private String name = null;
	private boolean isMyTurn = false;
	private boolean saidUNO = false;
	private LinkedList<UNOCard> myCards;
	
	private int playedCards = 0;
	
	public Player(){
		myCards = new LinkedList<UNOCard>();
	}
	
	public Player(String player){
		setName(player);
		myCards = new LinkedList<UNOCard>();
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String getName(){
		return this.name;
	}
	public void obtainCard(UNOCard card){
		System.out.println("Player: " + name + " obtained card: " + card.toString());
		card.setShowValue(isMyTurn);
		myCards.add(card);
	}
	
	public LinkedList<UNOCard> getAllCards(){
		return myCards;
	}
	
	public int getTotalCards(){
		return myCards.size();
	}
	
	public boolean hasCard(UNOCard thisCard){
		return myCards.contains(thisCard);		
	}
	
	public void removeCard(UNOCard thisCard){
		System.out.println("Player: " + name + " played card: " + thisCard.toString());
		myCards.remove(thisCard);
		playedCards++;
	}
	
	public int totalPlayedCards(){
		return playedCards;
	}
	
	public void toggleTurn(){
		isMyTurn = !isMyTurn;
		for(UNOCard uno : myCards) {
			uno.setShowValue(isMyTurn);
		}
	}
	
	public boolean isMyTurn(){
		return isMyTurn;
	}
	
	public boolean hasCards(){
		return !myCards.isEmpty();
	}
	
	public boolean getSaidUNO(){
		return saidUNO;
	}
	
	public void saysUNO(){
		System.out.println("\nPlayer: " + name + " said UNO!");
		saidUNO = true;
	}
	
	public void setSaidUNOFalse(){
		saidUNO = false;
	}
	
	public void setCards(){
		myCards = new LinkedList<UNOCard>();
	}
}
