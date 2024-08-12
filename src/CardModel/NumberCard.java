package CardModel;
/*
Code created by Josh Braza 
*/
import View.UNOCard;
import java.awt.Color;

@SuppressWarnings("serial")
public class NumberCard extends UNOCard {

	public NumberCard(){
	}
	
	public NumberCard(Color cardColor, String cardValue){
		super(cardColor, NUMBERS, cardValue);
	}

}
