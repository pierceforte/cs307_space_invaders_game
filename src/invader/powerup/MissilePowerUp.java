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
     * Create a missile power up
     * @param xPos: x position of the speed powerup
     * @param yPos: y position of the speed power up
     * @param id: id later used for testing
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
