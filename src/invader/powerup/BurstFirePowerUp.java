package invader.powerup;

import invader.entity.Spaceship;

/**
 * This class inherits the abstract PowerUp class, implementing the ability for the spaceship to shoot three lasers in different directions.
 *
 * @author Pierce Forte
 * @author Jeff Kim
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
