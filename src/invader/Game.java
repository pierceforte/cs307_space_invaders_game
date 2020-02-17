package invader;

import invader.level.Level;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Main class that runs the entire game.
 * It initializes all the basic scene, key, and music setup.
 * Runs the animation and calls the step function for each frame
 */

public class Game extends Application {
    public static final String TITLE = "Space Invaders";
    public static final String GAME_MUSIC_FILE = "game_music.wav";
    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = 600;
    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 4;

    // some things we need to remember during our game
    private Scene myScene;
    private Timeline myAnimation;
    private KeyHandler myKeyHandler;
    private double gameTimer = 0;
    private Level curLevel;
    private Group root;
    private MediaPlayer backgroundMusicMediaPlayer;
    private boolean isMenuActive = true;
    private boolean isStartMenuActive = true;
    private boolean isGameOverMenuActive = false;
    private boolean isHighScoreTextFieldActive = false;
    private boolean isQuitGameMenuActive = false;

    public Game() {
        super();
    }
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) throws URISyntaxException {
        // add music. NOTE: due to issues with garbage collection, the following two methods could not be combined into one
        backgroundMusicMediaPlayer = createMediaPlayer(GAME_MUSIC_FILE);
        playMusic(MediaPlayer.INDEFINITE, true, backgroundMusicMediaPlayer);
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

    /**
     * Create the game's "scene": what shapes will be in the game and their starting properties
     * @param width width of scene
     * @param height height of scene
     * @param background color of background
     * @return
     */
    public Scene setupScene(int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        root = new Group();

        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);

        // create a level
        StatusDisplay.createStartMenu(root);
        // respond to input
        myKeyHandler = new KeyHandler(this);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }

    /**
     * Get the root
     * @return root the Group to which nodes are added for the game
     */
    public Group getRoot() {
        return root;
    }

    /**
     * Get the current game level
     * @return current level
     */
    public Level getCurLevel() {
        return curLevel;
    }

    /**
     * Set the current game level
     * @param curLevel current level
     */
    public void setCurLevel(Level curLevel) {
        this.curLevel = curLevel;
    }

    /**
     * Get the game timer
     * @return gameTimer how long the game has run since its timer was reset
     */
    public double getGameTimer() {
        return gameTimer;
    }

    /**
     * Set the game time
     * @param time what the timer should be set to
     */
    public void setGameTimer(double time) {
        gameTimer = time;
    }

    /**
     * Set the menu active status
     * @param isMenuActive
     */
    public void setMenuActive(boolean isMenuActive) {
        this.isMenuActive = isMenuActive;
    }

    /**
     * Get whether the menu is active
     * @return
     */
    public boolean isMenuActive() {
        return isMenuActive;
    }

    /**
     * Get the status of game over menu status
     * @return
     */
    public boolean isGameOverMenuActive() {
        return isGameOverMenuActive;
    }

    /**
     * Set the game over menu to active or inactive
     * @param isGameOverMenuActive
     */
    public void setGameOverMenuActive(boolean isGameOverMenuActive) {
        this.isGameOverMenuActive = isGameOverMenuActive;
    }

    /**
     * Get whether the start menu status is active
     * @return
     */
    public boolean isStartMenuActive() {
        return isStartMenuActive;
    }

    /**
     * Set the start menu active status
     * @param isStartMenuActive
     */
    public void setStartMenuActive(boolean isStartMenuActive) {
        this.isStartMenuActive = isStartMenuActive;
    }

    /**
     * Get the active status of the quit game menu
     * @return
     */
    public boolean isQuitGameMenuActive() {
        return isQuitGameMenuActive;
    }

    /**
     * Set the quit menu active status
     * @param isQuitGameMenuActive
     */
    public void setQuitGameMenuActive(boolean isQuitGameMenuActive) {
        this.isQuitGameMenuActive = isQuitGameMenuActive;
    }

    /**
     * Get whether the text field is active
     * @return
     */
    public boolean isHighScoreTextFieldActive() {
        return isHighScoreTextFieldActive;
    }

    /**
     * Set the high score text field either active or inactive
     * @param isHighScoreTextFieldActive
     */
    public void setHighScoreTextFieldActive(boolean isHighScoreTextFieldActive) {
        this.isHighScoreTextFieldActive = isHighScoreTextFieldActive;
    }

    /**
     * Get the animation status
     * @return
     */
    public Animation.Status getAnimationStatus() {
        return myAnimation.getStatus();
    }

    /**
     * Get the animation status
     */
    public void pauseAnimation() {
        myAnimation.pause();
    }

    /**
     * Play the animation
     */
    public void playAnimation() {
        myAnimation.play();
    }

    /**
     * Change properties of shapes to animate them
     */
    void step() {
        if (!isMenuActive) {
            gameTimer += Game.SECOND_DELAY;
            curLevel.handleEntitiesAndLasers(gameTimer, Game.SECOND_DELAY);
        }
    }

    /**
     * Handles a given key press
     * @param code signifies the key that was pressed
     */
    private void handleKeyInput (KeyCode code) {
        myKeyHandler.handleInput(code);
    }

    // due to issues with garbage collection, the media player must be stored globally; here is a function to
    // create the media player, allowing us to to run playMusic().
    private MediaPlayer createMediaPlayer(String fileName) throws URISyntaxException {
        Media song = new Media(this.getClass().getClassLoader().getResource(fileName).toURI().toString());
        return new MediaPlayer(song);
    }

    private void playMusic(int cycles, boolean autoPlay, MediaPlayer mediaPlayer) {
        mediaPlayer.setAutoPlay(autoPlay);
        mediaPlayer.setCycleCount(cycles);
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
