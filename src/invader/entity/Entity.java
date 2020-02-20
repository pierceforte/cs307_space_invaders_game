package invader.entity;

import invader.Game;
import invader.MovingObject;
import invader.projectile.Laser;
import invader.projectile.Projectile;

/**
 * This is an abstract class that is used to create all types of entities used in this game.
 *
 * Spaceship, boss, enemy, all belongs to this abstract class
 *
 * This class is necessary and efficient because there are many features that the boss, enemy, and spaceship share.
 *
 * The projectile creation is an abstract method because different entities have different rules for creating projectiles.
 *
 * This class has methods like getting lives, setting lives, getting points, seeking out of bounds, etc
 *
 * @author Pierce Forte
 * @author Jeff Kim
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
     * @param timeBetweenShots: the time that this entity must wait between firing shots
     * @param isEvil: boolean whether the entity is an evil
     * @param imgName: name of the image file
     */
    public Entity(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height,
                  double timeBetweenShots, boolean isEvil, String imgName) {
        super(xPos, yPos, xSpeed, ySpeed, width, height, imgName);
        this.isEvil = isEvil;
        this.timeBetweenShots = timeBetweenShots;
    }

    /**
     * Set the life of the current entity
     * @param lives how many lives this entity should have
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
     * Lower lives of the current entity
     * @param livesToRemove how many lives to remove from this entity
     */
    public void removeLives(int livesToRemove) {
        lives -= livesToRemove;
    }

    /**
     * @return Life count of the current entity
     */
    public int getLives() { return this.lives; }

    /**
     * Set how many points are awarded when this entity is hit
     * @param pointsPerHit how many points are awarded when this entity is hit
     */
    public void setPointsPerHit(int pointsPerHit) {
        this.pointsPerHit = pointsPerHit;
    }

    /** Get points per hit the user receives
     * @return Return the points per hit the user receives
     */
    public int getPointsPerHit() {
        return pointsPerHit;
    }

    /** Get the time when the entity can start shooting, based on the game timer
     * @return the time when the entity can start shooting, based on the game timer
     */
    public double getStartShootingTime() {
        return startingShootTime;
    }

    /**
     * Add to the starting shoot time of the entity to create random fires
     * @param timeToAdd the time added to the starting shoot time of the entity to create random fires
     */
    public void addToStartShootingTime(double timeToAdd) {
        startingShootTime += timeToAdd;
    }

    /**
     * Set the time when the entity can start shooting, based on the game timer
     * @param startingShootTime the time when the entity can start shooting, based on the game timer
     */
    public void setStartShootingTime(double startingShootTime) {
        this.startingShootTime = startingShootTime;
    }

    /**
     * Set how long the entity must wait between firing projectiles
     * @param timeBetweenShots how long the entity must wait between firing projectiles
     */
    public void setTimeBetweenShots(double timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    /**
     * Get how long the entity must wait between firing projectiles
     * @return how long the entity must wait between firing projectiles
     */
    public double getTimeBetweenShots() {
        return timeBetweenShots;
    }

    /**
     * Get this enemy's current projectile id number used for testing
     * @return enemy's current projectile id number used for testing
     */
    public int getCurProjectileIdNumber() {
        return curProjectileIdNumber;
    }

    /**
     * Increment this enemy's current projectile id number used for testing
     */
    public void incrementCurProjectileIdNumber() {
        curProjectileIdNumber++;
    }

    /**
     * Set whether this entity has the burst fire ability
     * @param hasBurstFire whether this entity has the burst fire ability
     */
    public void setHasBurstFire(boolean hasBurstFire) {
        this.hasBurstFire = hasBurstFire;
    }

    /**
     * Get whether this entity has the burst fire ability
     * @return whether this entity has the burst fire ability
     */
    public boolean hasBurstFire() {
        return hasBurstFire;
    }

    /**
     * Check if the entity is out of bounds in the y direction
     * @return Boolean whether it is out of bounds or not
     */
    @Override
    public boolean isOutOfYBounds() {
        return (this.getY() >= Game.GAME_HEIGHT - BOTTOM_OUT_OF_BOUNDS_LOCATION || this.getY() <= TOP_OUT_OF_BOUNDS_LOCATION);
    }

    /**
     * Create a projectile respective to the type of entity, which will be implemented in the subclasses
     * The projectile is an abstract method because different entities have different projectile types
     * @param rotation rotation of the projectile's image
     * @param idNumber id number of the projectile for testing
     * @return the projectile that is fired
     */
    public abstract Projectile createProjectile(double rotation, int idNumber);

    protected Projectile defaultProjectileFire(double rotation, int idNumber){
        Laser laser = new Laser(this.getX() + this.getFitWidth()/2,
                this.getY(), isEvil, rotation, idNumber);
        return laser;
    }


}
