package CardModel;
/*
Code created by Josh Braza
*/
import Interfaces.GameConstants;
import ServerController.MyCardListener;
import View.UNOCard;
import java.awt.Color;
import java.util.LinkedList;

/**
 * This Class contains standard 108-Card stack
 */
public class CardDeck implements GameConstants {
	private final LinkedList<UNOCard> UNOcards;
	
	public CardDeck(){
		UNOcards = new LinkedList<>();
		
		addCards();
		addCardListener(CARDLISTENER);
	}
		
	//Create 108 cards for this CardDeck
	private void addCards() {
		for(Color color:UNO_COLORS){
			
			//Create 76 NumberCards --> doubles except 0s.
			for(int num : UNO_NUMBERS){
				int i=0;
				do{
					UNOcards.add(new NumberCard(color, Integer.toString(num)));
					i++;
				}while(num!=0 && i<2);
			}
			
			//Create 24 ActionCards --> everything twice
			for(String type : ActionTypes){
				for(int i=0;i<2;i++)
					UNOcards.add(new ActionCard(color, type));
			}
		}
		
		for(String type : WildTypes){
			for(int i=0;i<4;i++){
				UNOcards.add(new WildCard(type));
			}
		}
		
	}
	
	//Cards have MouseListener
	public final void addCardListener(MyCardListener listener){
		for(UNOCard card: UNOcards)
		card.addMouseListener(listener);
	}
	
	public LinkedList<UNOCard> getCards(){
		return UNOcards;
	}
}
