/********************************************************************************
 *  Author Name:    Dylan Bassett                                               *
 *  Student Number: 000732642                                                   *
 *  File Date:      December 20th 2017                                          *
 *                                                                              *
 *  I, Dylan Bassett, 000732642 certify that this material is my original work. *
 *  No other person's work has been used without due acknowledgement.           *
 *                                                                              *
 *******************************************************************************/
package solution;

import battleship.BattleShip;

/**
 * Starting code for Comp10152 - Lab#6
 *
 * @author mark.yendt
 */
public class COMP10152_Lab6 {

    static final int NUMBEROFGAMES = 10000;

    public static void startingSolution() {
        
        int totalShots = 0;
        System.out.println(BattleShip.version());
        
        long time = System.nanoTime();
        
        for (int game = 0; game < NUMBEROFGAMES; game++) {

            BattleShip battleShip = new BattleShip();
            Bot bot = new Bot(battleShip);
            
            while (!battleShip.allSunk()) {
                bot.shoot();
            }
            int gameShots = battleShip.totalShotsTaken();
            totalShots += gameShots;
        }
        time = System.nanoTime() - time;
        System.out.printf("Bot - The Average # of Shots required in %d games to sink all Ships = %.2f\nTime to complete = %.2fs\n", NUMBEROFGAMES, (double) totalShots / NUMBEROFGAMES, time / 1000000000f);
    }

    public static void main(String[] args) {
        startingSolution();
    }
}
