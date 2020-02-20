package invader.projectile;

/**
 * This class inherits the abstract Projectile class, implementing a more powerful projectile that does two damage.
 *
 * Used by the Spaceship when the MissilePowerUp is active.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */

public class Missile extends Projectile{// extends Laser {
    public static final double WIDTH = 10;
    public static final double HEIGHT = 20;
    public static final String BOMB_IMG_NAME = "missile.png";
    public static final String TYPE = "Missile";
    public static final int DAMAGE = 3;

    /**
     * Create a missile projectile
     * @param xPos: x position of the laser
     * @param yPos: y position of the laser
     * @param isEvil: boolean whether the laser is enemy's or spaceship's
     * @param rotation: rotation of the image file
     * @param idNumber: id used for testing
     */
    public Missile(double xPos, double yPos, boolean isEvil, double rotation, int idNumber) {
        super(xPos, yPos, DEFAULT_X_SPEED, DEFAULT_Y_SPEED, WIDTH, HEIGHT, isEvil, BOMB_IMG_NAME, rotation, idNumber, TYPE);
        setDamage(DAMAGE);
    }
}
