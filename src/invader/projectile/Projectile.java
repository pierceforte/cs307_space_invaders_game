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

    public Projectile(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height,
                 boolean isEvil, String imgName, double rotation, int idNumber) {
        super(xPos,yPos,xSpeed,ySpeed * (isEvil ? -1 : 1), width, height, imgName);
        this.isEvil = isEvil;
        setRotate(rotation);
        String idString = isEvil ? "enemy" : "spaceship";
        this.setId(idString + "Projectile" + idNumber);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isEvil() {
        return isEvil;
    }
}