package invader.powerup;

import invader.MovingObject;
import invader.entity.Spaceship;

public abstract class PowerUp extends MovingObject {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final int X_SPEED = 0;
    public static final int Y_SPEED = -100;

    private boolean isActive = false;
    private double timeWhenActivated;
    private double timeActive;

    public PowerUp (double xPos, double yPos, String imgName) {
        super(xPos, yPos, X_SPEED, Y_SPEED, WIDTH, HEIGHT, imgName);
    }

    public void setActive() {
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setTimeActive(double timeActive) {
        this.timeActive = timeActive;
    }

    public void setTimeWhenActivated(double time) {
        timeWhenActivated = time;
    }

    public double getTimeWhenActivated() {
        return timeWhenActivated;
    }

    public abstract void activate(double gameTimer, Spaceship spaceship);
}
