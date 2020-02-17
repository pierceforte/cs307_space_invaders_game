package invader;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public abstract class MovingObject extends ImageView {
    public static final int OUT_OF_BOUNDS_LOCATION = 20;

    private double xSpeed;
    private double ySpeed;
    private Image image;

    /**
     * Constructor for all moving objects
     * @param xPos
     * @param yPos
     * @param xSpeed
     * @param ySpeed
     * @param width
     * @param height
     * @param imgName
     */
    public MovingObject(double xPos, double yPos, double xSpeed, double ySpeed, double width, double height, String imgName) {
        this.setX(xPos);
        this.setY(yPos);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.image = makeImage(imgName);
        this.setImage(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    /**
     * Reads an image file from the resource folder
     * @param imgName
     * @return Image file that corresponds to the string
     */
    public Image makeImage (String imgName) {
        return new Image(this.getClass().getClassLoader().getResource(imgName).toExternalForm());
    }

    // Get x speed of the moving object
    public double getXSpeed() {
        return xSpeed;
    }

    // Set x speed of the moving object
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    // Get y speed of the moving object
    public double getYSpeed() {
        return ySpeed;
    }

    // Set y speed of the moving object
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Update the position of the moving object for each step
     * @param elapsedTime
     */
    public void updatePositionOnStep(double elapsedTime) {
        this.setX(this.getX() + this.getXSpeed() * elapsedTime);
        this.setY(this.getY() - this.getYSpeed() * elapsedTime);
    }

    /**
     * Check if the moving object is out of bounds in the x direction
     * @return Boolean value
     */
    public boolean isOutOfXBounds() {
        return (this.getX() >= Game.GAME_WIDTH - this.getFitWidth()|| this.getX() <= 0);
    }

    /**
     * Check if the moving object is out of bounds in the y direction
     * @return Boolean value
     */
    public boolean isOutOfYBounds() {
        return (this.getY() >= Game.GAME_HEIGHT - OUT_OF_BOUNDS_LOCATION || this.getY() <= OUT_OF_BOUNDS_LOCATION);
    }

    // Reverse the x direction
    public void reverseXDirection() {
        this.xSpeed *= -1;
    }

    // Reverse the y direction
    public void reverseYDirection() {
        this.ySpeed *= -1;
    }

    /**
     * Check whether the two nodes on the screen intersects with each other
     * @param node
     * @return
     */
    public boolean intersects(Node node) {
        return this.getBoundsInParent().intersects(node.getBoundsInLocal());
    }
}
