package invader;

import invader.entity.Spaceship;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.*;


/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Class that is used to display all messages on the screen, It deals with the splash screens before, between, and after each level and
 * the current status for that level. All variables and methods are static because it could be used anytime during the game and do not need
 * multiple instances. As such, the class is a final class. This class, if time had allowed, would have been a focus for refactoring given its length.
 * While the number of constants is high, we wanted to focus on flexibility and eliminate duplication/magic values.
 */

public final class StatusDisplay {
    public static final String ERROR_LOG = "error_log.txt";
    public static final Paint MENU_BACKGROUND = Color.GRAY;
    public static final Paint INTERFACE_BACKGROUND = Color.GRAY;
    public static final Paint TEXT_COLOR = Color.MAROON;
    public static final String GAMEOVER_TEXT = "GAME OVER!\n\n\n";
    public static final String THANKS_TEXT = "THANKS FOR PLAYING!\n\n\n";
    public static final String WINNER_TEXT = "YOU WIN!\n\n\n";
    public static final String LEVEL_COMPLETE_TEXT = "LEVEL COMPLETE!\n\n\nPRESS S TO ADVANCE";
    public static final String RESTART_AND_CHANGE_LEVEL = "\n\nPRESS R TO RESTART LEVEL\n\nPRESS 1-9 TO CHANGE LEVEL";
    public static final String RESTART_OR_EXIT_INSTRUCTIONS = "PRESS W TO PLAY AGAIN\n\nPRESS Q TO EXIT GAME";
    public static final String HIGHSCORE_INSTRUCTION = "PRESS E TO SAVE YOUR SCORE\nAND RESET POINTS";
    public static final String FONT = "Verdana";
    public static final int DEFAULT_TEXT_SIZE = 15;
    public static final String HEART_IMAGE = "heart.png";
    public static final int HEART_IMAGE_X_POS = 15;
    public static final int HEART_IMAGE_Y_DIST_FROM_GAME_HEIGHT = 40;
    public static final int HEART_IMAGE_SCALE_DOWN_FACTOR = 75;
    public static final int LIFE_COUNT_X_DIST_FROM_HEART = 35;
    public static final int LIFE_COUNT_Y_DIST_FROM_GAME_HEIGHT = 65;
    public static final String CHARACTER_BETWEEN_HEART_AND_LIVES = " * ";
    public static final int LEVEL_NUM_X_POS = 22;
    public static final int LEVEL_NUM_Y_DIST_FROM_GAME_HEIGHT = 30;
    public static final String LEVEL_TEXT = "LEVEL ";
    public static final int POINTS_X_DIST_FROM_SCENE_CENTER = -35;
    public static final int POINTS_Y_DIST_FROM_GAME_HEIGHT = 35;
    public static final String POINTS_TEXT = "POINTS\n";
    public static final int HIGHSCORE_X_DIST_FROM_SCENE_WIDTH = 110;
    public static final int HIGHSCORE_Y_DIST_FROM_GAME_HEIGHT = 35;
    public static final String HIGHSCORE_TEXT = "HIGHSCORE\n";
    public static final String YOUR_SCORE_TEXT ="YOUR SCORE\n";
    public static final String HIGHSCORE_CHART_TEXT = "\n\n\nHIGH SCORES:";
    public static final int DEFAULT_MENU_X_POS = 85;
    public static final int DEFAULT_MENU_Y_POS = 275;
    public static final int GAMEOVER_MENU_Y_POS = 200;
    public static final int START_MENU_X_POS = 40;
    public static final int START_MENU_Y_POS = 70;
    public static final int VICTORY_MENU_Y_POS = 225;
    public static final int THANKS_MENU_X_POS = 100;
    public static final int THANKS_MENU_Y_POS = 225;
    public static final int BOSS_MENU_X_POS = 75;
    public static final int BOSS_MENU_Y_POS = 225;
    public static final int HIGHSCORE_TEXT_FIELD_WIDTH = 150;
    public static final String DEFAULT_HIGHSCORE_TEXT_FIELD_TEXT = "Name";
    public static final String POINTS_FORMAT = "%06d";
    public static final String HIGHSCORES_FILE_PATH = "resources/highscores.txt";
    public static final int NUM_HIGHSCORES_STORED = 100;
    public static final int NUM_HIGHSCORES_DISPLAYED = 5;
    public static final String SCORE_DELIMITER = ":";

