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

    public int getStartingShootTime() {
        return startingShootTime;
    }

}
