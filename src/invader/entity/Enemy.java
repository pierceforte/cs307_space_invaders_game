package invader.entity;

import invader.MovingObject;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Enemy extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;

    private double xPos;
    private double yPos;
    private int lives;

    public Enemy(double xPos, double yPos, int lives) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.lives = lives;
    }

}
