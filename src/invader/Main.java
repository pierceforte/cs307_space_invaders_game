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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author Robert C. Duvall
 */
public class Main extends Application {
    public static final String TITLE = "Space Invaders";
    public static final String WINNING_MESSAGE = "WINNER!";
    //public static final int SIZE = 400;
    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = 600;
    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 800;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int MOVER_SPEED = 5;
    public static final int BLOCK_SIZE = 30;
    public static final int BLOCK_MIN_SPEED = 10;
    public static final int BLOCK_MAX_SPEED = 100;
    public static final int BLOCK_SPEEDUP_FACTOR = 2;


    // some things we need to remember during our game
    private Scene myScene;
    private Timeline myAnimation;

    private Level level1;
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupScene(GAME_WIDTH, GAME_HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }


    // Create the game's "scene": what shapes will be in the game and their starting properties
    Scene setupScene (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes, set their properties, and add them to the scene

        /*
        root.getChildren().add(myBlock.getShape());
        myMover = new Mover(width / 2 - BLOCK_SIZE / 4, height - BLOCK_SIZE / 2, BLOCK_SIZE / 2, BLOCK_SIZE / 2);
        root.getChildren().add(myMover.getShape());
         */

        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);

        level1 = new Level(this.getClass().getClassLoader().getResource("level_files/level_01.txt").toExternalForm(), 1);
        level1.addEnemiesToScene(root);


        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }

    // Change properties of shapes to animate them
    void step (double elapsedTime) {
        // get internal values of other classes

        // update attributes

        // check for collisions

        // TODO: check for win and, if true, pause the animation
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        // move player

        if (code == KeyCode.RIGHT) {
            //moverShape.setX(moverShape.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            //moverShape.setX(moverShape.getX() - MOVER_SPEED);
        }
        else if (code == KeyCode.UP) {
            //moverShape.setY(moverShape.getY() - MOVER_SPEED);
        }
        else if (code == KeyCode.DOWN) {
            //moverShape.setY(moverShape.getY() + MOVER_SPEED);
        }
        // pause/restart animation
        if (code == KeyCode.SPACE) {
            if (myAnimation.getStatus() == Animation.Status.RUNNING) {
                myAnimation.pause();
            }
            else {
                myAnimation.play();
            }
        }
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
