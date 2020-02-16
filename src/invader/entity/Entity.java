package invader.entity;

import invader.Game;
import invader.MovingObject;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public abstract class Entity extends MovingObject {
    public static final int NON_BOSS_WIDTH = 30;
    public static final int TOP_OUT_OF_BOUNDS_LOCATION = 150;
    public static final int BOTTOM_OUT_OF_BOUNDS_LOCATION = 250;
    private double startingShootTime;

    private int pointsPerHit;
    private int lives;

    public Entity(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height, String imgName) {
        super(xPos, yPos, xSpeed, ySpeed, width, height, imgName);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addLife() { this.lives++; }

    public void lowerLives() { this.lives--; }

    public void removeLives(int livesToRemove) {
        lives -= livesToRemove;
    }

    public int getLives() { return this.lives; }

    public void setPointsPerHit(int pointsPerHit) {
        this.pointsPerHit = pointsPerHit;
    }

    public int getPointsPerHit() {
        return pointsPerHit;
    }

    public double getStartShootingTime() {
        return startingShootTime;
    }

    public void addToStartShootingTime(double timeToAdd) {
        startingShootTime += timeToAdd;
    }

    public void setStartShootingTime(double startingShootTime) {
        this.startingShootTime = startingShootTime;
    }

    @Override
    public boolean isOutOfYBounds() {
        return (this.getY() >= Game.GAME_HEIGHT - BOTTOM_OUT_OF_BOUNDS_LOCATION || this.getY() <= TOP_OUT_OF_BOUNDS_LOCATION);
    }
}
