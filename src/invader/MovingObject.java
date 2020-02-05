package invader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class MovingObject extends ImageView {
    private int xPos;
    private int yPos;
    private int xSpeed;
    private int ySpeed;
    private Image image;

    public MovingObject(int xPos, int yPos, int xSpeed, int ySpeed, Image image) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.image = image;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }




}
