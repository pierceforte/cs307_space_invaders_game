package test;

import invader.Game;
import invader.Level;
import invader.entity.Enemy;
import invader.entity.Spaceship;
import invader.projectile.Laser;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest extends DukeApplicationTest {
    private final Game myGame = new Game();
    private Scene myScene;

    private Spaceship mySpaceship;
    //private Enemy myEnemy;


    @Override
    public void start (Stage stage) {
        // create game's scene with all shapes in their initial positions and show it
        myScene = myGame.setupScene(Game.GAME_WIDTH, Game.GAME_HEIGHT, Game.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
        mySpaceship = lookup("#spaceship").query();
        //myEnemy = lookup("#enemy").query();
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
        Level testLevel = new Level("resources/level_files/level_01.txt", 1);
        int rows = testLevel.getNumberOfRows();
        int enemyNumber = 0;
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int i = 0; i < rows; i++) {
            double xPos = (Game.GAME_WIDTH - Level.ENEMIES_PER_ROW * (Level.ENEMY_SPACING + Enemy.WIDTH) - Level.ENEMY_SPACING)/2;
            for (int j = 0; j < Level.ENEMIES_PER_ROW; j++) {
                Enemy curEnemy = lookup("#enemy" + enemyNumber++).query();

                assertEquals(curEnemy.getX(), xPos);
                assertEquals(curEnemy.getY(), yPos);

                xPos += Enemy.WIDTH + Level.ENEMY_SPACING;
            }
            yPos += Enemy.HEIGHT;
        }
    }

/*
    @Test
    public void testLaserCollisionWithEnemy() {
        Level testLevel = new Level("resources/level_files/test_level_01.txt", 1);
        Laser mySpaceshipLaser = testLevel.shootSpaceshipLaser(mySpaceship);
        Enemy myEnemy = lookup("#enemy0").query();
        //Laser mySpaceshipLaser = lookup("#laser0").query();
        //assertEquals(true,true);
        boolean isEnemyActive = false;
        List<List<Enemy>> enemies = testLevel.getEnemies();
        while (myEnemy != null && mySpaceshipLaser != null &&
                !myEnemy.getBoundsInParent().intersects(mySpaceshipLaser.getBoundsInParent())) {
            for (List<Enemy> enemyRow : enemies) {
                if (enemyRow.contains(myEnemy)) {
                    isEnemyActive = true;
                    break;
                }
                else isEnemyActive = false;
            }
            assertEquals(true, isEnemyActive);
        }
        for (List<Enemy> enemyRow : enemies) {
            if (enemyRow.contains(myEnemy)) {
                isEnemyActive = true;
                break;
            }
        }
        assertEquals(false, isEnemyActive);


    }
*/
}
