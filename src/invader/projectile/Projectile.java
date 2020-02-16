package invader.projectile;

import invader.MovingObject;

public abstract class Projectile extends MovingObject {

    public static final double DEFAULT_X_SPEED = 0;
    public static final double DEFAULT_Y_SPEED = 200;
    public static final double DEFAULT_WIDTH = 10;
    public static final double DEFAULT_HEIGHT = 20;
    public static final String ENEMY_LASER_IMG_NAME = "enemylaser.png";
    public static final String SPACESHIP_LASER_IMG_NAME = "spaceshiplaser.png";

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
        setRotate(rotation);
        String idString = isEvil ? "evil" : "spaceship";
        this.setId(idString + projectileType + "Projectile" + idNumber);
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