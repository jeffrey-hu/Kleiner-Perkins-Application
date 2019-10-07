import java.util.Scanner;

enum HandStatus {
    //FirstMove means the hand is on the first move.
    //CanContinue means that the player has not busted or stood on the current hand
    //Finished means that the hand has either busted or the player has stood or the player has doubled down
    //Blackjack means you have gotten a blackjack
    FirstMove, CanContinue, Finished;
}

public class Game {
    Deck deck = new Deck();
    Player player = new Player();
    Player house = new Player();
    HandStatus handStatus = HandStatus.FirstMove;
    Scanner input = new Scanner(System.in);
    int handCount = 1;

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    public void play() {
        startGame();
        determinePossibleMoves();
    }

    public void startGame() {
        System.out.println("Welcome to blackjack!\n");
        house.getCurrentHand().addCard(deck.drawCard());

        player.getCurrentHand().addCard(deck.drawCard());
        player.getCurrentHand().addCard(deck.drawCard());
    }

    public void resetGame() {
        deck = new Deck();
        player = new Player();
        house = new Player();
        handStatus = HandStatus.FirstMove;
    }

    public void determinePossibleMoves() {
        describeHands();

        if (checkBlackjack(player.getCurrentHand())) {
            playNextHand();
            return;
        }
        switch (handStatus) {
            case FirstMove:
                makeFirstMove();
                break;
            case CanContinue:
                makeGeneralMove();
                break;
            case Finished:
                playNextHand();
        }
    }

    public void playNextHand() {
        System.out.println("You have finished playing out your hand");
        describeHandFinish(player.getCurrentHand());
        handCount++;

        handStatus = HandStatus.CanContinue;
        if (player.playNextHand()) {
            determinePossibleMoves();
        } else {
            housePlays();
            return;
        }
    }

    public void makeFirstMove() {
        System.out.println("Your move:");
        if (handStatus == HandStatus.FirstMove && (player.getCurrentHand().getCards().get(0).getValue() == player.getCurrentHand().getCards().get(1).getValue())) {
            System.out.println("Hit (h), Stand (s), Double Down (d), Surrender (su), Split (sp)");
        } else {
            System.out.println("Hit (h), Stand (s), Double Down (d), Surrender (su)");
        }
        String userFlag;

        userFlag = input.nextLine();
        switch (userFlag) {
            case "d":
                player.doubleDown(deck.drawCard());
                handStatus = HandStatus.Finished;
                determinePossibleMoves();
                break;
            case "h":
                player.hit(deck.drawCard());
                if (player.getCurrentHand().getValue() >= 21) {
                    handStatus = HandStatus.Finished;
                } else {
                    handStatus = HandStatus.CanContinue;
                }
                determinePossibleMoves();
                break;
            case "s":
                handStatus = HandStatus.Finished;
                determinePossibleMoves();
                break;
            case "sp":
                player.split(deck.drawCard(), deck.drawCard());
                handStatus = HandStatus.CanContinue;
                determinePossibleMoves();
                break;
            case "su":
                System.out.println("You have surrendered the current game. Starting a new game.");
                resetGame();
                play();
                break;
            default:
                System.out.println("Invalid input. Please try again");
                determinePossibleMoves();
        }
    }

    public void makeGeneralMove() {
        if (player.getHands().size() > 1) {
            System.out.println("Your move on hand " + handCount + "/" + player.getHands().size());
        } else {
            System.out.println("Your move");
        }
        System.out.println("Hit (h), Stand (s)");
        String userFlag;
        userFlag = input.nextLine();
        switch (userFlag) {
            case "h":
                player.hit(deck.drawCard());
                if (player.getCurrentHand().getValue() >= 21) {
                    handStatus = HandStatus.Finished;
                } else {
                    handStatus = HandStatus.CanContinue;
                }
                break;
            case "s":
                handStatus = HandStatus.Finished;
                break;
            default:
                System.out.println("Invalid input. Please try again");
                determinePossibleMoves();
        }
        determinePossibleMoves();
    }

    public void describeHands() {
        System.out.println("This is the house's hand: ");
        System.out.println(house.getCurrentHand());

        System.out.println("This is your hand: ");
        System.out.println(player.getCurrentHand());
    }

    public boolean checkBlackjack(Hand h) {
        if (h.getValue() == 21 && handStatus == HandStatus.FirstMove) {
            return true;
        } else {
            return false;
        }
    }

    public void describeHandFinish(Hand h) {
        if (h.getValue() > 21) {
            System.out.println("This hand has busted");
        } else if (h.getValue() == 21 && handStatus == HandStatus.FirstMove) {
            System.out.println("You have a blackjack");
        } else {
            System.out.println("Your hand has a FINAL VALUE of: " + h.getValue());
        }
        System.out.println();
    }

    public void housePlays() {
        if (house.getCurrentHand().getValue() < 17) {
            Card nextCard = deck.drawCard();
            house.hit(nextCard);
            System.out.println("house drew the " + nextCard);
            housePlays();
        } else {
            System.out.println("House hand has a FINAL VALUE of: " + house.getCurrentHand().getValue());
            describeGameFinish();
        }
    }

    public void describeGameFinish() {
        int count = 1;
        for (Hand h : player.getHands()) {
            System.out.println();

            if (player.getHands().size() > 1) {
                System.out.println("RESULTS of hand " + count + "/" + player.getHands().size());
            } else {
                System.out.println("RESULTS of hand");
            }

            //if you bust
            if (h.getValue() > 21) {
                System.out.println("You have lost this hand because you have gone over 21");
                continue;
            }


            //if house bust
            if (house.getCurrentHand().getValue() > 21) {
                System.out.println("You have won this hand because the house has busted");
                continue;
            }

            if (h.getValue() > house.getCurrentHand().getValue()) {
                System.out.println("You have won this hand because your hand's value of " + h.getValue() + " is greater than the house's value of " + house.getCurrentHand().getValue());
            } else if (h.getValue() == house.getCurrentHand().getValue()) {
                System.out.println("This hand is a push because your hand's value of " + h.getValue() + " is equal to the house's value of " + house.getCurrentHand().getValue());
            } else {
                System.out.println("You have lost this hand because your hand's value of " + h.getValue() + " is less than the house's value of " + house.getCurrentHand().getValue());
            }

            count++;
        }
    }
}
