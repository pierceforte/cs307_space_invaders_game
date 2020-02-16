package invader.entity;

import invader.projectile.Fireball;
import invader.projectile.Laser;
import invader.projectile.Projectile;

import java.util.concurrent.ThreadLocalRandom;

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
    public static final int DEFAULT_TIME_BETWEEN_SHOTS = 50;
    public static final int START_FIRING_TIME = 5;
    public static final int POINTS_PER_HIT = 50;
    public static final int POINTS_PER_HIT_WHEN_INVULNERABLE = 0;
    public static final int TIME_VULNERABLE = 3;
    public static final int TIME_INVULNERABLE = 8;

    private boolean hasFireballBlast = false;
    private boolean isVulnerable = false;
    private int switchVulnerabilityTime = TIME_INVULNERABLE;

    /**
     * Constructor
     * @param xPos
     * @param yPos
     * @param xSpeed
     * @param ySpeed
     * @param lives
     */
    public Boss(double xPos, double yPos, double xSpeed, double ySpeed, int lives) {
        super(xPos, yPos, xSpeed, ySpeed, HIDDEN_WIDTH, HIDDEN_HEIGHT, DEFAULT_TIME_BETWEEN_SHOTS, BOSS_HIDING_IMG_NAME);
        setLives(lives);
        setPointsPerHit(POINTS_PER_HIT_WHEN_INVULNERABLE);
        this.setId("boss");
        addToStartShootingTime(START_FIRING_TIME);
    }

    public void switchVulnerabilityStatus() {
        if (isVulnerable) setInvulnerable();
        else setVulnerable();
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void addToSwitchVulnerabilityTime(int timeToAdd) {
        switchVulnerabilityTime += timeToAdd;
    }

    public int getSwitchVulnerabilityTime() {
        return switchVulnerabilityTime;
    }

    public void setRandomSpeed() {
        setRandomXSpeed();
        setRandomYSpeed();
    }

    public void setHasFireballBlast(boolean hasFireballBlast) {
        this.hasFireballBlast = hasFireballBlast;
    }

    public boolean hasFireballBlast() {
        return hasFireballBlast;
    }

    private void setVulnerable() {
        isVulnerable = true;
        switchBossImage(BOSS_IMG_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        addToSwitchVulnerabilityTime(TIME_VULNERABLE);
        setPointsPerHit(POINTS_PER_HIT);
    }

    private void setInvulnerable() {
        isVulnerable = false;
        switchBossImage(BOSS_HIDING_IMG_NAME, HIDDEN_WIDTH, HIDDEN_HEIGHT);
        addToSwitchVulnerabilityTime(TIME_INVULNERABLE);
        setPointsPerHit(POINTS_PER_HIT_WHEN_INVULNERABLE);
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

    @Override
    public Projectile createProjectile(double rotation, int idNumber) {
        if (hasFireballBlast) {
            return new Fireball(this.getX() + this.getFitWidth()/2,
                    this.getY(), true, rotation, idNumber++);
        }
        else {
            return normalEvilEntityLaserBlast(rotation, idNumber);
        }
    }

    @Override
    public void removeLives(int livesToRemove) {
        if (this.isVulnerable) this.setLives(this.getLives()-livesToRemove);
    }
}
