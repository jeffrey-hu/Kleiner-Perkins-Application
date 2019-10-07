import java.util.ArrayList;

public class Hand {
    private int value = 0;
    private ArrayList<Card> cards = new ArrayList<>();
    //this is the number of aces that are currently valued at 11. Aces that are valued at 1 are not included in this count.
    private int unconvertedAces = 0;

    //adds a card to the hand and returns the hand's value
    public int addCard(Card nextCard) {
        cards.add(nextCard);

        switch (nextCard.getValue()) {
            case Two:
                value += 2;
                break;
            case Three:
                value += 3;
                break;
            case Four:
                value += 4;
                break;
            case Five:
                value += 5;
                break;
            case Six:
                value += 6;
                break;
            case Seven:
                value += 7;
                break;
            case Eight:
                value += 8;
                break;
            case Nine:
                value += 9;
                break;
            case Ten:
            case Jack:
            case Queen:
            case King:
                value += 10;
                break;
            case Ace:
                if (value + 11 > 21) {
                    value += 1;
                } else {
                    value += 11;
                    unconvertedAces++;
                }
        }

        //if the value is over 21 and there are at least one aces interpreted as an 11 value, change the ace to
        //be interpreted as a 1 value so the hand isn't a bust
        if (value > 21 && unconvertedAces > 1) {
            value = value - 10;
            unconvertedAces --;
        }

        return value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public int numCards() {
        return cards.size();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        String returnString = "";
        for (Card card : cards) {
            returnString = returnString + card.toString() + "\n";
        }

        returnString = returnString + "hand value: " + value + "\n";

        return returnString;
    }
}
