package invader.projectile;

import invader.MovingObject;

public class Laser extends Projectile {
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 200;
    public static final String ENEMY_LASER_IMG_NAME = "enemylaser.png";
    public static final String SPACESHIP_LASER_IMG_NAME = "spaceshiplaser.png";
    private static final int DAMAGE = 1;

    /**
     * Constructor
     * @param xPos
     * @param yPos
     * @param isEvil
     * @param rotation
     * @param idNumber
     */
    public Laser(double xPos, double yPos, boolean isEvil, double rotation, int idNumber) {
        super(xPos,yPos, X_SPEED,Y_SPEED, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                isEvil, isEvil ? ENEMY_LASER_IMG_NAME : SPACESHIP_LASER_IMG_NAME, rotation, idNumber);

        setDamage(DAMAGE);
    }
}
