package invader;

import invader.entity.Enemy;
import invader.entity.Spaceship;
import invader.projectile.Laser;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest extends DukeApplicationTest {
    private final Game myGame = new Game();
    private Scene myScene;

    private Spaceship mySpaceship;
    private List<List<Enemy>> myEnemies = new ArrayList<>();
    private Enemy myEnemy31;
    private Enemy myEnemy;
    private Laser mySpaceshipLaser;


    @Override
    public void start (Stage stage) {
        // create game's scene with all shapes in their initial positions and show it
        myScene = myGame.setupScene(Game.GAME_WIDTH, Game.GAME_HEIGHT, Game.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
//        press(myScene, KeyCode.SPACE);
        mySpaceship = lookup("#spaceship").query();
        //myEnemy = lookup("#enemy").query();
        for (int i = 0; i < 4; i++) {
            myEnemies.add(new ArrayList<>());
            for (int j = 0; j < Level.ENEMIES_PER_ROW; j++) {
                myEnemies.get(i).add(lookup("#enemy" + (j + i*Level.ENEMIES_PER_ROW)).query());
            }
        }
        myEnemy31 = lookup("#enemy31").query();
        //press(myScene, KeyCode.SPACE);
        myGame.fire();
        sleep(1, TimeUnit.SECONDS);
        mySpaceshipLaser = lookup("#spaceshipLaser0").query();
        //mySpaceshipLaser = new Laser(0,0,false);
        myEnemy = lookup("#enemy0").query();
    }

    @Test
    public void testLaserCollisionWithEnemy() {
        Platform.runLater(() -> {
            // check if laser and enemy are on scene before collision
            assertEquals(true, myGame.getRoot().getChildren().contains(myEnemy31));
            assertEquals(true, myGame.getRoot().getChildren().contains(mySpaceshipLaser));
            // position the laser one step prior to hitting enemy31
            mySpaceshipLaser.setX(myEnemy31.getX());
            mySpaceshipLaser.setY(myEnemy31.getY() + 9.5*Laser.Y_SPEED*Game.SECOND_DELAY);
            myGame.step(Game.SECOND_DELAY);
            // check if both enemy31 and the laser have been removed from scene upon collision
            assertEquals(false, myGame.getRoot().getChildren().contains(myEnemy31));
            assertEquals(false, myGame.getRoot().getChildren().contains(mySpaceshipLaser));
        });
    }


    @Test
    public void testSpaceshipInitialPosition () {
        assertEquals(Spaceship.DEFAULT_X_POS, mySpaceship.getX());
        assertEquals(Spaceship.DEFAULT_Y_POS, mySpaceship.getY());
        assertEquals(Spaceship.WIDTH, mySpaceship.getFitWidth());
        assertEquals(Spaceship.HEIGHT, mySpaceship.getFitHeight());
    }

    @Test
    public void testSpaceshipMove () {
        // move it right one step by "pressing" the left arrow
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        press(myScene, KeyCode.RIGHT);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: but useful when debugging to verify what is happening
        // then check its position has changed properly
        assertEquals(Spaceship.DEFAULT_X_POS + mySpaceship.X_SPEED_ON_KEY_PRESS, mySpaceship.getX());

        // reset spaceship position
        mySpaceship.setX(Spaceship.DEFAULT_X_POS);
        // move it left one step by "pressing" the left arrow
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        press(myScene, KeyCode.LEFT);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: but useful when debugging to verify what is happening
        // then check its position has changed properly
        assertEquals(Spaceship.DEFAULT_X_POS - mySpaceship.X_SPEED_ON_KEY_PRESS, mySpaceship.getX());
    }

    @Test
    public void testSpaceshipWrap () {
        // set position to far right of screen
        mySpaceship.setX(Game.GAME_WIDTH - Spaceship.WIDTH);
        // move it right one step by "pressing" the right arrow
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        press(myScene, KeyCode.RIGHT);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: but useful when debugging to verify what is happening
        // then check its position has changed properly
        assertEquals(0, mySpaceship.getX());

        // set position to far left of screen
        mySpaceship.setX(0);
        // move it left one step by "pressing" the left arrow
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        press(myScene, KeyCode.LEFT);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: but useful when debugging to verify what is happening
        // then check its position has changed properly
        assertEquals(Game.GAME_WIDTH - mySpaceship.getFitWidth(), mySpaceship.getX());
    }

    @Test
    public void testEnemiesInitialPosition() {
        int rows = 4;
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int i = 0; i < rows; i++) {
            double xPos = (Game.GAME_WIDTH - Level.ENEMIES_PER_ROW * (Level.ENEMY_SPACING + Enemy.WIDTH) - Level.ENEMY_SPACING)/2;
            for (int j = 0; j < Level.ENEMIES_PER_ROW; j++) {
                Enemy curEnemy = myEnemies.get(i).get(j);

                assertEquals(curEnemy.getX(), xPos);
                assertEquals(curEnemy.getY(), yPos);

                xPos += Enemy.WIDTH + Level.ENEMY_SPACING;
            }
            yPos += Enemy.HEIGHT;
        }
    }

}
