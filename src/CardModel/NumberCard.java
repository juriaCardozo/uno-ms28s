package CardModel;
/*
Code created by Josh Braza 
*/
import View.UNOCard;
import java.awt.Color;

public class NumberCard extends UNOCard {

	public NumberCard(){
	}
	
	public NumberCard(Color cardColor, String cardValue, boolean minimalistStyle){
		super(cardColor, NUMBERS, cardValue, minimalistStyle);
	}

}
