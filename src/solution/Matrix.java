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
import java.awt.Point;

/**
 * Probabilty matrix used to determine the most likely grid space containing a ship
 *
 * @author Dylan Bassett 000732642
 */
public class Matrix {
    
    // Instance of the battleship game being played
    private final BattleShip game;
    
    // Integer array holding the data used to determine probability
    private final int[][][] matrix;
    
    /**
     * Constructor assigns the battleship game to instance variable and initializes
     * the probabilty matrix to its default values
     * 
     * @param game Battleship game instance
     */
    public Matrix(BattleShip game) {
        this.game = game;
        matrix = new int[5][game.BOARDSIZE][game.BOARDSIZE];
        for(int i = 0; i < game.BOARDSIZE; i++) {
            for(int j = 0; j < game.BOARDSIZE; j++) {
                matrix[1][i][j] = i + 1;
                matrix[2][j][i] = i + 1;
                matrix[3][i][j] = game.BOARDSIZE - i;
                matrix[4][j][i] = game.BOARDSIZE - i;
            }
        }
        for(int i = 0; i < game.BOARDSIZE; i++) {
            for(int j = 0; j < game.BOARDSIZE; j++) {
                matrix[0][i][j] = this.findProbability(i, j);
            }
        }
    }
    
    /**
     * Re-evaluates the game board, and calculates the probabilties for each grid space
     */
    private void recalculate() {
        int count = 0;
        for(int i = 0; i < game.BOARDSIZE; i++) {
            for(int j = 0; j < game.BOARDSIZE; j++) {
                if(matrix[0][i][j] != 0) {
                    matrix[1][j][i] = count + 1;
                    matrix[2][i][j] = count + 1;
                    count++;
                } else {
                    count = 0;
                }
            }
            count = 0;
        }
        count = game.BOARDSIZE - 1;
        for(int i = game.BOARDSIZE - 1; i >= 0; i--) {
            for(int j = game.BOARDSIZE - 1; j >= 0; j--) {
                if(matrix[0][i][j] == 0) {
                    count = game.BOARDSIZE - 1;
                } else {
                    matrix[4][i][j] = game.BOARDSIZE - count;
                    count--;
                }
            }
            count = game.BOARDSIZE - 1;
        }
        count = game.BOARDSIZE - 1;
        for(int i = game.BOARDSIZE - 1; i >= 0; i--) {
            for(int j = game.BOARDSIZE - 1; j >= 0; j--) {
                if(matrix[0][j][i] == 0) {
                    count = game.BOARDSIZE - 1;
                } else {
                    matrix[3][j][i] = game.BOARDSIZE - count;
                    count--;
                }
            }
            count = game.BOARDSIZE - 1;
        }
        for(int i = 0; i < game.BOARDSIZE; i++) {
            for(int j = 0; j < game.BOARDSIZE; j++) {
                matrix[0][i][j] = this.findProbability(i, j);
            }
        }
    }
    
    /**
     * Sets the probabilty of the grid space to zero, used when a shot has been taken
     * 
     * @param target Point containing the x and y coordinates of the shot to 
     */
    public void markShot(Point target) {
        for(int i = 0; i < 5; i++) {
            matrix[i][target.x][target.y] = 0;
        }
        this.recalculate();
    }
    
    /**
     * Method that selects and returns the most probable location for a ship
     * 
     * @return Point object containing x and y coordinates of the probable location
     */
    public Point getTarget() {
        int highest = 0;
        for(int i = 0; i < game.BOARDSIZE; i++) {
            for(int j = 0; j < game.BOARDSIZE; j++) {
                if(matrix[0][i][j] > highest) {
                    highest = matrix[0][i][j];
                }
            }
        }
        Point target = new Point();
        for(int i = 0; i < game.BOARDSIZE; i++) {
            for(int j = 0; j < game.BOARDSIZE; j++) {
                if(matrix[0][i][j] == highest) {
                    target = new Point(i, j);
                }
            }
        }
        return target;
    }
    
    /**
     * Method to display the probabilty matrix in the console output
     * 
     */
    public void printMatrix() {
        int x = 0;
        //for(int x = 0; x < 5; x++) {
        for(int i = 0; i < game.BOARDSIZE; i++) {
                for(int j = 0; j < game.BOARDSIZE; j++) {
                    System.out.printf("%3d ", matrix[x][i][j]);
                }
                System.out.printf("\n");
            }
            System.out.printf("\n");
        //}
    }
    
    /**
     * Method that determines the probabilty for a given grid location
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return probabilty of a ship (0-100)
     */
    private int findProbability(int x, int y) { 
        int horizontalScore = Math.abs(matrix[2][x][y] - matrix[4][x][y]);
        horizontalScore = (matrix[2][x][y] + matrix[4][x][y]) - horizontalScore;
        int verticalScore = Math.abs(matrix[1][x][y] - matrix[3][x][y]);
        verticalScore = (matrix[1][x][y] + matrix[3][x][y]) - verticalScore;
        return horizontalScore * verticalScore;
    }
    
    /**
     * Method that determines if a shot has already been taken
     * 
     * @param target Point object containing the x and y coordinate of the shot to be checked
     * @return boolean value specifying if the shot has been taken already
     */
    public boolean isShot(Point target) {
        return matrix[0][target.x][target.y] == 0;
    }
    
    /**
     * Getter method for the probabilty matrix
     * 
     * @return returns the probabilty matrix
     */
    public int[][][] getMatrix() {
        return matrix;
    }
}