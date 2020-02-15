package invader.powerup;

import invader.entity.Spaceship;

public class LaserBeamPowerup extends PowerUp {
    public static final String IMG_NAME = "extraballpower.gif";

    public LaserBeamPowerup(double xPos, double yPos, String id) {
        super(xPos, yPos, IMG_NAME, id);
    }

    @Override
    public void activate(double gameTimer, Spaceship spaceship) {
    }

    @Override
    public void deactivate(double gameTimer, Spaceship spaceship) {
    }

    @Override
    public void reapplyPowerUp(double gameTimer, Spaceship spaceship) {
    }
}
