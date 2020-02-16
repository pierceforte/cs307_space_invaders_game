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

    /**
     * Constructor
     * @param xPos
     * @param yPos
     * @param imgName
     * @param id
     */
    public PowerUp (double xPos, double yPos, String imgName, String id) {
        super(xPos, yPos, X_SPEED, Y_SPEED, WIDTH, HEIGHT, imgName);
        this.setId(id);
    }

    /**
     * Sets the power up status to inactive
     */
    public void setInactive() {
        this.isActive = false;
    }

    /**
     * Sets the time to active
     * @param timeActive
     */
    public void setTimeActive(double timeActive) {
        isActive = true;
        this.timeActive = timeActive;
    }

    /**
     * Sets the time of the activated time
     * @param time
     */
    public void setTimeWhenActivated(double time) {
        timeWhenActivated = time;
        hasBeenActivated = true;
    }

    /**
     * Get the activated time
     * @return
     */
    public double getTimeWhenActivated() {
        return timeWhenActivated;
    }

    /**
     * Check if the power up is activated
     * @return Returns whether the powerup is activated
     */
    public boolean hasBeenActivated() {
        return hasBeenActivated;
    }

    /**
     * Check if the powerup is active
     * @return Returns boolean whether the powerup is active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * If the activated time is past the active time limit, set it to inactive
     * @param gameTimer
     * @param spaceship
     * @return Returns boolean whether the powerup is active or not
     */
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
