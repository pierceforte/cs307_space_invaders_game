package invader.projectile;

import invader.MovingObject;

/**
 * This is an abstract class used by its subclasses to create Projectiles that can collide with Entities in the game and do damage.
 *
 * This class is used for the Fireball, Laser, and Missile classes.
 *
 * The class is abstract so that it cannot be instantiated.
 *
 * All subclasses inherits this class have the common methods of setting and getting the damage of that specific projectile.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */

public abstract class Projectile extends MovingObject {
    public static final double DEFAULT_X_SPEED = 0;
    public static final double DEFAULT_Y_SPEED = 200;
    public static final double DEFAULT_WIDTH = 10;
    public static final double DEFAULT_HEIGHT = 20;
    public static final int LEFT_PROJECTILE_ROTATION = 45;
    public static final int LEFT_PROJECTILE_X_SPEED = -50;
    public static final int RIGHT_PROJECTILE_ROTATION = -45;
    public static final int RIGHT_PROJECTILE_X_SPEED = 50;
    public static final int DEFAULT_PROJECTILE_ROTATION = 0;
    public static final String EVIL_IDENTIFIER = "evil";
    public static final String NOT_EVIL_IDENTIFIER = "spaceship";
    public static final String IDENTIFIER = "Projectile";

    private int damage;
    private boolean isEvil;

    /**
     * Constructor
     * @param xPos: x position of the projectile
     * @param yPos: y position of the projectile
     * @param xSpeed: x speed of the projectile
     * @param ySpeed: y speed of the projectile
     * @param width: width of the projectile's image
     * @param height: height of the projectile's image
     * @param isEvil: boolean whether the projectile is the enemy's or the spaceship's
     * @param imgName: name of the image file
     * @param rotation: Rotation of the image
     * @param idNumber: id number of the projectile for testing
     * @param projectileType: implementation of projectile, used in id name for testing
     */
    public Projectile(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height,
                 boolean isEvil, String imgName, double rotation, int idNumber, String projectileType) {
        super(xPos,yPos,xSpeed,ySpeed * (isEvil ? -1 : 1), width, height, imgName);
        this.isEvil = isEvil;
        setRotate(rotation * (isEvil ? 1 : -1));
        String idString = isEvil ? EVIL_IDENTIFIER : NOT_EVIL_IDENTIFIER;
        this.setId(idString + projectileType + IDENTIFIER + idNumber);
    }

    /**
     * Sets the damage of the projectile
     * @param damage: damage of the projectile 
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * @return Returns the damage the projectile does to an entity
     */
    public int getDamage() {
        return damage;
    }
}