    private static Rectangle menuBackground;
    private static Rectangle userInterfaceArea;
    private static ImageView heartImageDisplay;
    private static TextField highScoreTextField;
    private static Text menuText;
    private static Text lifeCountText;
    private static Text levelNumberDisplay;
    private static Text pointsDisplay;
    private static Text highScoreDisplay;
    private static int points;
    private static Set<String> highscores = new TreeSet<>(Comparator
            .comparing((String entry) -> Integer.parseInt(entry.split(SCORE_DELIMITER)[1]))
            .reversed()
            .thenComparing((entry) -> entry.toLowerCase()));

    private StatusDisplay() {
        //not called
    }

    /**
     * Logs the error if it ever happens
     * @param e
     */
    public static void logError(Exception e) {
        try {
            FileWriter fStream = new FileWriter(ERROR_LOG, true);
            BufferedWriter out = new BufferedWriter(fStream);
            PrintWriter pw = new PrintWriter(out, true);
            e.printStackTrace(pw);
            fStream.close();
        }
        catch (Exception ie) {
            throw new RuntimeException("Could not write Exception to file", ie);
        }
    }

    /**
     * Create the interface of the status display
     * @param root
     * @param game_height
     * @param scene_width
     * @param scene_height
     */
    public static void createInterfaceAndAddToRoot(Group root, int game_height, int scene_width, int scene_height) {
        createInterfaceBackground(root, game_height, scene_width, scene_height);
        heartImageDisplay = createImageDisplay(root, HEART_IMAGE_X_POS, game_height +
                HEART_IMAGE_Y_DIST_FROM_GAME_HEIGHT, HEART_IMAGE, HEART_IMAGE_SCALE_DOWN_FACTOR);
        lifeCountText = createTextDisplayAndAddToRoot(root, CHARACTER_BETWEEN_HEART_AND_LIVES + Spaceship.DEFAULT_LIVES, heartImageDisplay.getX() +
                LIFE_COUNT_X_DIST_FROM_HEART, game_height + LIFE_COUNT_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        levelNumberDisplay = createTextDisplayAndAddToRoot(root, LEVEL_TEXT + Game.MIN_LEVEL, LEVEL_NUM_X_POS,
                game_height + LEVEL_NUM_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        pointsDisplay = createTextDisplayAndAddToRoot(root, POINTS_TEXT + formatPoints(points), scene_width/2 +
                POINTS_X_DIST_FROM_SCENE_CENTER, game_height + POINTS_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        points = 0;
        addHighScoreDisplay(root, game_height, scene_width);
        highscores.addAll(readInHighScores());
    }

    /**
     * Update the life count on the display
     * @param lives number of lives to display on screen
     */
    public static void updateLifeCountDisplay(int lives) {
        lifeCountText.setText(CHARACTER_BETWEEN_HEART_AND_LIVES + lives);
    }

    /**
     * Update the level number on display
     * @param levelNumber level number to display
     */
    public static void updateLevelNumberDisplay(int levelNumber) {
        levelNumberDisplay.setText(LEVEL_TEXT + levelNumber);
    }

    /**
     * Get the menu text
     * @return menuText
     */
    public static Text getMenuText() {
        return menuText;
    }

    /**
     * Create starting splash screen
     * @param root the Group to which nodes are added for the game
     */
    public static void createStartMenu(Group root) {
        createMenu(root, START_MENU_X_POS, START_MENU_Y_POS, "SPACE INVADERS\nBY PIERCE AND JEFF\n\n\nYOU START WITH 3 LIVES\n\n" +
                "DESTROY ENEMIES TO EARN POINTS\n\nMOVE THE SPACESHIP WITH\nLEFT AND RIGHT KEYS\n\n" +
                "COLLECT POWER UPS: \nSPEED UP\nSTRONGER MISSILES\nMULTIPLE MISSILES\n\nBEAT ALL 3 LEVELS + BOSS LEVEL TO WIN!\n\n\n" +
                "CHEAT CODES:\n1-9   SKIP TO LEVEL\nA,B,F,M   DROP POWER UP\nD   DESTROY FIRST ENEMY\n" +
                "L   ADD 1 LIFE\nP   PAUSE\nR   RESET LEVEL\nS   SKIP TO NEXT LEVEL\n\n\n" +
                "PRESS SPACE TO BEGIN");
    }

    /**
     * Removes the splash screen
     * @param root the Group to which nodes are added for the game
     */
    public static void removeMenu(Group root) {
        root.getChildren().remove(menuBackground);
        root.getChildren().remove(menuText);
    }

    /**
     * Create the game over splash screen
     * @param root the Group to which nodes are added for the game
     */
    public static void createGameOverMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, GAMEOVER_MENU_Y_POS, GAMEOVER_TEXT + getYourScoreText() + HIGHSCORE_INSTRUCTION
                + RESTART_AND_CHANGE_LEVEL + collectTopHighScores(NUM_HIGHSCORES_DISPLAYED));
    }

    /**
     * Create the level intermission level splash screen
     * @param root the Group to which nodes are added for the game
     */
    public static void createLevelIntermissionMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, DEFAULT_MENU_Y_POS, LEVEL_COMPLETE_TEXT + RESTART_AND_CHANGE_LEVEL);
    }

    /**
     * Create the boss level intermission menu
     * @param root the Group to which nodes are added for the game
     */
    public static void createBossLevelMenu(Group root) {
        createMenu(root, BOSS_MENU_X_POS, BOSS_MENU_Y_POS, "LEVEL COMPLETE!\n\n\nGET READY FOR THE BOSS ROUND\n\n" +
                "RULES\nSTART WITH 5 LIVES \n\nBOSS HAS:\n10 LIVES\nVULNERABLE STATE\nINVINCIBLE STATE\n" +
                "LASERS: SINGLE DAMAGE\nFIREBALLS: DOUBLE DAMAGE\n\n\nPRESS S TO START");
    }

    /**
     * Create the splash screen for victory condition
     * @param root the Group to which nodes are added for the game
     */
    public static void createVictoryMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, VICTORY_MENU_Y_POS, WINNER_TEXT + getYourScoreText() + HIGHSCORE_INSTRUCTION
                + RESTART_AND_CHANGE_LEVEL + collectTopHighScores(NUM_HIGHSCORES_DISPLAYED));
    }

    /**
     * Create restart or end splash screen
     * @param root the Group to which nodes are added for the game
     */
    public static void createRestartOrEndMenu(Group root) {
        createMenu(root, THANKS_MENU_X_POS, THANKS_MENU_Y_POS, THANKS_TEXT + getYourScoreText()
                + RESTART_OR_EXIT_INSTRUCTIONS + collectTopHighScores(NUM_HIGHSCORES_DISPLAYED));
    }

    /**
     * Create the high score text field on the status display
     * @param root the Group to which nodes are added for the game
     */
    public static void createHighScoreTextField(Group root) {
        highScoreTextField = new TextField(DEFAULT_HIGHSCORE_TEXT_FIELD_TEXT);
        highScoreTextField.setPrefWidth(HIGHSCORE_TEXT_FIELD_WIDTH);
        highScoreTextField.setLayoutX(Game.GAME_WIDTH/2 - HIGHSCORE_TEXT_FIELD_WIDTH/2);
        highScoreTextField.setLayoutY(Game.GAME_HEIGHT/2);

        highScoreTextField.setPromptText(DEFAULT_HIGHSCORE_TEXT_FIELD_TEXT);
        root.getChildren().add(highScoreTextField);
    }

    /**
     * Keep track of the high score of the game
     * @param root the Group to which nodes are added for the game
     */
    public static void storeHighScore(Group root) {
        String name = highScoreTextField.getText();
        name = String.join("", name.split(SCORE_DELIMITER));
        root.getChildren().remove(highScoreTextField);
        highscores.add(name + SCORE_DELIMITER + points);
        updateHighScoreList(highscores);
    }

    /**
     * Update the points on the display
     * @param pointsEarned the number of points to be added to the points on display
     */
    public static void updatePointsDisplay(int pointsEarned) {
        points += pointsEarned;
        pointsDisplay.setText(POINTS_TEXT + formatPoints(points));
    }

    /**
     * Update the high score on the display
     */
    public static void updateHighScoreDisplay() {
        File file = new File(HIGHSCORES_FILE_PATH);
        try {
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                int highScore = Integer.parseInt(myReader.nextLine().split(SCORE_DELIMITER)[1]);
                highScoreDisplay.setText(HIGHSCORE_TEXT + formatPoints(highScore));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logError(e);
        }
    }

    /**
     * Reset the total points on display
     */
    public static void resetPointsDisplay() {
        points = 0;
        updatePointsDisplay(0);
    }

    /**
     * Get menu background
     */
    public static Rectangle getMenuBackground() {
        return menuBackground;
    }

    private static void createInterfaceBackground(Group root, int game_height, int scene_width, int scene_height) {
        int heightOfInterface = scene_height - game_height;
        userInterfaceArea = new Rectangle(0, game_height, scene_width, heightOfInterface);
        userInterfaceArea.setFill(INTERFACE_BACKGROUND);
        root.getChildren().add(userInterfaceArea);
    }

    private static ImageView createImageDisplay(Group root, double xPos, double yPos, String imgName, double imgScaleDownFactor) {
        Image img = new Image(StatusDisplay.class.getClassLoader().getResource(imgName).toExternalForm());
        ImageView display = new ImageView(img);
        display.setX(xPos);
        display.setY(yPos);
        display.setFitHeight(img.getHeight()/imgScaleDownFactor);
        display.setFitWidth(img.getWidth()/imgScaleDownFactor);
        root.getChildren().add(display);
        return display;
    }

    private static String getYourScoreText() {
        return YOUR_SCORE_TEXT + points + "\n\n";
    }

    private static Text createTextDisplayAndAddToRoot(Group root, String text, double xPos, double yPos, Paint color) {
        Text tempDisplay = createTextDisplay(text, xPos, yPos, color);
        root.getChildren().add(tempDisplay);
        return tempDisplay;
    }

    private static Text createTextDisplay(String text, double xPos, double yPos, Paint color) {
        Text tempDisplay = new Text(text);
        tempDisplay.setFont(Font.font (FONT, DEFAULT_TEXT_SIZE));
        tempDisplay.setX(xPos);
        tempDisplay.setY(yPos);
        tempDisplay.setFill(color);
        tempDisplay.setTextAlignment(TextAlignment.CENTER);
        return tempDisplay;
    }

    private static String formatPoints(int points) {
        return String.format(POINTS_FORMAT, points);
    }

    private static void createMenu(Group root, double xPos, double yPos, String text) {
        menuBackground = new Rectangle(0, 0, Game.SCENE_WIDTH, Game.SCENE_HEIGHT);
        menuBackground.setFill(MENU_BACKGROUND);
        root.getChildren().add(menuBackground);
        menuText = createTextDisplayAndAddToRoot(root, text, xPos, yPos, Color.MAROON);
        menuText.setTextAlignment(TextAlignment.CENTER);
    }

    private static List<String> readInHighScores() {
        List<String> highscores = new ArrayList<>();
        try {
            File file = new File(HIGHSCORES_FILE_PATH);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                highscores.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logError(e);
        }
        return highscores;
    }

    private static String collectTopHighScores(int maxNumberOfHighScores) {
        if (highscores.isEmpty()) return "";
        String highscoresChart = HIGHSCORE_CHART_TEXT;
        int scoresCharted = 0;
        for (String highscore : highscores) {
            if (scoresCharted >= maxNumberOfHighScores) break;
            scoresCharted++;
            highscoresChart += "\n" + highscore.split(SCORE_DELIMITER)[0] + "   " + highscore.split(SCORE_DELIMITER)[1];
        }
        return highscoresChart;
    }

    private static void addHighScoreDisplay(Group root, double game_height, double scene_width) {
        highScoreDisplay = createTextDisplayAndAddToRoot(root, HIGHSCORE_TEXT + formatPoints(0), scene_width -
                HIGHSCORE_X_DIST_FROM_SCENE_WIDTH, game_height + HIGHSCORE_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        updateHighScoreDisplay();
    }

    private static void updateHighScoreList(Set<String> highscores) {
        try {
            PrintWriter pw = new PrintWriter(HIGHSCORES_FILE_PATH);
            int numEntries = 0;
            for (String entry : highscores) {
                if (numEntries < NUM_HIGHSCORES_STORED) {
                    pw.println(entry);
                    numEntries++;
                }
                else break;
            }
            pw.close();
        } catch (FileNotFoundException e) {
            logError(e);
        }
    }
}
