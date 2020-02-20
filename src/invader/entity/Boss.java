package invader.entity;

import invader.projectile.Fireball;
import invader.projectile.Projectile;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class inherits the abstract entity class, which is used to create a boss object.
 *
 * It has multiple features that only the boss possesses, such as vulnerability and missile type
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */
public class Boss extends Entity {

    public static final double DEFAULT_WIDTH = 150;
    public static final double DEFAULT_HEIGHT = 150;
    public static final double HIDDEN_WIDTH = 150;
    public static final double HIDDEN_HEIGHT = (0.75)*150;
    public static final int MIN_SPEED = 40;
    public static final int MAX_SPEED = 70;
    public static final int DEFAULT_SPEED = 50;
    public static final String BOSS_IMG_NAME = "boss.gif";
    public static final String BOSS_HIDING_IMG_NAME = "boss_hiding.png";
    public static final String IDENTIFIER = "boss";
    public static final int DEFAULT_TIME_BETWEEN_SHOTS = 50;
    public static final int START_FIRING_TIME = 5;
    public static final int POINTS_PER_HIT = 50;
    public static final int POINTS_PER_HIT_WHEN_INVULNERABLE = 0;
    public static final int TIME_VULNERABLE = 3;
    public static final int TIME_INVULNERABLE = 8;
    public static final boolean IS_EVIL = true;

    private boolean isVulnerable = false;
    private int switchVulnerabilityTime = TIME_INVULNERABLE;

    /**
     * Constructor
     * @param xPos: x position of the boss
     * @param yPos: y position of the boss
     * @param xSpeed: x speed of the boss
     * @param ySpeed: y speed of the boss
     * @param lives: how many lives the boss has
     */
    public Boss(double xPos, double yPos, double xSpeed, double ySpeed, int lives) {
        super(xPos, yPos, xSpeed, ySpeed, HIDDEN_WIDTH, HIDDEN_HEIGHT, DEFAULT_TIME_BETWEEN_SHOTS, IS_EVIL, BOSS_HIDING_IMG_NAME);
        setLives(lives);
        setPointsPerHit(POINTS_PER_HIT_WHEN_INVULNERABLE);
        this.setId(IDENTIFIER);
        addToStartShootingTime(START_FIRING_TIME);
    }

    /**
     * Change whether the boss is vulnerable or not; ie. whether it can be damaged by the player
     */
    public void switchVulnerabilityStatus() {
        if (isVulnerable) setVulnerable(false);
        else setVulnerable(true);
    }

    /**
     * Get if the boss is currently vulnerable
     * @return boolean regarding boss's vulnerability state
     */
    public boolean isVulnerable() {
        return isVulnerable;
    }

    /**
     * Add time for the boss' vulnerability time
     * @param timeToAdd how much time to add to the value that keeps track of
     * when the vulnerability of the boss should be switched next
     */
    public void addToSwitchVulnerabilityTime(int timeToAdd) {
        switchVulnerabilityTime += timeToAdd;
    }

    /**
     * Get the switch vulnerability time
     * @return the time at which the boss's vulnerability status should changed, based on the game timer
     */
    public int getSwitchVulnerabilityTime() {
        return switchVulnerabilityTime;
    }

    /**
     * Randomly assign an x speed and y speed for the boss
     */
    public void setRandomSpeed() {
        setRandomXSpeed();
        setRandomYSpeed();
    }

    /**
     * Set the vulnerability status of the boss
     * @param isVulnerable if true (boss is currently vulnerable), make the boss invulnerable;
     * if false (currently invulnerable), make the boss vulnerable
     */
    private void setVulnerable(boolean isVulnerable) {
        this.isVulnerable = isVulnerable;
        if (isVulnerable) {
            switchBossImage(BOSS_IMG_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            addToSwitchVulnerabilityTime(TIME_VULNERABLE);
            setPointsPerHit(POINTS_PER_HIT);
        } else {
            switchBossImage(BOSS_HIDING_IMG_NAME, HIDDEN_WIDTH, HIDDEN_HEIGHT);
            addToSwitchVulnerabilityTime(TIME_INVULNERABLE);
            setPointsPerHit(POINTS_PER_HIT_WHEN_INVULNERABLE);
        }
    }

    /**
     * Create a projectile fired from the boss
     * @param rotation rotation of the projectile's image
     * @param idNumber id number of the projectile for testing
     * @return the projectile that is fired
     */
    @Override
    public Projectile createProjectile(double rotation, int idNumber) {
        if (hasBurstFire()) {
            return new Fireball(this.getX() + this.getFitWidth()/2,
                    this.getY(), true, rotation, idNumber);
        }
        else {
            return defaultProjectileFire(rotation, idNumber);
        }
    }

    @Override
    public void removeLives(int livesToRemove) {
        if (this.isVulnerable) this.setLives(this.getLives()-livesToRemove);
    }

    private void switchBossImage(String imgName, double width, double height) {
        setImage(makeImage(imgName));
        setFitWidth(width);
        setFitHeight(height);
    }

    private void setRandomXSpeed() {
        setXSpeed(createRandomSpeed(getXSpeed()));
    }

    private void setRandomYSpeed() {
        setYSpeed(createRandomSpeed(getYSpeed()));
    }

    private int createRandomSpeed(double curSpeed) {
        int newSpeed = ThreadLocalRandom.current().nextInt(Boss.MIN_SPEED, Boss.MAX_SPEED);
        newSpeed = curSpeed < 0 ? newSpeed * -1 : newSpeed;
        return newSpeed;
    }
}
