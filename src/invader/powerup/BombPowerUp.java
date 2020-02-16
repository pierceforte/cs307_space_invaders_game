package invader.powerup;

import invader.entity.Spaceship;

public class BombPowerUp extends PowerUp {
    public static final String IMG_NAME = "extraballpower.gif";
    public static final int TIME_ACTIVE = 8;

    public BombPowerUp(double xPos, double yPos, String id) {
        super(xPos, yPos, IMG_NAME, id);
        setTimeActive(TIME_ACTIVE);
    }

    @Override
    public void activate(double gameTimer, Spaceship spaceship) {
        spaceship.setBombPowerUp(true);
    }

    @Override
    public void deactivate(double gameTimer, Spaceship spaceship) {
        spaceship.setBombPowerUp(false);
    }

    @Override
    public void reapplyPowerUp(double gameTimer, Spaceship spaceship) {
    }
}
