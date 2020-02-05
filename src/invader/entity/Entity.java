package invader.entity;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

/**
 * @author Jeff Kim
 * started 2/4/20
 */
public class Entity extends ImageView {
    private int startingShootTime;

    /**
     * Creates a new view that represents an IMG element.
     *
     * @param elem the element to create a view for
     */
    public Entity(Element elem) {
        super(elem);
    }

    public int getStartingShootTime() {
        return startingShootTime;
    }

}
