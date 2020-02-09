package invader.entity;

import invader.powerup.PowerUp;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Enemy extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final String ENEMY_IMG_NAME= "enemy.png";

    private PowerUp powerUp;

    public Enemy(double xPos, double yPos, double xSpeed, double ySpeed, int lives, int idNumber, PowerUp powerUp) {
        super(xPos, yPos, xSpeed, ySpeed, WIDTH, HEIGHT, ENEMY_IMG_NAME);
        setLives(lives);
        this.setId("enemy" + idNumber);
        addToStartShootingTime(ThreadLocalRandom.current().nextInt(1, 40));
        this.powerUp = powerUp;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }



}
