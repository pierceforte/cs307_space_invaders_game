package invader.entity;

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
    public static final int TIME_BETWEEN_SHOTS = 50;
    public static final int START_FIRING_TIME = 5;
    public static final int POINTS_PER_HIT = 50;
    public static final int TIME_VULNERABLE = 3;
    public static final int TIME_INVULNERABLE = 8;

    private boolean isVulnerable = false;
    private int switchVulnerabilityTime = TIME_INVULNERABLE;

    public Boss(double xPos, double yPos, double xSpeed, double ySpeed, int lives) {
        super(xPos, yPos, xSpeed, ySpeed, HIDDEN_WIDTH, HIDDEN_HEIGHT, BOSS_HIDING_IMG_NAME);
        setLives(lives);
        setPointsPerHit(POINTS_PER_HIT);
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

    private void setVulnerable() {
        isVulnerable = true;
        switchBossImage(BOSS_IMG_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        addToSwitchVulnerabilityTime(TIME_VULNERABLE);
    }

    private void setInvulnerable() {
        isVulnerable = false;
        switchBossImage(BOSS_HIDING_IMG_NAME, HIDDEN_WIDTH, HIDDEN_HEIGHT);
        addToSwitchVulnerabilityTime(TIME_INVULNERABLE);
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
