package invader.powerup;

import invader.entity.Spaceship;

public class LaserBeamPowerup extends PowerUp {
    public static String IMG_NAME = "";

    public LaserBeamPowerup(double xPos, double yPos) {
        super(xPos, yPos, IMG_NAME);
    }

    @Override
    public void activate(Spaceship spaceship) {

    }
}
