package CardModel;
/*
Code created by Josh Braza 
*/
import View.UNOCard;
import java.awt.Color;

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
