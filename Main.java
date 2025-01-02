import java.util.Random;
import java.util.Scanner;

public class Main {  //class name

    public static void main(String[] args) {
        // declare variables
        String anotherCard, AGAIN = "y";
        int nextCard, c1, c2, dealCard_1, dealCard_2;
        int cardTotal = 0, dTotal = 0;
        int playerTokens = 100;
        int betAmount;

        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();

        // game loop
        while ("y".equals(AGAIN) && playerTokens > 0) {
            // display player tokens and ask for bet
            System.out.println("you have " + playerTokens + " tokens.");
            System.out.print("place your bet: ");
            betAmount = keyboard.nextInt();
            keyboard.nextLine(); // consume newline after nextInt

            // check if bet is valid
            if (betAmount > playerTokens) {
                System.out.println("you don't have enough tokens to place that bet. please bet a lower amount.");
                continue; // retry bet if invalid
            }

            // deal cards to player and dealer
            c1 = drawCard(random);  // Player's first card
            c2 = drawCard(random);  // Player's second card
            dealCard_1 = drawCard(random);  // Dealer's first card (visible)
            dealCard_2 = drawCard(random);  // Dealer's second card (hidden)
            cardTotal = c1 + c2;
            dTotal = dealCard_1 + dealCard_2;

            // display dealer's visible card and player's cards
            System.out.println("dealer shows: " + dealCard_1);
            System.out.println("your cards: " + c1 + ", " + c2 + " (total: " + cardTotal + ")");

            // player's decision to hit or stand
            System.out.println("another card (y/n)?: ");
            anotherCard = keyboard.nextLine();

            // player's turn to hit
            while ("y".equals(anotherCard)) {
                nextCard = drawCard(random);  // Draw another card for the player
                cardTotal += nextCard;
                System.out.println("you drew: " + nextCard);
                System.out.println("your total is now: " + cardTotal);

                // check if player busts
                if (cardTotal > 21) {
                    System.out.println("you busted! dealer wins.");
                    playerTokens = Math.max(1, playerTokens - betAmount);  // Deduct tokens, but not below 1
                    System.out.println("you now have " + playerTokens + " tokens.");
                    break; // stop game after bust
                }

                // ask if player wants another card
                if (cardTotal < 21) {
                    System.out.print("another card (y/n)?: ");
                    anotherCard = keyboard.nextLine();
                }
            }

            // dealer's turn to play if player didn't bust
            if (cardTotal <= 21) {
                System.out.println("dealer's cards: " + dealCard_1 + ", " + dealCard_2 + " (total: " + dTotal + ")");

                // dealer draws cards if total < 17
                while (dTotal < 17) {
                    int dealerCard = drawCard(random);  // Dealer draws another card
                    dTotal += dealerCard;
                    System.out.println("dealer drew: " + dealerCard);
                    System.out.println("dealer's total is now: " + dTotal);
                }

                // check if dealer busts
                if (dTotal > 21) {
                    System.out.println("dealer busted! you win!");
                    playerTokens += betAmount;
                    System.out.println("you now have " + playerTokens + " tokens.");
                } else {
                    // compare player and dealer totals to decide winner
                    System.out.println("dealer's final total: " + dTotal);
                    if (cardTotal > dTotal) {
                        System.out.println("you win!");
                        playerTokens += betAmount;
                        System.out.println("you now have " + playerTokens + " tokens.");
                    } else if (cardTotal < dTotal) {
                        System.out.println("dealer wins!");
                        playerTokens = Math.max(1, playerTokens - betAmount);  // Deduct tokens, but not below 1
                        System.out.println("you now have " + playerTokens + " tokens.");
                    } else {
                        System.out.println("it's a tie!");
                    }
                }
            }

            // check if player has no tokens left
            if (playerTokens <= 0) {
                System.out.println("you have no more tokens left. game over!");
                break; // end the game if no tokens
            }

            // ask player to play again
            System.out.println("do you want to play again? (y/n): ");
            AGAIN = keyboard.nextLine();
        }

        // final message if player has tokens left
        if (playerTokens > 0) {
            System.out.println("thank you for playing! you finished with " + playerTokens + " tokens.");
        }
    }

    // Method to draw a card, with face cards and Ace (1 or 11)
    public static int drawCard(Random random) {
        int card = random.nextInt(13) + 1;  // Draw a number between 1 and 13
        if (card > 10) {
            return 10;  // Face cards (Jack, Queen, King) all count as 10
        } else if (card == 1) {
            return 11;  // Ace is initially 11
        }
        return card;  // Numbered cards (2-10) return their value
    }
}
