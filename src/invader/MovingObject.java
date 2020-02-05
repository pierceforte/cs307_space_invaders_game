package invader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class MovingObject extends ImageView {
    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;
    private Image image;

//    public MovingObject(){}

    public MovingObject(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height, String imgName) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.image = new Image(this.getClass().getClassLoader().getResource(imgName).toExternalForm());
        this.setImage(image);
        this.setFitWidth(width);
        this.setFitWidth(height);
    }


    public double getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
}
