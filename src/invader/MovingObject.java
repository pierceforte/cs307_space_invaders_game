package invader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class MovingObject extends ImageView {
    private double xSpeed;
    private double ySpeed;
    private Image image;

//    public MovingObject(){}

    public MovingObject(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height, String imgName) {
        this.setX(xPos);
        this.setY(yPos);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.image = new Image(this.getClass().getClassLoader().getResource(imgName).toExternalForm());
        this.setImage(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void updatePositionOnStep(double elapsedTime) {
        this.setX(this.getX() + this.getXSpeed() * elapsedTime);
        this.setY(this.getY() - this.getYSpeed() * elapsedTime);
    }

    public boolean isOutOfXBounds() {
        return (this.getX() >= Game.GAME_WIDTH - this.getFitWidth()|| this.getX() <= 0);
    }

    public boolean isOutOfYBounds() {
        return (this.getY() >= Game.GAME_HEIGHT - 20 || this.getY() <= 20);
    }

    public void reverseXDirection() {
        this.xSpeed *= -1;
    }

    public void reverseYDirection() {
        this.ySpeed *= -1;
    }
}
