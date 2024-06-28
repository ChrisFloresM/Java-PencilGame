package com.cfloresh.pencilgame;
import java.util.Random;
import java.util.Scanner;

public class functionTest {
    private static final int LOWER_BOUND_PENCILS = 1;
    private static final int UPPER_BOUND_PENCILS = 3;
    private static final int INTERVAL_OFFSET = 1;

    private static final int RAND_INTERVAL = UPPER_BOUND_PENCILS - LOWER_BOUND_PENCILS + INTERVAL_OFFSET;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random random = new Random();
        int gamePencils = scan.nextInt();

        while( gamePencils != 0) {
            System.out.println(getNumOfPencilsBot(gamePencils, random));
            gamePencils = scan.nextInt();
        }

    }

    public static int getNumOfPencilsBot(int gamePencils, Random rand) {
        /* Determine winning condition */
        boolean winningCondition = (gamePencils - INTERVAL_OFFSET) % 4 == 0;

        return winningCondition ? rand.nextInt(RAND_INTERVAL) + INTERVAL_OFFSET
                : (UPPER_BOUND_PENCILS - (gamePencils + 2*(gamePencils % 2)) % 4);
    }
}
