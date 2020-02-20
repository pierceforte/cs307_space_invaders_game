package invader.projectile;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Specific Laser class which is used in the missile for both the regular enemy and spaceship
 */

public class Laser extends Projectile {
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 200;
    public static final String ENEMY_LASER_IMG_NAME = "enemylaser.png";
    public static final String SPACESHIP_LASER_IMG_NAME = "spaceshiplaser.png";
    public static final String TYPE = "Laser";
    public static final int DAMAGE = 1;

    /**
     * Create a laser
     * @param xPos: x position of the laser
     * @param yPos: y position of the laser
     * @param isEvil: boolean whether the laser is enemy's or spaceship's
     * @param rotation: rotation of the image file
     * @param idNumber: id used for testing
     */
    public Laser(double xPos, double yPos, boolean isEvil, double rotation, int idNumber) {
        super(xPos,yPos, X_SPEED,Y_SPEED, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                isEvil, isEvil ? ENEMY_LASER_IMG_NAME : SPACESHIP_LASER_IMG_NAME, rotation, idNumber, TYPE);

        setDamage(DAMAGE);
    }
}
