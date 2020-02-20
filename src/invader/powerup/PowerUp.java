package invader.powerup;

import invader.MovingObject;
import invader.entity.Spaceship;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Abstract class that is used for BurstFire, Missile, and Speed powerups.
 * Has common methods like setting activation status
 */

public abstract class PowerUp extends MovingObject {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final int X_SPEED = 0;
    public static final int Y_SPEED = -100;
    public static final String ENEMY_POWERUP_IDENTIFIER = "enemyPowerUp";
    public static final String CHEAT_POWER_UP_IDENTIFIER = "cheatPowerUp";

    private boolean hasBeenActivated = false;
    private boolean isActive = false;
    private double timeWhenActivated;
    private double timeActive;

    /**
     * Constructor
     * @param xPos: x position of the power up
     * @param yPos: y position of the power up
     * @param imgName: name of the image file
     * @param id: id of the powerup used for testing
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
     * Sets the duration that power up is active for
     * @param timeActive The duration that power up is active for
     */
    public void setTimeActive(double timeActive) {
        isActive = true;
        this.timeActive = timeActive;
    }

    /**
     * Sets the time of when the power up was activated
     * @param time the time when the power up was activated
     */
    public void setTimeWhenActivated(double time) {
        timeWhenActivated = time;
        hasBeenActivated = true;
    }

    /**
     * Get the time when the power up was activated
     * @return the time when the power up was activated
     */
    public double getTimeWhenActivated() {
        return timeWhenActivated;
    }

    /**
     * Check if the power up has been activated
     * @return returns whether the powerup has been activated
     */
    public boolean hasBeenActivated() {
        return hasBeenActivated;
    }

    /**
     * Check if the powerup is currently active
     * @return returns boolean whether the powerup is active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * If the activated time is past the active time limit, set it to inactive
     * @param gameTimer the timer of the entire game
     * @param spaceship the spaceship that the user controls
     * @return Returns boolean whether the powerup is active or not
     */
    public boolean updateAndGetActiveStatus(double gameTimer, Spaceship spaceship) {
        if (gameTimer - timeWhenActivated >= timeActive) {
            this.setInactive();
        }
        else reapplyPowerUp(gameTimer, spaceship);
        return isActive;
    }

    /**
     * Initially activates the power up
     * @param gameTimer the timer of the entire game
     * @param spaceship the spaceship that the user controls
     */
    public abstract void activate(double gameTimer, Spaceship spaceship);

    /**
     * Deactivate the current power up the spaceship has
     * @param gameTimer the timer of the entire game
     * @param spaceship the spaceship that the user controls
     */
    public abstract void deactivate(double gameTimer, Spaceship spaceship);

    /**
     * Reactivate the power up of the spaceship
     * @param gameTimer the timer of the entire game
     * @param spaceship the spaceship that the user controls
     */
    public abstract void reapplyPowerUp(double gameTimer, Spaceship spaceship);
}
