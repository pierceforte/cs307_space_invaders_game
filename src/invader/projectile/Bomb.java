package invader.projectile;

import invader.MovingObject;
import invader.entity.Enemy;
import javafx.scene.image.Image;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Bomb extends Laser {
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 200;
    public static final double WIDTH = 20;
    public static final double HEIGHT = 10;
    public static final String ENEMY_LASER_IMG_NAME = "bomb.png";

    private final int DAMAGE = 3;
    boolean isEnemy;

    public Bomb(double xPos, double yPos, int idNumber) {
        super(xPos,yPos,false,idNumber);
        String idString = "spaceship";
        this.setId(idString + "Laser" + idNumber);
        Image bombImg = makeImage(ENEMY_LASER_IMG_NAME);
        this.setImage(bombImg);
    }

    public int getDamage() {
        return this.DAMAGE;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
