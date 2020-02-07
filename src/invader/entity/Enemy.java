package invader.entity;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Enemy extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final String ENEMY_IMG_NAME= "enemy.png";

    public Enemy(double xPos, double yPos, int lives, int idNumber) {
        super(xPos, yPos, 0, 0, WIDTH, HEIGHT, ENEMY_IMG_NAME);
        setLives(lives);
        this.setId("enemy" + idNumber);
        addToStartShootingTime(ThreadLocalRandom.current().nextInt(1, 40));
    }



}
