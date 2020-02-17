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

public class Game extends Application {
    public static final String TITLE = "Space Invaders";
    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = 600;
    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;
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
        backgroundMusicMediaPlayer = createMediaPlayer("game_music.mp3");
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

    // Create the game's "scene": what shapes will be in the game and their starting properties
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

    // get the root
    public Group getRoot() {
        return root;
    }

    // get the current game level
    public Level getCurLevel() {
        return curLevel;
    }

    // set the current game lvel
    public void setCurLevel(Level curLevel) {
        this.curLevel = curLevel;
    }

    // get the game timer
    public double getGameTimer() {
        return gameTimer;
    }

    // Set the game time
    public void setGameTimer(double time) {
        gameTimer = time;
    }

    // set the menu active status
    public void setMenuActive(boolean isMenuActive) {
        this.isMenuActive = isMenuActive;
    }

    // get whether the menu is active
    public boolean isMenuActive() {
        return isMenuActive;
    }

    // get the status of game over menu status
    public boolean isGameOverMenuActive() {
        return isGameOverMenuActive;
    }

//    set the game over menu to active or inactive
    public void setGameOverMenuActive(boolean isGameOverMenuActive) {
        this.isGameOverMenuActive = isGameOverMenuActive;
    }

    // get whether the start menu status is active
    public boolean isStartMenuActive() {
        return isStartMenuActive;
    }

    // set the start menu active status
    public void setStartMenuActive(boolean isStartMenuActive) {
        this.isStartMenuActive = isStartMenuActive;
    }

    // get the active status of the quit game menu
    public boolean isQuitGameMenuActive() {
        return isQuitGameMenuActive;
    }

    // set the quit menu active status
    public void setQuitGameMenuActive(boolean isQuitGameMenuActive) {
        this.isQuitGameMenuActive = isQuitGameMenuActive;
    }

    // get whether the text field is active
    public boolean isHighScoreTextFieldActive() {
        return isHighScoreTextFieldActive;
    }

    // Set the high score text field either active or inactive
    public void setHighScoreTextFieldActive(boolean isHighScoreTextFieldActive) {
        this.isHighScoreTextFieldActive = isHighScoreTextFieldActive;
    }

    // Get the animation status
    public Animation.Status getAnimationStatus() {
        return myAnimation.getStatus();
    }

    // Pause the animation
    public void pauseAnimation() {
        myAnimation.pause();
    }

    // Play the animation
    public void playAnimation() {
        myAnimation.play();
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
     * Start the program.x
     */
    public static void main (String[] args) {
        launch(args);
    }
}
