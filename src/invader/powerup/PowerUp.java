package invader.powerup;

import invader.MovingObject;
import invader.entity.Spaceship;

public abstract class PowerUp extends MovingObject {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final int X_SPEED = 0;
    public static final int Y_SPEED = -100;

    private boolean hasBeenActivated = false;
    private boolean isActive = false;
    private double timeWhenActivated;
    private double timeActive;

    public PowerUp (double xPos, double yPos, String imgName, String id) {
        super(xPos, yPos, X_SPEED, Y_SPEED, WIDTH, HEIGHT, imgName);
        this.setId(id);
    }

    public void setInactive() {
        this.isActive = false;
    }

    public void setTimeActive(double timeActive) {
        isActive = true;
        this.timeActive = timeActive;
    }

    public void setTimeWhenActivated(double time) {
        timeWhenActivated = time;
        hasBeenActivated = true;
    }

    public double getTimeWhenActivated() {
        return timeWhenActivated;
    }

    public boolean hasBeenActivated() {
        return hasBeenActivated;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean updateAndGetActiveStatus(double gameTimer, Spaceship spaceship) {
        if (gameTimer - timeWhenActivated >= timeActive) {
            this.setInactive();
        }
        else reapplyPowerUp(gameTimer, spaceship);
        return isActive;
    }

    public abstract void activate(double gameTimer, Spaceship spaceship);
    public abstract void deactivate(double gameTimer, Spaceship spaceship);
    public abstract void reapplyPowerUp(double gameTimer, Spaceship spaceship);
}
