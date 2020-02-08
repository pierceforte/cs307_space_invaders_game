package invader;

import invader.entity.Enemy;
import invader.entity.Spaceship;
import invader.projectile.Laser;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest extends DukeApplicationTest {
    private final String ENEMY_ABOVE_SPACESHIP = "#enemy31";
    private final Game myGame = new Game();

    private Scene myScene;
    private Spaceship mySpaceship;
    private List<List<Enemy>> myEnemies = new ArrayList<>();
    private Enemy myEnemy31;
    private Laser mySpaceshipLaser;
    private Level myLevel;

    @Override
    public void start (Stage stage) {
        // create game's scene with all shapes in their initial positions and show it
        myScene = myGame.setupScene(Game.GAME_WIDTH, Game.GAME_HEIGHT, Game.BACKGROUND);
        stage.setScene(myScene);
        stage.show();
        myLevel = myGame.getCurLevel();

        // find individual items within game by ID (must have been set in your code using setID())
        mySpaceship = lookup("#spaceship").query();
        for (int row = 0; row < 4; row++) {
            myEnemies.add(new ArrayList<>());
            for (int col = 0; col < Level.ENEMIES_PER_ROW; col++) {
                myEnemies.get(row).add(lookup("#enemy" + (col + row*Level.ENEMIES_PER_ROW)).query());
            }
        }
        // when a laser is fired from the spaceship's default position, it will hit enemy31
        myEnemy31 = lookup(ENEMY_ABOVE_SPACESHIP).query();
        press(myScene, KeyCode.SPACE);
        // need to wait for scene to update after key press in application thread
        Platform.runLater(() -> mySpaceshipLaser = lookup("#spaceshipLaser0").query());
    }

    @Test
    public void testSkipLevelCheatKey() {
        // assert that level is level 1 at start
        assertEquals(1, myLevel.getLevelNumber());
        // press cheat key to skip to next level
        press(myScene, KeyCode.S);
        // need to reassign our level to the new level created in game
        myLevel = myGame.getCurLevel();
        // assert that level is now level 2
        Platform.runLater(() -> assertEquals(2, myLevel.getLevelNumber()));
    }

    @Test
    public void testAddLifeCheatKey() {
        // assert that spaceship has 3 (default) lives
        assertEquals(3, mySpaceship.getLives());
        // press cheat key to add life
        press(myScene, KeyCode.L);
        // assert that spaceship has gained 1 life
        assertEquals(4, mySpaceship.getLives());
    }

    @Test
    public void testSpaceshipLifeLoss() {
        // set enemy's start shooting time to 0 so it shoots immediately
        myEnemy31.setStartShootingTime(0);
        // step to initiate laser fire from enemy31
        step();
        // since all other enemies cannot fire until 1 second after game begins,
        // we know enemy31's laser is the 0th laser and can query it as such
        Laser myEnemy31Laser = lookup("#enemyLaser0").query();
        // position the laser one step prior to hitting spaceship
        myEnemy31Laser.setY(mySpaceship.getY() - 6*Laser.Y_SPEED*Game.SECOND_DELAY);
        // assert that laser is in scene and spaceship has 3 (default) lives before collision
        assertTrue(isNodeInMyScene(myEnemy31Laser));
        assertEquals(3, mySpaceship.getLives());
        step();
        // assert that laser has been removed from scene and spaceship has 2 lives after collision
        assertFalse(isNodeInMyScene(myEnemy31Laser));
        assertEquals(2, mySpaceship.getLives());
    }

    @Test
    public void testEnemiesBounceOffRightWall() {
        // check collision off right wall
        testEnemiesReverseXDirection(Level.ENEMIES_PER_ROW-1,
                Game.GAME_WIDTH - Enemy.WIDTH - Game.SECOND_DELAY, true);
    }

    @Test
    public void testEnemiesBounceOffLeftWall() {
        // check collision off left wall
        testEnemiesReverseXDirection(0, Game.SECOND_DELAY,
                false);
    }

    private void testEnemiesReverseXDirection(int enemyColumn, double startingXPos, boolean isStartingXSpeedPositive) {
        // get first or last enemy in first row
        Enemy curEnemy = myEnemies.get(0).get(enemyColumn);
        // set enemy to xPos one step before colliding with wall
        curEnemy.setX(startingXPos);
        // if checking left bound, set enemy's direction to left
        if (!isStartingXSpeedPositive) curEnemy.reverseXDirection();
        // assert that the enemy's speed is in direction of wall before collision
        assertTrue(curEnemy.getXSpeed() * (isStartingXSpeedPositive ? 1 : -1) > 0);
        // step so enemy collides with wall
        step();
        // assert that enemy's speed is in opposite direction of wall after collision
        assertTrue(curEnemy.getXSpeed() * (isStartingXSpeedPositive ? 1 : -1) < 0);
    }

    @Test
    public void testLaserDisappearsIfOutOfBounds() {
        // check if laser is on scene before being out of bounds
        assertTrue(isNodeInMyScene(mySpaceshipLaser));
        // position the laser one step prior to being out of bounds
        mySpaceshipLaser.setY(Game.GAME_HEIGHT - 20 + Laser.Y_SPEED*Game.SECOND_DELAY);
        // step so laser is out of bounds
        step();
        // check if the laser has been removed from scene upon being out of bounds
        assertFalse(isNodeInMyScene(mySpaceshipLaser));
    }

    @Test
    public void testLaserCollisionWithEnemy() {
        // check if laser and enemy are on scene before collision
        assertTrue(isNodeInMyScene(myEnemy31));
        assertTrue(isNodeInMyScene(mySpaceshipLaser));
        // position the laser one step prior to hitting enemy31
        mySpaceshipLaser.setX(myEnemy31.getX());
        mySpaceshipLaser.setY(myEnemy31.getY() + 9.5*Laser.Y_SPEED*Game.SECOND_DELAY);
        // step to initiate collision
        step();
        // check if both enemy31 and the laser have been removed from scene upon collision
        assertFalse(isNodeInMyScene(myEnemy31));
        assertFalse(isNodeInMyScene(mySpaceshipLaser));
    }

    @Test
    public void testSpaceshipInitialPosition () {
        assertEquals(Spaceship.DEFAULT_X_POS, mySpaceship.getX());
        assertEquals(Spaceship.DEFAULT_Y_POS, mySpaceship.getY());
        assertEquals(Spaceship.WIDTH, mySpaceship.getFitWidth());
        assertEquals(Spaceship.HEIGHT, mySpaceship.getFitHeight());
    }

    @Test
    public void testSpaceshipMoveLeftAndRight () {
        // test movement to right
        testSpaceshipMove(KeyCode.RIGHT, Spaceship.DEFAULT_X_POS +
                mySpaceship.X_SPEED_ON_KEY_PRESS, Spaceship.DEFAULT_X_POS);
        // test movement to left
        testSpaceshipMove(KeyCode.LEFT, Spaceship.DEFAULT_X_POS -
                mySpaceship.X_SPEED_ON_KEY_PRESS, Spaceship.DEFAULT_X_POS);
    }

    @Test
    public void testSpaceshipWrap () {
        // test wrap from right to left side of screen
       testSpaceshipMove(KeyCode.RIGHT, 0, Game.GAME_WIDTH - Spaceship.WIDTH);
       // test wrap from left to right side of screen
       testSpaceshipMove(KeyCode.LEFT, Game.GAME_WIDTH - mySpaceship.WIDTH, 0);
    }

    @Test
    public void testEnemiesInitialPosition() {
        int rows = 4;
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int row = 0; row < rows; row++) {
            double xPos = (Game.GAME_WIDTH - Level.ENEMIES_PER_ROW * (Level.ENEMY_SPACING + Enemy.WIDTH) - Level.ENEMY_SPACING)/2;
            for (int col = 0; col < Level.ENEMIES_PER_ROW; col++) {
                Enemy curEnemy = myEnemies.get(row).get(col);
                // check that enemy is in expected position
                assertEquals(curEnemy.getX(), xPos);
                assertEquals(curEnemy.getY(), yPos);
                xPos += Enemy.WIDTH + Level.ENEMY_SPACING;
            }
            yPos += Enemy.HEIGHT;
        }
    }

    private void step() {
        javafxRun(() -> myGame.step());
    }

    private boolean isNodeInMyScene(Node node) {
        return myGame.getRoot().getChildren().contains(node);
    }

    private void testSpaceshipMove(KeyCode code, double expectedPosition, double startingXPos) {
        // set initial position
        mySpaceship.setX(startingXPos);
        // move spaceship left or right one step by "pressing" the key
        press(myScene, code);
        // then check its position has changed properly
        assertEquals(expectedPosition, mySpaceship.getX());
    }
}
