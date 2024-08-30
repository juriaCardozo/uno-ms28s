package GameModel;
/*
Code created by Josh Braza
*/
import View.UNOCard;
import java.util.LinkedList;

public class Player {
	
	private String name = null;
	private boolean isMyTurn = false;
	private boolean saidUNO = false;
	private final LinkedList<UNOCard> myCards;
	private boolean isPC = false;

	
	private int playedCards = 0;
	
	public Player(){
		myCards = new LinkedList<>();
	}
	
	public Player(String player){
		setName(player);
		myCards = new LinkedList<>();
	}
	
	public final void setName(String newName){
		name = newName;
	}
	
	public String getName(){
		return this.name;
	}

	public void setPC(boolean isPC){
		this.isPC = isPC;
	}

	public boolean isPC(){
		return isPC;
	}
	
	public void obtainCard(UNOCard card){
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
		saidUNO = true;
	}
	
	public void setSaidUNOFalse(){
		saidUNO = false;
	}
	
	public void setCards(){
		myCards.clear();
	}

	public LinkedList<UNOCard> getCards() {
		return myCards;
	}
}
