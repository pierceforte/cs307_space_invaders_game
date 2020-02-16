package invader;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class StatusDisplay {

    public static final Paint MENU_BACKGROUND = Color.GRAY;
    public static final Paint INTERFACE_BACKGROUND = Color.GRAY;
    public static final Paint TEXT_COLOR = Color.MAROON;
    public static final String RESTART_AND_CHANGE_LEVEL = "\n\nPRESS R TO RESTART LEVEL\n\nPRESS 1-9 TO CHANGE LEVEL";
    public static final String FONT = "Verdana";
    public static final int DEFAULT_TEXT_SIZE = 15;
    public static final String HEART_IMAGE = "heart.png";
    public static final int HEART_IMAGE_X_POS = 15;
    public static final int HEART_IMAGE_Y_DIST_FROM_GAME_HEIGHT = 40;
    public static final int HEART_IMAGE_SCALE_DOWN_FACTOR = 75;
    public static final int LIFE_COUNT_X_DIST_FROM_HEART = 35;
    public static final int LIFE_COUNT_Y_DIST_FROM_GAME_HEIGHT = 65;
    public static final int LEVEL_NUM_X_POS = 22;
    public static final int LEVEL_NUM_Y_DIST_FROM_GAME_HEIGHT = 30;
    public static final int POINTS_X_DIST_FROM_SCENE_CENTER = -35;
    public static final int POINTS_Y_DIST_FROM_GAME_HEIGHT = 35;
    public static final int HIGHSCORE_X_DIST_FROM_SCENE_WIDTH = 110;
    public static final int HIGHSCORE_Y_DIST_FROM_GAME_HEIGHT = 35;
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

    public static void createInterfaceAndAddToRoot(Group root, int game_height, int scene_width, int scene_height) {
        createInterfaceBackground(root, game_height, scene_width, scene_height);
        heartImageDisplay = createImageDisplay(root, HEART_IMAGE_X_POS, game_height +
                HEART_IMAGE_Y_DIST_FROM_GAME_HEIGHT, HEART_IMAGE, HEART_IMAGE_SCALE_DOWN_FACTOR);
        lifeCountText = createTextDisplayAndAddToRoot(root, " * 3", heartImageDisplay.getX() +
                LIFE_COUNT_X_DIST_FROM_HEART, game_height + LIFE_COUNT_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        levelNumberDisplay = createTextDisplayAndAddToRoot(root, "LEVEL 1", LEVEL_NUM_X_POS,
                game_height + LEVEL_NUM_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        pointsDisplay = createTextDisplayAndAddToRoot(root, "POINTS\n" + formatPoints(points), scene_width/2 +
                POINTS_X_DIST_FROM_SCENE_CENTER, game_height + POINTS_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        points = 0;
        addHighScoreDisplay(root, game_height, scene_width);
        highscores.addAll(readInHighScores());
    }

    public static void updateLifeCountDisplay(int lives) {
        lifeCountText.setText(" * " + lives);
    }

    public static void updateLevelNumberDisplay(int levelNumber) {
        levelNumberDisplay.setText("LEVEL " + levelNumber);
    }

    public static Text getMenuText() {
        return menuText;
    }

    public static void createStartMenu(Group root) {
        createMenu(root, START_MENU_X_POS, START_MENU_Y_POS, "SPACE INVADERS\nBY PIERCE AND JEFF\n\n\nYOU START WITH 3 LIVES\n\n" +
                "DESTROY ENEMIES TO EARN POINTS\n\nMOVE THE SPACESHIP WITH\nLEFT AND RIGHT KEYS\n\n" +
                "COLLECT POWER UPS: \nSPEED UP\nSTRONGER MISSILES\nMULTIPLE MISSILES\n\nBEAT ALL 3 LEVELS + BOSS LEVEL TO WIN!\n\n\n" +
                "CHEAT CODES:\n1-9   SKIP TO LEVEL\nA,M   DROP POWER UP\nD   DESTROY FIRST ENEMY\n" +
                "L   ADD 1 LIFE\nP   PAUSE\nR   RESET LEVEL\nS   SKIP TO NEXT LEVEL\n\n\n" +
                "PRESS SPACE TO BEGIN");
    }

    public static void removeMenu(Group root) {
        root.getChildren().remove(menuBackground);
        root.getChildren().remove(menuText);
    }

    public static void createGameOverMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, GAMEOVER_MENU_Y_POS, "GAME OVER!\n\n\n" + getYourScoreText() + "PRESS E TO SAVE YOUR SCORE\nAND RESET POINTS"
                + RESTART_AND_CHANGE_LEVEL + collectTopHighScores(NUM_HIGHSCORES_DISPLAYED));
    }

    public static void createLevelIntermissionMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, DEFAULT_MENU_Y_POS, "LEVEL COMPLETE!\n\n\nPRESS S TO ADVANCE" + RESTART_AND_CHANGE_LEVEL);
    }

    public static void createBossLevelMenu(Group root) {
        createMenu(root, BOSS_MENU_X_POS, BOSS_MENU_Y_POS, "LEVEL COMPLETE!\n\n\nGET READY FOR THE BOSS ROUND\n\n" +
                "RULES\nSTART WITH 5 LIVES \n\nBOSS HAS:\n10 LIVES\nVULNERABLE STATE\nINVINCIBLE STATE\n" +
                "LASERS: SINGLE DAMAGE\nFIREBALLS: DOUBLE DAMAGE\n\n\nPRESS S TO START ");
    }

    public static void createVictoryMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, VICTORY_MENU_Y_POS, "YOU WIN!\n\n\n" + getYourScoreText() + "PRESS E TO SAVE YOUR SCORE\nAND RESET POINTS"
                + RESTART_AND_CHANGE_LEVEL + collectTopHighScores(NUM_HIGHSCORES_DISPLAYED));
    }

    public static void createRestartOrEndMenu(Group root) {
        createMenu(root, THANKS_MENU_X_POS, THANKS_MENU_Y_POS, "THANKS FOR PLAYING!\n\n\n" + getYourScoreText()
                + "PRESS W TO PLAY AGAIN\n\nPRESS Q TO EXIT GAME" + collectTopHighScores(NUM_HIGHSCORES_DISPLAYED));
    }

    public static void createHighScoreTextField(Group root) {
        highScoreTextField = new TextField("Name");
        highScoreTextField.setPrefWidth(HIGHSCORE_TEXT_FIELD_WIDTH);
        highScoreTextField.setLayoutX(Game.GAME_WIDTH/2 - HIGHSCORE_TEXT_FIELD_WIDTH/2);
        highScoreTextField.setLayoutY(Game.GAME_HEIGHT/2);

        System.out.println(highScoreTextField.getWidth());
        highScoreTextField.setPromptText("Name");
        root.getChildren().add(highScoreTextField);
    }

    public static void storeHighScore(Group root) {
        String name = highScoreTextField.getText();
        name = String.join("", name.split(SCORE_DELIMITER));
        root.getChildren().remove(highScoreTextField);
        highscores.add(name + SCORE_DELIMITER + points);
        updateHighScoreList(highscores);
    }

    public static void updatePointsDisplay(int pointsEarned) {
        points += pointsEarned;
        pointsDisplay.setText("POINTS\n" + formatPoints(points));
    }

    public static void updateHighScoreDisplay() {
        File file = new File(HIGHSCORES_FILE_PATH);
        try {
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                int highScore = Integer.parseInt(myReader.nextLine().split(SCORE_DELIMITER)[1]);
                highScoreDisplay.setText("HIGHSCORE\n" + formatPoints(highScore));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading high scores txt file.");
            e.printStackTrace();
        }
    }

    public static void resetPointsDisplay() {
        points = 0;
        pointsDisplay.setText("POINTS\n" + formatPoints(0));
    }

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
        return "YOUR SCORE\n" + points + "\n\n";
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
            System.out.println("An error occurred while reading high scores txt file.");
            e.printStackTrace();
        }
        return highscores;
    }

    private static String collectTopHighScores(int maxNumberOfHighScores) {
        if (highscores.size() == 0) return "";
        String highscoresChart = "\n\n\nHIGH SCORES:";
        int scoresCharted = 0;
        for (String highscore : highscores) {
            if (scoresCharted >= maxNumberOfHighScores) break;
            scoresCharted++;
            highscoresChart += "\n" + highscore.split(SCORE_DELIMITER)[0] + "   " + highscore.split(SCORE_DELIMITER)[1];
        }
        return highscoresChart;
    }

    private static void addHighScoreDisplay(Group root, double game_height, double scene_width) {
        highScoreDisplay = createTextDisplayAndAddToRoot(root, "HIGHSCORE\n" + formatPoints(0), scene_width -
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
            System.out.println("An error occurred while writing to high scores txt file.");
            e.printStackTrace();
        }
    }
}
