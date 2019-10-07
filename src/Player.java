import java.util.ArrayList;

public class Player {
    private ArrayList<Hand> hands;
    private Hand currentHand;

    public Player() {
        hands = new ArrayList<>();
        hands.add(new Hand());
        currentHand = hands.get(0);
    }

    public void hit(Card card) {
        currentHand.addCard(card);
    }

    public void doubleDown(Card card) {
        currentHand.addCard(card);
    }

    public void split(Card card1, Card card2) {
        Hand newHand = new Hand();
        hands.add(newHand);

        for (Hand h : hands) {
            h.setValue(0);
        }

        Card splitCard1 = currentHand.getCards().remove(0);
        Card splitCard2 = currentHand.getCards().remove(0);
        currentHand.addCard(splitCard1);
        currentHand.addCard(card1);

        newHand.addCard(splitCard2);
        newHand.addCard(card2);
    }

    public Hand getCurrentHand() {
        return currentHand;
    }

    public ArrayList<Hand> getHands() {
        return hands;
    }

    //returns if there is another hand to play. If player is on last hand, returns false.
    public boolean playNextHand() {
        if (hands.indexOf(currentHand) == hands.size() - 1) {
            return false;
        } else {
            currentHand = hands.get(hands.indexOf(currentHand) + 1);
            return true;
        }
    }
}
