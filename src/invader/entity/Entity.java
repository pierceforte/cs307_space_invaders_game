package invader.entity;

import invader.Game;
import invader.MovingObject;
import invader.projectile.Laser;
import invader.projectile.Projectile;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Abstract class that is used to create all types of entities used in this game.
 * Spaceship, boss, enemy, all belongs to this abstract class
 * This class is necessary and efficient because there are many features that the boss, enemy, and spaceship shares
 * The projectile is an abstract method because different entities have different missile types.
 * It has methods like getting lives, setting lives, getting points, seeking out of bounds, etc
 */

public abstract class Entity extends MovingObject {
    public static final int NON_BOSS_WIDTH = 30;
    public static final int TOP_OUT_OF_BOUNDS_LOCATION = 150;
    public static final int BOTTOM_OUT_OF_BOUNDS_LOCATION = 250;
    private double startingShootTime;

    private int pointsPerHit;
    private int lives;
    private double timeBetweenShots;
    private int curProjectileIdNumber;
    private boolean hasBurstFire = false;
    private boolean isEvil;

    /**
     * Constructor
     * @param xPos: x position of the entity
     * @param yPos: y position of the entity
     * @param xSpeed: x speed of the entity
     * @param ySpeed: y speed of the entity
     * @param width: width of the image file
     * @param height: height of the image file
     * @param imgName: name of the image file
     * @param isEvil: boolean whether the entity is an evil
     */
    public Entity(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height,
                  double timeBetweenShots, boolean isEvil, String imgName) {
        super(xPos, yPos, xSpeed, ySpeed, width, height, imgName);
        this.isEvil = isEvil;
        this.timeBetweenShots = timeBetweenShots;
    }

    /**
     * Set the life of the current entity
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Add a life to the current entity
     */
    public void addLife() { this.lives++; }

    /**
     * Lower life of the current entity
     */
    public void lowerLives() { this.lives--; }

    /**
     * Lower multiple lives of the current entity
     * @param livesToRemove
     */
    public void removeLives(int livesToRemove) {
        lives -= livesToRemove;
    }

    /**
     * @return Life count of the current entity
     */
    public int getLives() { return this.lives; }

    /**
     * Set the amount of points the user gets when hitting an enemy
     * @param pointsPerHit
     */
    public void setPointsPerHit(int pointsPerHit) {
        this.pointsPerHit = pointsPerHit;
    }

    /**
     * @return Return the points per hit the user receives
     */
    public int getPointsPerHit() {
        return pointsPerHit;
    }

    /**
     * @return The starting shoot time of the entity
     */
    public double getStartShootingTime() {
        return startingShootTime;
    }

    /**
     * Increment the starting shoot time of the entity to create random fires
     * @param timeToAdd
     */
    public void addToStartShootingTime(double timeToAdd) {
        startingShootTime += timeToAdd;
    }

    /**
     * Set the starting shoot time of the entity
     * @param startingShootTime
     */
    public void setStartShootingTime(double startingShootTime) {
        this.startingShootTime = startingShootTime;
    }

    public void setTimeBetweenShots(double timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    public double getTimeBetweenShots() {
        return timeBetweenShots;
    }

    /**
     * CHeck if the entity is out of bounds in the y direction
     * @return Boolean whether it is out of bounds or not
     */
    @Override
    public boolean isOutOfYBounds() {
        return (this.getY() >= Game.GAME_HEIGHT - BOTTOM_OUT_OF_BOUNDS_LOCATION || this.getY() <= TOP_OUT_OF_BOUNDS_LOCATION);
    }

    public int getCurProjectileIdNumber() {
        return curProjectileIdNumber;
    }

    public void incrementCurProjectileIdNumber() {
        curProjectileIdNumber++;
    }

    public void setHasBurstFire(boolean hasBurstFire) {
        this.hasBurstFire = hasBurstFire;
    }

    public boolean hasBurstFire() {
        return hasBurstFire;
    }

    /**
     * Create a projectile respective to the type of entity, which will be implemented in the subclasses
     * The projectile is an abstract method because different entities have different missile types.
     * @param rotation
     * @param idNumber
     */
    public abstract Projectile createProjectile(double rotation, int idNumber);

    protected Projectile defaultProjectileFire(double rotation, int idNumber){
        Laser laser = new Laser(this.getX() + this.getFitWidth()/2,
                this.getY(), isEvil, rotation, idNumber);
        return laser;
    }


}
