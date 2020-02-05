package invader.entity;

public class Spaceship extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;

    public Spaceship(double xPos, double yPos) {
        super(xPos, yPos, 0, 0, Enemy.ENEMY_IMG_NAME);
        setLives(3);
    }




}
