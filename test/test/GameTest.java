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
        assertEquals(Game.GAME_WIDTH/2 - Spaceship.WIDTH/2, mySpaceship.getX());
        assertEquals(Game.GAME_HEIGHT - 30, mySpaceship.getY());
        assertEquals(Spaceship.WIDTH, mySpaceship.getFitWidth());
        assertEquals(Spaceship.HEIGHT, mySpaceship.getFitHeight());
    }

    /*@Test
    public void testSpaceshipMove () {
        // given the game has just started, take one step
        //myGame.step(Game.SECOND_DELAY);
        //365
        mySpaceship.setX(Game.GAME_WIDTH - Spaceship.WIDTH/2);
        // then check block has moved forward some (its speed is random)
        assertTrue(mySpaceship.getX() > 0);
        assertEquals(200 - 15, mySpaceship.getY());
    }*/

    @Test
    public void testSpaceshipWrap () {
        // given the game has just started, take one step
        //myGame.step(Game.SECOND_DELAY);
        //365
        mySpaceship.setX(Game.GAME_WIDTH - Spaceship.WIDTH);
        // move it up one step by "pressing" the up arrow
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        press(myScene, KeyCode.RIGHT);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: but useful when debugging to verify what is happening
        // then check its position has changed properly
        assertEquals(0, mySpaceship.getX());

        mySpaceship.setX(0);
        // move it up one step by "pressing" the up arrow
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
