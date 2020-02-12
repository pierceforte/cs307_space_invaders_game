package invader.entity;

import invader.Game;
import javafx.scene.image.Image;

public class Spaceship extends Entity {
    public static final int DEFAULT_X_SPEED = 0;
    public static final int DEFAULT_Y_SPEED = 0;
    public static final int HEIGHT = 30;
    public static final int WIDTH = 30;
    public static final int DEFAULT_X_POS = Game.GAME_WIDTH/2 - Spaceship.WIDTH/2;
    public static final int DEFAULT_Y_POS = Game.GAME_HEIGHT - 30;
    public static final int DEFAULT_X_SPEED_ON_KEY_PRESS = 10;
    public static final int DEFAULT_LIVES = 3;
    public static final String SPACESHIP_IMG_NAME = "spaceship.png";

    private double xSpeedOnKeyPress = DEFAULT_X_SPEED_ON_KEY_PRESS;

    public Spaceship(double xPos, double yPos) {
        super(xPos, yPos, DEFAULT_X_SPEED, DEFAULT_Y_SPEED, HEIGHT, WIDTH, SPACESHIP_IMG_NAME);
        setLives(DEFAULT_LIVES);
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

    public double getXSpeedOnKeyPress() {
        return xSpeedOnKeyPress;
    }

    public void setXSpeedOnKeyPress(double xSpeedOnKeyPress) {
        this.xSpeedOnKeyPress = xSpeedOnKeyPress;
    }




}
