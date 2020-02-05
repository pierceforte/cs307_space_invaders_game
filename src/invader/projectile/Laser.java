package invader.projectile;

import invader.MovingObject;

public class Laser extends MovingObject {
    private final int DAMAGE = 1;
    boolean isEnemy;

    public Laser(boolean isEnemy) {
        this.isEnemy = isEnemy;
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}
