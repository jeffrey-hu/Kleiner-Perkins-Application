enum Suit {
    Hearts, Spades, Diamonds, Clubs;
}

enum CardValue {
    Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
}

public class Card {
    private Suit suit;
    private CardValue value;

    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    private String valueToString() {
        switch (this.value) {
            case Two:
                return "two";
            case Three:
                return "three";
            case Four:
                return "four";
            case Five:
                return "five";
            case Six:
                return "six";
            case Seven:
                return "seven";
            case Eight:
                return "eight";
            case Nine:
                return "nine";
            case Ten:
                return "ten";
            case Jack:
                return "jack";
            case Queen:
                return "queen";
            case King:
                return "king";
            case Ace:
                return "ace";
        }

        return null;
    }

    private String suitToString() {
        switch (this.suit) {
            case Hearts:
                return "hearts";
            case Clubs:
                return "clubs";
            case Diamonds:
                return "diamonds";
            case Spades:
                return "spades";
        }

        return null;
    }

    @Override
    public String toString() {
        return valueToString() + " of " + suitToString();
    }

    public Suit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }
}
