package invader.projectile;

import invader.MovingObject;

public class Laser extends MovingObject {
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 200;
    public static final double WIDTH = 10;
    public static final double HEIGHT = 20;
    public static final String ENEMY_LASER_IMG_NAME = "enemylaser.png";
    public static final String SPACESHIP_LASER_IMG_NAME = "spaceshiplaser.png";

    private final int DAMAGE = 1;
    boolean isEnemy;

    public Laser(double xPos, double yPos, boolean isEnemy, int idNumber) {
        super(xPos,yPos,X_SPEED,Y_SPEED * (isEnemy ? -1 : 1), WIDTH, HEIGHT, isEnemy ? ENEMY_LASER_IMG_NAME : SPACESHIP_LASER_IMG_NAME);
        this.isEnemy = isEnemy;
        String idString = isEnemy ? "enemy" : "spaceship";
        this.setId(idString + "Laser" + idNumber);
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
