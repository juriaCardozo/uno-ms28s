package GameModel;
/*
Code created by Josh Braza
*/

import CardModel.WildCard;
import Interfaces.GameConstants;
import View.UNOCard;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class PC extends Player implements GameConstants {
	public PC() {
		setName("PC");
		setPC(true);
		super.setCards();
	}

	//PC plays a card
	public boolean play(UNOCard topCard) {
		Color color = topCard.getType() == WILD ? ((WildCard) topCard).getWildColor() : topCard.getColor();
		String value = topCard.getValue();
		
		if(playCard(color, value)){
			return true;
		}

		return playWildCard();
	}

	private boolean playCard(Color color, String value) {
		for (UNOCard card : getAllCards()) {
			if(card.getColor().equals(color) || card.getValue().equals(value)){
				dispatchCardEvent(card);
				return true;
			}
		}
		return false;
	}
	private boolean playWildCard(){
		for(UNOCard card : getAllCards()){
			if(card.getType() == WILD){
				dispatchCardEvent(card);
				return true;
			}
		}
		return false;
	}

	private void dispatchCardEvent(UNOCard card){
		try {
			MouseEvent doPress = new MouseEvent(card, MouseEvent.MOUSE_PRESSED,
						System.currentTimeMillis(),
						(int) MouseEvent.MOUSE_EVENT_MASK, 5, 5, 1, true);
			card.dispatchEvent(doPress);

			MouseEvent doRelease = new MouseEvent(card, MouseEvent.MOUSE_RELEASED,
							System.currentTimeMillis(),
							(int) MouseEvent.MOUSE_EVENT_MASK, 5, 5, 1, true);
					card.dispatchEvent(doRelease);
		}
		catch (Exception e) {
			System.err.println("Erro ao jogar carta: " + e.getMessage());
		}
	}
}
