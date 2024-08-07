/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author brahm
 */
import java.util.Scanner;

public class GoFishGame extends Game {

    private final GroupOfCards deck;

    public GoFishGame(String name) {
        super(name);
        deck = new GroupOfCards();
        initializeDeck();
    }

    private void initializeDeck() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.addCard(new Card(rank, suit));
            }
        }
        deck.shuffle();
    }

    @Override
    public void play() {
        // Deal cards to players
        dealCards();

        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            for (Player player : getPlayers()) {
                System.out.println("\n" + player.getName() + "'s turn");
                System.out.println("Your hand: " + player.getHand().getCards());

                // Ask another player for a card
                System.out.print("Ask which player for a card (enter player's name): ");
                String playerName = scanner.nextLine();
                Player otherPlayer = getPlayerByName(playerName);
                if (otherPlayer == null || otherPlayer == player) {
                    System.out.println("Invalid player. Try again.");
                    continue;
                }

                // Ask for a rank
                System.out.print("Enter a rank to ask for: ");
                String rank = scanner.nextLine();

                // Check if the other player has the card
                boolean hasCard = false;
                for (Card card : otherPlayer.getHand().getCards()) {
                    if (card.toString().contains(rank)) {
                        hasCard = true;
                        player.addCardToHand(card);
                        otherPlayer.removeCardFromHand(card);
                        System.out.println("You got the " + card + " from " + otherPlayer.getName() + "!");
                        break;
                    }
                }
                if (!hasCard) {
                    System.out.println(otherPlayer.getName() + " says: Go Fish!");
                    if (deck.getSize() > 0) {
                        Card newCard = deck.getCard(0);
                        player.addCardToHand(newCard);
                        deck.removeCard(newCard);
                        System.out.println("You drew the " + newCard + " from the deck.");
                    } else {
                        System.out.println("The deck is empty.");
                    }
                }
            }
        }
        declareWinner();
    }

    public void dealCards() {
        for (Player player : getPlayers()) {
            for (int i = 0; i < 5; i++) { // Deal 5 cards to each player
                if (deck.getSize() > 0) {
                    Card newCard = deck.getCard(0);
                    player.addCardToHand(newCard);
                    deck.removeCard(newCard);
                } else {
                    System.out.println("The deck is empty.");
                    break;
                }
            }
        }
    }

    private Player getPlayerByName(String name) {
        for (Player player : getPlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public boolean isGameOver() {
        if (deck.getSize() == 0) {
            return true;
        }
        for (Player player : getPlayers()) {
            if (player.getHand().getSize() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void declareWinner() {
        Player winner = null;
        int maxSets = 0;

        for (Player player : getPlayers()) {
            int sets = countSets(player);
            if (sets > maxSets) {
                maxSets = sets;
                winner = player;
            }
            System.out.println(player.getName() + " has " + sets + " sets.");
        }

        if (winner != null) {
            System.out.println("The winner is " + winner.getName() + " with " + maxSets + " sets!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private int countSets(Player player) {
        int[] rankCount = new int[13]; // 13 ranks: 2, 3, 4, ..., Ace
        for (Card card : player.getHand().getCards()) {
            String rank = card.getRank();
            switch (rank) {
                case "2":
                    rankCount[0]++;
                    break;
                case "3":
                    rankCount[1]++;
                    break;
                case "4":
                    rankCount[2]++;
                    break;
                case "5":
                    rankCount[3]++;
                    break;
                case "6":
                    rankCount[4]++;
                    break;
                case "7":
                    rankCount[5]++;
                    break;
                case "8":
                    rankCount[6]++;
                    break;
                case "9":
                    rankCount[7]++;
                    break;
                case "10":
                    rankCount[8]++;
                    break;
                case "Jack":
                    rankCount[9]++;
                    break;
                case "Queen":
                    rankCount[10]++;
                    break;
                case "King":
                    rankCount[11]++;
                    break;
                case "Ace":
                    rankCount[12]++;
                    break;
            }
        }

        int sets = 0;
        for (int count : rankCount) {
            if (count >= 4) {
                sets += count / 4;
            }
        }
        return sets;
    }

}
