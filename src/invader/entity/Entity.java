package invader.entity;

import invader.MovingObject;
import javafx.scene.image.Image;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Entity extends MovingObject {
    public static final int NON_BOSS_WIDTH = 30;
    private double startingShootTime;

    private int lives;

    public Entity(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height, String imgName) {
        super(xPos, yPos, xSpeed, ySpeed, width, height, imgName);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void lowerLives() { this.lives--; }

    public int getLives() { return this.lives; }

    public double getStartShootingTime() {
        return startingShootTime;
    }

    public void addToStartShootingTime(double timeToAdd) {
        startingShootTime += timeToAdd;
    }
}
