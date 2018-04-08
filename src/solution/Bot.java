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
import java.util.Stack;

/**
 * Battleship solving bot designed around a probabilty matrix
 *
 * @author Dylan Bassett 000732642
 */
public class Bot {
    
    // Instance of the battleship game being played
    private BattleShip game;
    
    // Probabilty matrix object
    private Matrix matrix;
    
    // Stack of targets to be shot
    private Stack<Point> targets;
    
    // Boolean value that determines if the matrix is printed each shot
    private boolean DEBUG;
    
    /**
     * Constructor that assigns the battleship game instance, probabilty matrix, target stack
     * and sets the DEBUG value to false
     * 
     * @param game Battleship game instance
     */
    public Bot(BattleShip game) {
        this.game = game;
        matrix = new Matrix(game);
        targets = new Stack<>();
        DEBUG = false;
    }
    
    /**
     * Constructor that assigns the battleship game instance, probabilty matrix, target stack
     * and sets the DEBUG value to true
     * 
     * @param game Battleship game instance
     * @param DEBUG Boolean value to set as DEBUG
     */
    public Bot(BattleShip game, boolean DEBUG) {
        this.game = game;
        matrix = new Matrix(game);
        targets = new Stack<>();
        if(DEBUG) {
            System.out.printf("\nMatrix Created\n");
            matrix.printMatrix();
            this.DEBUG = true;
        }
    }
    
    /**
     * Method that fires a shot at the battleship game instance, the shot is detemined
     * by first checking the target stack for a shot and if empty get the most probable
     * shot from the probabilty matrix
     * 
     */
    public void shoot() {
        Point target;
        // If targets exist in Stack assign to target
        if(targets.size() > 0) {
            target = targets.pop();
        // If Stack is empty get target from matrix
        } else {
            target = matrix.getTarget();
        }
        // Mark the shot in the matrix
        matrix.markShot(target);
        // Shoot the target and save the result to hit variable
        boolean hit = game.shoot(target);
        // If hit is true add surrounding valid targets to stack
        if(hit) {
            this.addTargets(target);
            // If DEBUG is set to true print shot and matrix
            if(DEBUG) {
                System.out.printf("Hit at target [ %d, %d ]\n", target.x, target.y);
                matrix.printMatrix();
            }
        // If DEBUG is set to true print shot and matrix
        } else {
            if(DEBUG) {
                System.out.printf("Miss at target [ %d, %d ]\n", target.x, target.y);
                matrix.printMatrix();
            }
        }
    }
    
    /**
     * Method that adds the four adjacent grid locations to the target Stack if they
     * are valid shots and not already fired upon
     * 
     * @param p Point object that will be used as reference point
     */
    private void addTargets(Point p) {
        // If location to left of target is valid and not shot add to target Stack
        if(p.x > 0) {
            Point target = new Point(p.x - 1, p.y);
            if(!matrix.isShot(target)) {
                targets.push(target);
                matrix.markShot(target);
            }
        }
        // If location on top of target is valid and not shot add to target Stack
        if(p.y > 0) {
            Point target = new Point(p.x, p.y - 1);
            if(!matrix.isShot(target)) {
                targets.push(target);
                matrix.markShot(target);
            }
        }
        // If location to right of target is valid and not shot add to target Stack
        if(p.x < game.BOARDSIZE - 1) {
            Point target = new Point(p.x + 1, p.y);
            if(!matrix.isShot(target)) {
                targets.push(target);
                matrix.markShot(target);
            }
        }
        // If location below target is valid and not shot add to target Stack
        if(p.y < game.BOARDSIZE - 1) {
            Point target = new Point(p.x, p.y + 1);
            if(!matrix.isShot(target)) {
                targets.push(target);
                matrix.markShot(target);
            }
        }
    }
}