package GameModel.Managers;

import GameModel.Dealer;
import GameModel.Player;
import View.UNOCard;
import java.util.Stack;

public class CardManager {
    private final Dealer dealer;
    private final Stack<UNOCard> cardStack;
    private final Player[] players;

    public CardManager(Player[] players) {
        this.players = players;
        dealer = new Dealer();
        cardStack = dealer.shuffle();
        dealer.spreadOut(players);
    }

    public UNOCard drawCard() {
        if (!cardStack.isEmpty()) {
            return cardStack.pop();
        } else {
            // Handle empty card stack scenario
            return null;
        }
    }

    public int remainingCards() {
        return cardStack.size();
    }

    public void removePlayedCard(UNOCard playedCard) {
        for (Player player : players) {
            if (player.hasCard(playedCard)) {
                player.removeCard(playedCard);
                // Additional UNO state handling if needed
                break;
            }
        }
    }

    public void drawPlus(int times) {
        for (Player p : players) {
            if (!p.isMyTurn()) {
                for (int i = 0; i < times; i++) {
                    p.obtainCard(drawCard());
                }
            }
        }
    }
}
