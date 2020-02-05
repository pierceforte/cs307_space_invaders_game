package invader.entity;

import invader.MovingObject;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Enemy extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final String ENEMY_IMG_NAME= "enemy.png";

    public Enemy(double xPos, double yPos, int lives) {
        super(xPos, yPos, 0, 0, ENEMY_IMG_NAME);
        setLives(lives);
    }



}
