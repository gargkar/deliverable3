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

public class Main {
    public static void main(String[] args) {
        // Instantiate the specific game (GoFishGame)
        Game game = new GoFishGame("Go Fish");

        // Initialize players
        game.initializePlayers();

        // Start the game
        game.play();
    }
}
