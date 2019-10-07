import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards = new ArrayList();

    public Deck() {
        initializeDeck();
    }

    public Card drawCard() {
        Random rand = new Random();
        if (cards.size() == 0) {
            return null;
        }

        int removeIndex = rand.nextInt(cards.size());
        return cards.remove(removeIndex);
    }

    private void initializeDeck() {
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }
}
