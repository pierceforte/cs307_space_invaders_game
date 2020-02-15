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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<KeyCode, Runnable> keyToActionMap = new HashMap<>();
    public static final List<KeyCode> KEY_CODES_1_THROUGH_9 = List.of(KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4,
            KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9);


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
        StatusDisplay.createInterfaceAndAddToRoot(root, GAME_HEIGHT, SCENE_WIDTH, SCENE_HEIGHT);
        curLevel = new Level(root,1, this);
        // respond to input
        initializeKeyToActionMap();
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
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (isMenuActive) {
            if (isKeyCodeADigit(code) || code == KeyCode.R || code == KeyCode.S) {
                isMenuActive = false;
                StatusDisplay.removeMenu(root);
            }
            else return;
        }
        if (keyToActionMap.containsKey(code)) keyToActionMap.get(code).run();
    }

    private void goToLevel(int levelNumber) {
        curLevel.clearLevel();
        gameTimer = 0;
        curLevel = new Level(root, levelNumber, this);
    }

    private boolean isKeyCodeADigit(KeyCode code) {
        return (code.getCode() >= KEY_CODE_1 && code.getCode() <= KEY_CODE_9);
    }

    private void initializeKeyToActionMap() {
        keyToActionMap.put(KeyCode.RIGHT, () -> curLevel.moveSpaceship(true));
        keyToActionMap.put(KeyCode.LEFT, () -> curLevel.moveSpaceship(false));
        keyToActionMap.put(KeyCode.SPACE, () -> curLevel.attemptSpaceshipFire(gameTimer));
        keyToActionMap.put(KeyCode.L, () -> curLevel.addLife());
        keyToActionMap.put(KeyCode.A, () -> curLevel.addPowerUpSpeed(gameTimer));
        keyToActionMap.put(KeyCode.B, () -> curLevel.addPowerUpBomb(gameTimer));
        keyToActionMap.put(KeyCode.R, () -> goToLevel(curLevel.getLevelNumber()));
        keyToActionMap.put(KeyCode.D, () -> curLevel.destroyFirstEnemy());
        keyToActionMap.put(KeyCode.P, () -> {
            if (myAnimation.getStatus() == Animation.Status.RUNNING) myAnimation.pause();
            else myAnimation.play();
        });
        keyToActionMap.put(KeyCode.S, () -> {
            if (curLevel.getLevelNumber() < MAX_LEVEL) goToLevel(curLevel.getLevelNumber()+1);
            });
        for (KeyCode code : KEY_CODES_1_THROUGH_9) {
            keyToActionMap.put(code, () -> {int levelNumber = code.getCode() <= KEY_CODE_9 - 7 ? code.getCode()-KEY_CODE_TO_LEVEL_CONVERSION : MAX_LEVEL;
                goToLevel(levelNumber);});
        }
    }

    /**
     * Start the program.x
     */
    public static void main (String[] args) {
        launch(args);
    }
}
