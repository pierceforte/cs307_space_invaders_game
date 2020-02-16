package invader.powerup;

import invader.entity.Spaceship;

public class SpaceshipSpeedPowerUp extends PowerUp {
    public static final String IMG_NAME = "fastpower.gif";
    public static final int INCREASED_SPEED = 25;
    public static final int TIME_ACTIVE = 8;

    /**
     * Constructor
     * @param xPos
     * @param yPos
     * @param id
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
