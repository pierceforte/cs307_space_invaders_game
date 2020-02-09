package invader.powerup;

import invader.entity.Spaceship;

public class SpaceshipSpeedPowerUp extends PowerUp {
    public static String IMG_NAME = "fastpower.gif";
    public static int INCREASED_SPEED = 25;
    public static int TIME_ACTIVE = 8;

    public SpaceshipSpeedPowerUp(double xPos, double yPos) {
        super(xPos, yPos, IMG_NAME);
        setTimeActive(TIME_ACTIVE);
    }

    @Override
    public void activate(double gameTimer, Spaceship spaceship) {
        spaceship.setXSpeedOnKeyPress(INCREASED_SPEED);
        setTimeWhenActivated(gameTimer);
    }


}
