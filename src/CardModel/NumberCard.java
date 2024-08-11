package CardModel;
/*
Code created by Josh Braza 
*/
import java.awt.Color;

import View.UNOCard;

@SuppressWarnings("serial")
public class NumberCard extends UNOCard {

	public NumberCard(){
	}
	
	public NumberCard(Color cardColor, String cardValue, boolean minimalistStyle){
		super(cardColor, NUMBERS, cardValue, minimalistStyle);
	}

}
