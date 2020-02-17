package invader.projectile;

import invader.MovingObject;

/**
 * Extends
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
     * @param xPos
     * @param yPos
     * @param xSpeed
     * @param ySpeed
     * @param width
     * @param height
     * @param isEvil
     * @param imgName
     * @param rotation
     * @param idNumber
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
     * @param damage
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

    /**
     * @return Check whether the missile belongs to an enemy or user
     */
    public boolean isEvil() {
        return isEvil;
    }
}