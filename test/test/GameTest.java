package test;

import invader.Game;
import invader.entity.Enemy;
import invader.entity.Spaceship;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest extends DukeApplicationTest {
    private final Game myGame = new Game();
    private Scene myScene;

    private Spaceship mySpaceship;
    private Enemy myEnemy;

    @Override
    public void start (Stage stage) {
        // create game's scene with all shapes in their initial positions and show it
        myScene = myGame.setupScene(Game.GAME_WIDTH, Game.GAME_HEIGHT, Game.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
        mySpaceship = lookup("#spaceship").query();
        myEnemy = lookup("#enemy").query();
    }

    @Test
    public void testSpaceshipInitialPosition () {
        assertEquals(Game.GAME_WIDTH/2 - Spaceship.WIDTH/2, mySpaceship.getX());
        assertEquals(Game.GAME_HEIGHT - 30, mySpaceship.getY());
        assertEquals(30, mySpaceship.getFitWidth());
        assertEquals(Spaceship.HEIGHT, mySpaceship.getFitHeight());
    }
}
