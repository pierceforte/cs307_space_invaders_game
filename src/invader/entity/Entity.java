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
    private int startingShootTime;

    private int lives;

    public Entity(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height, String imgName) {
        super(xPos, yPos, xSpeed, ySpeed, width, height, imgName);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getStartShootingTime() {
        return startingShootTime;
    }

    public void addToStartShootingTime(int timeToAdd) {
        startingShootTime += timeToAdd;
    }
}
