package invader.entity;

import invader.Game;
import invader.projectile.Missile;
import invader.projectile.Projectile;

/**
 * This class inherits the abstract entity class, which is used to create a spaceship object.
 *
 * Has its own methods compared to other entities like receiving power ups and reacting to them.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */

public class Spaceship extends Entity {
    public static final int DEFAULT_X_SPEED = 0;
    public static final int DEFAULT_Y_SPEED = 0;
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final int DEFAULT_X_POS = Game.GAME_WIDTH/2 - Spaceship.WIDTH/2;
    public static final int DEFAULT_Y_POS = Game.GAME_HEIGHT - 30;
    public static final int DEFAULT_X_SPEED_ON_KEY_PRESS = 10;
    public static final int DEFAULT_LIVES = 3;
    public static final int POINTS_PER_HIT = 0;
    public static final int DEFAULT_TIME_BETWEEN_SHOTS = 1;
    public static final String IDENTIFIER = "spaceship";
    public static final String SPACESHIP_IMG_NAME = "spaceship.png";
    public static final boolean IS_EVIL = false;

    private double xSpeedOnKeyPress = DEFAULT_X_SPEED_ON_KEY_PRESS;
    private boolean hasMissilePowerUp = false;

    /**
     * Create a spaceship
     * @param xPos: x position of the spaceship
     * @param yPos: y position of the spaceship
     */
    public Spaceship(double xPos, double yPos) {
        super(xPos, yPos, DEFAULT_X_SPEED, DEFAULT_Y_SPEED, HEIGHT, WIDTH, DEFAULT_TIME_BETWEEN_SHOTS, IS_EVIL, SPACESHIP_IMG_NAME);
        setLives(DEFAULT_LIVES);
        setPointsPerHit(POINTS_PER_HIT);
        this.setId(IDENTIFIER);
        setStartShootingTime(0);
    }

    /**
     * Sets the location of the spaceship to the other side of the screen when it hits the end of the screen
     */
    public void wrap() {
        if (this.getX() > Game.GAME_WIDTH - this.getFitWidth()) {
            this.setX(0);
        }
        else if (this.getX() < 0) {
            this.setX(Game.GAME_WIDTH - this.getFitWidth());
        }
    }

    /** Get the x speed of the spaceship when left or right key is pressed
     * @return Returns the x speed of the spaceship when left or right key is pressed
     */
    public double getXSpeedOnKeyPress() {
        return xSpeedOnKeyPress;
    }

    /**
     * Set the x speed of the spaceship when left or right key is pressed
     * @param xSpeedOnKeyPress the new x speed of the spaceship when left or right key is pressed
     */
    public void setXSpeedOnKeyPress(double xSpeedOnKeyPress) {
        this.xSpeedOnKeyPress = xSpeedOnKeyPress;
    }

    /**
     * Set whether the missile power up for the spaceship is active
     * @param isActive whether the missile power up is active
     */
    public void setMissilePowerUp(boolean isActive) {
        hasMissilePowerUp = isActive;
    }

    /**
     * Check if the spaceship has missile power up
     * @return whether the spaceship has missle power up
     */
    public boolean hasMissilePowerUp() {
        return hasMissilePowerUp;
    }

    /**
     * Create a projectile fired from the spaceship
     * @param rotation rotation of the projectile's image
     * @param idNumber id number of the projectile for testing
     * @return the projectile that is fired
     */
    @Override
    public Projectile createProjectile(double rotation, int idNumber) {
        Projectile projectile;
        if (this.hasMissilePowerUp()) {
            projectile = new Missile(this.getX() + this.getFitWidth()/2,
                    this.getY(), false, rotation, idNumber);
        }
        else {
            projectile = defaultProjectileFire(rotation, idNumber);
        }
        return projectile;
    }


}
