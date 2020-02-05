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

    public Entity(double xPos, double yPos, double xSpeed, double ySpeed, String imgName) {
        super(xPos, yPos, 0, 0, imgName);
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getStartingShootTime() {
        return startingShootTime;
    }
}
