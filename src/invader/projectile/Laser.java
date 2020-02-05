package invader.projectile;

import invader.MovingObject;
import invader.entity.Enemy;

public class Laser extends MovingObject {
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 150;
    public static final double WIDTH = 10;
    public static final double HEIGHT = 20;
    public static final String IMG_NAME = "enemylaser.png";

    private final int DAMAGE = 1;
    boolean isEnemy;

    public Laser(double xPos, double yPos, boolean isEnemy) {
        super(xPos,yPos,X_SPEED,Y_SPEED * (isEnemy ? -1 : 1), WIDTH, HEIGHT, IMG_NAME);
        this.isEnemy = isEnemy;

    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
