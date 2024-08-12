package CardModel;
import View.UNOCard;
import java.awt.Color;

@SuppressWarnings("serial")
public class WildCard extends UNOCard {
	private Color chosenColor;
	
	public WildCard() {
	}
	
	public WildCard(String cardValue){
		super(BLACK, WILD, cardValue);
	}
	
	public void useWildColor(Color wildColor){
		chosenColor = wildColor;
	}
	
	public Color getWildColor(){
		return chosenColor;
	}
}
