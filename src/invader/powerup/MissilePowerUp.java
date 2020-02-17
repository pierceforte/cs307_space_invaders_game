package invader.powerup;

import invader.entity.Spaceship;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Specific Missile power up class that inherits the abstract class Powerup
 * Increases the number of missile on the screen
 */

public class MissilePowerUp extends PowerUp {
    public static final String IMG_NAME = "laserpower.gif";
    public static final int TIME_ACTIVE = 6;

    /**
     * Constructor
     * @param xPos
     * @param yPos
     * @param id
     */
    public MissilePowerUp(double xPos, double yPos, String id) {
        super(xPos, yPos, IMG_NAME, id);
        setTimeActive(TIME_ACTIVE);
    }

    @Override
    public void activate(double gameTimer, Spaceship spaceship) {
        spaceship.setMissilePowerUp(true);
        setTimeWhenActivated(gameTimer);
    }

    @Override
    public void deactivate(double gameTimer, Spaceship spaceship) {
        spaceship.setMissilePowerUp(false);
    }

    @Override
    public void reapplyPowerUp(double gameTimer, Spaceship spaceship) {
        spaceship.setMissilePowerUp(true);
    }
}
