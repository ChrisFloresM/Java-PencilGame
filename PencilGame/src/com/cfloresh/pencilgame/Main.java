package com.cfloresh.pencilgame;

import java.util.Scanner;
import java.util.Random;

public class Main {

    private static final int LOWER_BOUND_PENCILS = 1;
    private static final int UPPER_BOUND_PENCILS = 3;
    private static final int INTERVAL_OFFSET = 1;

    private static final int RAND_INTERVAL = UPPER_BOUND_PENCILS - LOWER_BOUND_PENCILS + INTERVAL_OFFSET;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random botRand = new Random();

        /* Definning players and initializing the game */
        String[] players = {"Luis", "Robert"}; // Robert is the bot on this case

        /* Intialize the game */
        int[] gameStatus = initializeGame(scan, players);
        int gamePencils = gameStatus[0];
        int playerIdx = gameStatus[1];
        int roundPencils;

        scan.nextLine(); //clear whithespace from input buffer

        /* Performing the game until it ends */
        while (true) {
            roundPencils = playerIdx == 0 ? getNumOfPencils(scan, gamePencils) : getNumOfPencilsBot(gamePencils, botRand);
            gamePencils -= roundPencils;
            playerIdx = selectNextPlayer(++playerIdx, players.length);

            if(gamePencils <= 0) {
                System.out.println(players[playerIdx] + " won!");
                break;
            }


            playRound(gamePencils, players[playerIdx]);
        }
        scan.close();
    }

    public static int[] initializeGame(Scanner scan, String[] players) {
        System.out.println("How many pencils would you like to use: ");
        int gamePencils;

        /* Validation loop for the initial number of pencils */
        while (true) {
            String inputPencils = scan.nextLine();
            if (!validateOnlyDigits(inputPencils, false)) {
                continue;
            }

            gamePencils = Integer.parseInt(inputPencils);
            if (validateNotZero(gamePencils, false)) {
                break;
            }
        }

        /* Finishing setup for the game - asking who whill start the game and performing first round */
        System.out.println("Who will be the first (Luis, Robert): ");
        int playerIdx = getPlayerName(scan, players);
        playRound(gamePencils, players[playerIdx]);

        return new int[]{gamePencils, playerIdx};
    }

    public static int getPlayerName(Scanner scan, String[] players){
        String inputName = scan.next();

        while(!inputName.equals(players[0]) && !inputName.equals(players[1])) {
            System.out.println("Choose between 'Luis' and 'Robert'");
            inputName = scan.next();
        }

        int idx = 0;
        for(String player : players) {
            if(inputName.equals(player)) {
                break;
            }
            idx++;
        }

        return idx;
    }

    public static int selectNextPlayer(int idx, int limit) {
        return idx < limit ? idx : 0;
    }

    public static void playRound(int numOfPencils, String playerName) {

        for(int i = 0; i < numOfPencils; i++) {
            System.out.print("|");
        }

        System.out.println("\n" + playerName + "'s turn!");
    }

    public static int getNumOfPencilsBot(int gamePencils, Random rand) {
        /* Determine winning condition */
        boolean winningCondition = (gamePencils - INTERVAL_OFFSET) % 4 == 0;

        int botPencils;

        do {
            botPencils = winningCondition ? rand.nextInt(RAND_INTERVAL) + INTERVAL_OFFSET
                    : (UPPER_BOUND_PENCILS - (gamePencils + 2*(gamePencils % 2)) % 4);
        } while (botPencils > gamePencils);

            System.out.println(botPencils);

        return botPencils;
    }

    public static int getNumOfPencils(Scanner scan, int gamePencils) {
        boolean validInput = false;
        int numPencils = 0;

        while(!validInput) {
            String input = scan.nextLine();

            /* 1. Validate that he input is numeric */
            if(!validateOnlyDigits(input, true)) continue;

            /* 2. Validate that the input is not Zero */
            numPencils = Integer.parseInt(input);
            if(!validateNotZero(numPencils, true)) continue;

            /* 3. Validate that the input is only 1, 2 or 3 */
            if(numPencils > 3) {
                System.out.println("Possible values: '1', '2' or '3'");
                continue;
            }

            /* 4. Validate that the input is not greather that the amount of pencils on table */
            if(numPencils > gamePencils) {
                System.out.println("Too many pencils were taken");
                continue;
            }

            validInput = true;
        }

        return numPencils;
    }

    private static boolean validateOnlyDigits(String input, boolean inGame) {
        for(char d : input.toCharArray()) {
            if(!Character.isDigit(d)) {
                if(inGame) {
                    System.out.println("Possible values: '1', '2' or '3'");
                } else {
                    System.out.println("The number of pencils should be numeric");
                }
                return false;
            }
        }

        return true;
    }

    private static boolean validateNotZero(int input, boolean inGame) {
        if(input <= 0) {
            if(inGame) {
                System.out.println("Possible values: '1', '2' or '3'");
            } else {
                System.out.println("The number of pencils should be positive");
            }
            return false;
        }
        return true;
    }

}
