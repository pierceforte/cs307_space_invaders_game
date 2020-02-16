package invader.projectile;

public class Fireball extends Projectile {
    public static final double WIDTH = 20;
    public static final double HEIGHT = 1.5*WIDTH;
    public static final double Y_SPEED = 120;
    public static final String FIREBALL_IMG_NAME = "fireball.gif";

    private final int DAMAGE = 1;

    /**
     * Constructor
     * @param xPos
     * @param yPos
     * @param isEvil
     * @param rotation
     * @param idNumber
     */
    public Fireball(double xPos, double yPos, boolean isEvil, double rotation, int idNumber) {
        super(xPos, yPos, DEFAULT_X_SPEED, Y_SPEED, WIDTH, HEIGHT, isEvil, FIREBALL_IMG_NAME, rotation, idNumber);
        setDamage(DAMAGE);
    }

}
