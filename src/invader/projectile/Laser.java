package invader.projectile;

import invader.MovingObject;

public class Laser extends MovingObject {
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 200;
    public static final double WIDTH = 10;
    public static final double HEIGHT = 20;
    public static final String ENEMY_LASER_IMG_NAME = "enemylaser.png";
    public static final String SPACESHIP_LASER_IMG_NAME = "spaceshiplaser.png";
    private static final int DAMAGE = 1;

    private boolean isEvil;

    public Laser(double xPos, double yPos, boolean isEvil, double rotation, int idNumber) {
        super(xPos,yPos,X_SPEED,Y_SPEED * (isEvil ? -1 : 1), WIDTH, HEIGHT, isEvil ? ENEMY_LASER_IMG_NAME : SPACESHIP_LASER_IMG_NAME);
        this.isEvil = isEvil;
        setRotate(rotation);
        String idString = isEvil ? "enemy" : "spaceship";
        this.setId(idString + "Laser" + idNumber);
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isEvil() {
        return isEvil;
    }
}
