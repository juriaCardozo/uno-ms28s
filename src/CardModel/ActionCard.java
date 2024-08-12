package CardModel;

/*
	Code created by Josh Braza
*/

import View.UNOCard;
import java.awt.Color;

public class ActionCard extends UNOCard{
	
	private static final long serialVersionUID = 1L;
	
	public ActionCard(Color cardColor, String cardValue){
		super(cardColor,ACTION, cardValue);
	}
}
