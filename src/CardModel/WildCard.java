package CardModel;
/*
Code created by Josh Braza 
*/
import java.awt.Color;

import View.UNOCard;

@SuppressWarnings("serial")
public class WildCard extends UNOCard {
	private Color chosenColor;
	
	public WildCard() {
	}
	
	public WildCard(String cardValue, boolean minimalistStyle){
		super(BLACK, WILD, cardValue, minimalistStyle);
	}
	
	public void useWildColor(Color wildColor){
		chosenColor = wildColor;
	}
	
	public Color getWildColor(){
		return chosenColor;
	}
}
