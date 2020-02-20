package invader.powerup;

import invader.entity.Spaceship;

/**
 * This class inherits the abstract PowerUp class, implementing the ability for the spaceship to move faster on key press.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */

public class SpaceshipSpeedPowerUp extends PowerUp {
    public static final String IMG_NAME = "fastpower.gif";
    public static final int INCREASED_SPEED = 25;
    public static final int TIME_ACTIVE = 8;

    /**
     * Create a spaceship speed up power up
     * @param xPos: x position of the speed powerup
     * @param yPos: y position of the speed power up
     * @param id: id later used for testing
     */
    public SpaceshipSpeedPowerUp(double xPos, double yPos, String id) {
        super(xPos, yPos, IMG_NAME, id);
        setTimeActive(TIME_ACTIVE);
    }

    @Override
    public void activate(double gameTimer, Spaceship spaceship) {
        spaceship.setXSpeedOnKeyPress(INCREASED_SPEED);
        setTimeWhenActivated(gameTimer);
    }

    @Override
    public void deactivate(double gameTimer, Spaceship spaceship) {
        spaceship.setXSpeedOnKeyPress(Spaceship.DEFAULT_X_SPEED_ON_KEY_PRESS);
    }

    @Override
    public void reapplyPowerUp(double gameTimer, Spaceship spaceship) {
        spaceship.setXSpeedOnKeyPress(INCREASED_SPEED);
    }

}
