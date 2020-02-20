package invader.entity;

import invader.powerup.PowerUp;
import invader.projectile.Projectile;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Inherits the abstract entity class, which is used to create an enemy object.
 */

public class Enemy extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final String IDENTIFIER = "enemy";
    public static final String ENEMY_IMG_PREFIX = "enemy";
    public static final String ENEMY_IMG_EXTENSION = ".png";
    public static final int DEFAULT_Y_SPEED = 0;
    public static final int DEFAULT_TIME_BETWEEN_SHOTS = 20;
    public static final double MIN_TIME_BETWEEN_SHOTS = 0.5;
    public static final int EARLIEST_START_FIRING_TIME = 5;
    public static final int LATEST_START_FIRING_TIME = 20;
    public static final int POINTS_PER_HIT = 25;
    public static final boolean IS_EVIL = true;

    private PowerUp powerUp;
    private boolean hasPowerUp = false;

    /**
     * Create an Enemy
     * @param xPos: x position of the enemy
     * @param yPos: y position of the enemy
     * @param xSpeed: x speed of the enemy
     * @param ySpeed: y speed of the enemy
     * @param lives: how many lives the enemy has
     * @param idNumber: id number of the projectile for testing
     * @param powerUp: the power up that the enemy should release when destroyed
     */
    public Enemy(double xPos, double yPos, double xSpeed, double ySpeed, int lives, int idNumber, PowerUp powerUp) {
        super(xPos, yPos, xSpeed, ySpeed, WIDTH, HEIGHT, DEFAULT_TIME_BETWEEN_SHOTS,
                IS_EVIL, ENEMY_IMG_PREFIX + lives + ENEMY_IMG_EXTENSION);
        setLives(lives);
        this.setId(IDENTIFIER + idNumber);
        addToStartShootingTime(ThreadLocalRandom.current().nextInt(EARLIEST_START_FIRING_TIME, LATEST_START_FIRING_TIME));
        if (powerUp != null) {
            this.powerUp = powerUp;
            hasPowerUp = true;
        }
        setPointsPerHit(POINTS_PER_HIT);
    }

    /** Returns the power up that the enemy holds
     * @return PowerUp the power up that the enemy holds
     */
    public PowerUp getPowerUp() {
        return powerUp;
    }

    /** Returns if the enemy holds a power up
     * @return whether enemy has power up
     */
    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    /**
     * Create a projectile for the enemy
     * @param rotation rotation of the projectile's image
     * @param idNumber id number of the projectile for testing
     * @return the projectile that is fired
     */
    @Override
    public Projectile createProjectile(double rotation, int idNumber) {
        return defaultProjectileFire(rotation, idNumber);
    }
}
