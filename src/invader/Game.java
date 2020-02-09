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
    public static final int KEY_CODE_1 = 49;
    public static final int KEY_CODE_9 = 57;
    public static final int KEY_CODE_TO_LEVEL_CONVERSION = 48;
    public static final int MAX_LEVEL = 3;


    // some things we need to remember during our game
    private Scene myScene;
    private Timeline myAnimation;
    private double gameTimer = 0;
    private Level curLevel;
    private Group root;
    private boolean isMenuActive = false;

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
        curLevel = new Level(root,1, this);
        StatusDisplay.createInterfaceAndAddToRoot(root, GAME_HEIGHT, SCENE_WIDTH, SCENE_HEIGHT);

        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }

    public Group getRoot() {
        return root;
    }

    public Level getCurLevel() {
        return curLevel;
    }

    public void setMenuActive() {
        isMenuActive = true;
    }

    public boolean isMenuActive() {
        return isMenuActive;
    }

    // Change properties of shapes to animate them
    void step() {
        if (!isMenuActive) {
            gameTimer += Game.SECOND_DELAY;
            curLevel.handleEntitiesAndLasers(gameTimer, Game.SECOND_DELAY);
        }
        if (isMenuActive) {
            myScene.setOnKeyPressed(e -> handleMenuKeyInput(e.getCode()));
        }
    }

    private void handleMenuKeyInput (KeyCode code) {
//        if (isKeyCodeADigit(code) || code == KeyCode.R || code == KeyCode.S) {
//            if (isMenuActive) {
//                isMenuActive = false;
//                StatusDisplay.removeMenu(root);
//            }
//        }
        if (code == KeyCode.R) {
            goToLevel(curLevel.getLevelNumber());
        }
        else if (code == KeyCode.S) {
            if (curLevel.getLevelNumber() < MAX_LEVEL) {
                goToLevel(curLevel.getLevelNumber()+1);
            }
        }
        else if (isKeyCodeADigit(code)) {
            int levelNumber = code.getCode() <= KEY_CODE_9 - 7 ? code.getCode()-KEY_CODE_TO_LEVEL_CONVERSION : MAX_LEVEL;
            goToLevel(levelNumber);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            //moverShape.setX(moverShape.getX() + MOVER_SPEED);
            curLevel.moveSpaceship(true);
        }
        else if (code == KeyCode.LEFT) {
            //moverShape.setX(moverShape.getX() - MOVER_SPEED);
            curLevel.moveSpaceship(false);
        }
        else if (code == KeyCode.SPACE) {
            curLevel.attemptSpaceshipFire(gameTimer);
        }
        if (isKeyCodeADigit(code) || code == KeyCode.R || code == KeyCode.S) {
            if (isMenuActive) {
                isMenuActive = false;
                StatusDisplay.removeMenu(root);
            }
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
            goToLevel(curLevel.getLevelNumber());
        }
        else if (code == KeyCode.S) {
            if (curLevel.getLevelNumber() < MAX_LEVEL) {
                goToLevel(curLevel.getLevelNumber()+1);
            }
        }
        else if (code == KeyCode.L) {
            curLevel.addLife();
        }
        else if (code == KeyCode.A) {
            curLevel.addPowerUp(gameTimer);
        }
        else if (isKeyCodeADigit(code)) {
            int levelNumber = code.getCode() <= KEY_CODE_9 - 7 ? code.getCode()-KEY_CODE_TO_LEVEL_CONVERSION : MAX_LEVEL;
            goToLevel(levelNumber);
        }
    }

    private void goToLevel(int levelNumber) {
        curLevel.clearLevel();
        gameTimer = 0;
        curLevel = new Level(root, levelNumber, this);
        StatusDisplay.updateLevelNumberDisplay(levelNumber);
    }

    private boolean isKeyCodeADigit(KeyCode code) {
        return (code.getCode() >= KEY_CODE_1 && code.getCode() <= KEY_CODE_9);
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
