package invader.projectile;

import invader.MovingObject;
import invader.entity.Enemy;

public class Laser extends MovingObject {
    private final int DAMAGE = 1;
    boolean isEnemy;

    public Laser(boolean isEnemy) {
        super(9,0,0,0, Enemy.ENEMY_IMG_NAME);
        this.isEnemy = isEnemy;
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
