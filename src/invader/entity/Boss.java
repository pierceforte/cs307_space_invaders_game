package invader.entity;

import java.util.concurrent.ThreadLocalRandom;

public class Boss extends Entity {

    public static final double DEFAULT_WIDTH = 200;
    public static final double DEFAULT_HEIGHT = 200;
    public static final double HIDDEN_WIDTH = 200;
    public static final double HIDDEN_HEIGHT = (0.75)*200;
    public static final int MIN_SPEED = 40;
    public static final int MAX_SPEED = 70;
    public static final int DEFAULT_SPEED = 50;
    public static final String BOSS_IMG_NAME = "boss.gif";
    public static final String BOSS_HIDING_IMG_NAME = "boss_hiding.png";
    public static final int TIME_BETWEEN_SHOTS = 50;
    public static final int EARLIEST_START_FIRING_TIME = 1;
    public static final int LATEST_START_FIRING_TIME = 40;
    public static final int POINTS_PER_HIT = 50;

    private boolean isVulnerable = false;

    public Boss(double xPos, double yPos, double xSpeed, double ySpeed, int lives) {
        super(xPos, yPos, xSpeed, ySpeed, DEFAULT_WIDTH, DEFAULT_HEIGHT, BOSS_IMG_NAME);
        setInvulnerable();
        setLives(lives);
        setPointsPerHit(POINTS_PER_HIT);
        this.setId("boss");
        addToStartShootingTime(ThreadLocalRandom.current().nextInt(EARLIEST_START_FIRING_TIME, LATEST_START_FIRING_TIME));
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void setVulnerable() {
        isVulnerable = true;
        switchBossImage(BOSS_IMG_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void setInvulnerable() {
        isVulnerable = false;
        switchBossImage(BOSS_HIDING_IMG_NAME, HIDDEN_WIDTH, HIDDEN_HEIGHT);
    }

    public void setRandomXSpeed() {
        setXSpeed(getRandomSpeed(getXSpeed()));
    }

    public void setRandomYSpeed() {
        setYSpeed(getRandomSpeed(getYSpeed()));
    }

    private void switchBossImage(String imgName, double width, double height) {
        setImage(makeImage(imgName));
        setFitWidth(width);
        setFitHeight(height);
    }

    private int getRandomSpeed(double curSpeed) {
        int newSpeed = ThreadLocalRandom.current().nextInt(Boss.MIN_SPEED, Boss.MAX_SPEED);
        newSpeed = curSpeed < 0 ? newSpeed * -1 : newSpeed;
        return newSpeed;
    }

}
