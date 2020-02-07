package invader;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    public static final String TITLE = "Space Invaders";
    public static final String WINNING_MESSAGE = "WINNER!";
    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = 600;
    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;

    // some things we need to remember during our game
    private Scene myScene;
    private Timeline myAnimation;
    private double gameTimer = 0;
    private Level currLevel;
    private Group root;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupScene(SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    public Scene setupScene(int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        root = new Group();

        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);

        // create a level
        currLevel = new Level(root,1);
        currLevel.addEnemiesAndSpaceshipToScene();
        LevelStatsDisplay.createInterfaceAndAddToRoot(root, GAME_HEIGHT, SCENE_WIDTH, SCENE_HEIGHT);

        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }

    public Group getRoot() {
        return root;
    }

    // Change properties of shapes to animate them
    void step() {
        gameTimer += Game.SECOND_DELAY;
        currLevel.handleEntitiesAndLasers(gameTimer, Game.SECOND_DELAY);
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            //moverShape.setX(moverShape.getX() + MOVER_SPEED);
            currLevel.moveSpaceship(true);
        }
        else if (code == KeyCode.LEFT) {
            //moverShape.setX(moverShape.getX() - MOVER_SPEED);
            currLevel.moveSpaceship(false);
        }
        else if (code == KeyCode.SPACE) {
            currLevel.attemptSpaceshipFire(gameTimer);
        }
        // pause/restart animation
        if (code == KeyCode.P) {
            if (myAnimation.getStatus() == Animation.Status.RUNNING) {
                myAnimation.pause();
            }
            else {
                myAnimation.play();
            }
        }
        else if (code == KeyCode.R) {
            //root.getChildren().clear();
            currLevel.clearLevel();
            gameTimer = 0;
            currLevel = new Level( root, 1);
            currLevel.addEnemiesAndSpaceshipToScene();
        }
        else if (code == KeyCode.S) {
            goToNextLevel();
        }
    }

    private void goToNextLevel() {
        currLevel.clearLevel();
        gameTimer = 0;
        int currLevelNumber = currLevel.getLevelNumber();
        currLevel = new Level( root,  ++currLevelNumber);
        LevelStatsDisplay.updateLevelNumberDisplay(currLevelNumber);
        currLevel.addEnemiesAndSpaceshipToScene();
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
