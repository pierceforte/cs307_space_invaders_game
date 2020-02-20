package invader.projectile;

/**
 * This class inherits the abstract Projectile class, implementing more powerful projectiles with a fireball image.
 *
 * Used by the boss when it switches from invulnerable to vulnerable.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */

public class Fireball extends Projectile {
    public static final double WIDTH = 20;
    public static final double HEIGHT = 1.5*WIDTH;
    public static final double Y_SPEED = 200;
    public static final String FIREBALL_IMG_NAME = "fireball.gif";
    public static final String TYPE = "Fireball";
    public static final int DAMAGE = 2;

    /**
     * Create a fireball projectile
     * @param xPos: x position for the fireball
     * @param yPos: y position for the fireball
     * @param isEvil: boolean whether the fireball is the enemy's or the spaceship's
     * @param rotation: rotateion of the image
     * @param idNumber: id later used for testing 
     */
    public Fireball(double xPos, double yPos, boolean isEvil, double rotation, int idNumber) {
        super(xPos, yPos, DEFAULT_X_SPEED, Y_SPEED, WIDTH, HEIGHT, isEvil, FIREBALL_IMG_NAME, rotation, idNumber, TYPE);
        setDamage(DAMAGE);
    }

}
