package invader.entity;

import invader.Game;
import javafx.scene.image.Image;

public class Spaceship extends Entity {
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final int SPACESHIP_SPEED = 10;
    public static final String SPACESHIP_IMG_NAME = "spaceship.png";

    public Spaceship(double xPos, double yPos) {
        super(xPos, yPos, 0, 0, 30, 30, SPACESHIP_IMG_NAME);
        setLives(3);
        this.setId("spaceship");
    }

    public void wrap() {
        if (this.getX() > Game.GAME_WIDTH - this.getFitWidth()) {
            this.setX(0);
        }
        else if (this.getX() < 0) {
            this.setX(Game.GAME_WIDTH - this.getFitWidth());
        }
    }




}
