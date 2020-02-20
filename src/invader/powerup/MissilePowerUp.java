package invader.powerup;

import invader.entity.Spaceship;

/**
 * This class inherits the abstract PowerUp class, implementing the ability for the spaceship to shoot missiles that do more damage.
 *
 * @author Pierce Forte
 * @author Jeff Kim
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
