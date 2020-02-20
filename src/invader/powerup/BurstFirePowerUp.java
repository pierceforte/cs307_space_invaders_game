package invader.powerup;

import invader.entity.Spaceship;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Specific Burst power up class that inherits the abstract class Powerup
 * Increases the power of missile on the screen
 */

public class BurstFirePowerUp extends PowerUp {

    public static final String IMG_NAME = "burstpower.gif";
    public static final int TIME_ACTIVE = 8;

    /**
     * Create a burst fire power up
     * @param xPos: x position of the speed powerup
     * @param yPos: y position of the speed power up
     * @param id: id later used for testing
     */
    public BurstFirePowerUp(double xPos, double yPos, String id) {
        super(xPos, yPos, IMG_NAME, id);
        setTimeActive(TIME_ACTIVE);
    }

    @Override
    public void activate(double gameTimer, Spaceship spaceship) {
        spaceship.setHasBurstFire(true);
        setTimeWhenActivated(gameTimer);
    }

    @Override
    public void deactivate(double gameTimer, Spaceship spaceship) {
        spaceship.setHasBurstFire(false);
    }

    @Override
    public void reapplyPowerUp(double gameTimer, Spaceship spaceship) {
        spaceship.setHasBurstFire(true);
    }
}
