package invader;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
    public static final int START_MENU_X_POS = 50;
    public static final int START_MENU_Y_POS = 30;
    public static final int VICTORY_MENU_Y_POS = 300;
    public static final String POINTS_FORMAT = "%06d";
    public static final String HIGHSCORES_FILE_PATH = "resources/highscores.txt";


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
    private static List<String> highscores;

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
        createMenu(root, START_MENU_X_POS, START_MENU_Y_POS, "SPACE INVADERS BY PIERCE FORTE AND JEFF KIM\n\n\nDIRECTIONS\n\nBREAK BRICKS TO EARN POINTS\n\nBE CAREFUL!\n" +
                "YOU START WITH 3 LIVES\n\nRELEASE THE BALL WITH THE SPACE BAR\n\nMOVE THE PADDLES WITH\nTHE LEFT AND RIGHT KEYS\n\n" +
                "COLLECT POWER UPS TO HELP\n(OR HURT!) YOU\n\nBEAT ALL 4 LEVELS TO WIN!\n\n\nCHEAT CODES\n\n1-9   SKIP TO LEVEL\n\nJ   SKIP " +
                "TO NEXT LEVEL\n\nR   RESET LEVEL\n\nL   ADD 1 LIFE\n\nC   RESET BALL AND PADDLE\n(HELPFUL WHEN ENCOUNTERING BUGS)\n\n\nPRESS SPACE TO BEGIN");
    }

    public static void removeMenu(Group root) {
        root.getChildren().remove(menuBackground);
        root.getChildren().remove(menuText);
    }

    public static void createGameOverMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, DEFAULT_MENU_Y_POS, "GAME OVER!\n\n\nPRESS E TO SAVE YOUR SCORE\nAND RESET POINTS"
                + RESTART_AND_CHANGE_LEVEL);
    }

    public static void createLevelIntermissionMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, DEFAULT_MENU_Y_POS, "LEVEL COMPLETE!\n\n\nPRESS S TO ADVANCE" + RESTART_AND_CHANGE_LEVEL);
    }

    public static void createVictoryMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, VICTORY_MENU_Y_POS, "YOU WIN!\n\n\nPRESS E TO SAVE YOUR SCORE\nAND RESET POINTS"
                + RESTART_AND_CHANGE_LEVEL);
    }

    public static void createRestartOrEndMenu(Group root) {
        createMenu(root, DEFAULT_MENU_X_POS, VICTORY_MENU_Y_POS, "THANKS FOR PLAYING!\n\n\n"
                + "PRESS W TO PLAY AGAIN\n\nPRESS Q TO EXIT GAME");
    }

    public static void createHighScoreTextField(Group root) {
        highScoreTextField = new TextField("Name");
        highScoreTextField.setPrefWidth(150);
        highScoreTextField.setLayoutX(Game.GAME_WIDTH/2 - 100/2);
        highScoreTextField.setLayoutY(Game.GAME_HEIGHT/2);

        System.out.println(highScoreTextField.getWidth());
        highScoreTextField.setPromptText("Name");
        root.getChildren().add(highScoreTextField);
    }

    public static void storeHighScore(Group root) {
        String name = highScoreTextField.getText();
        name = String.join("", name.split(":"));
        root.getChildren().remove(highScoreTextField);

        highscores = readInHighScoresAndAddCurrentScore(name, points);
        resetPointsDisplay();
        updateHighScoreList(highscores);
    }

    public static void updatePointsDisplay(int pointsEarned) {
        points += pointsEarned;
        pointsDisplay.setText("POINTS\n" + formatPoints(points));
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

    private static List<String> readInHighScoresAndAddCurrentScore(String name, int points) {
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
        highscores.add(name + ":" + points);
        Collections.sort(highscores, Comparator.comparing((String entry) -> Integer.parseInt(entry.split(":")[1]))
                .reversed()
                .thenComparing((entry) -> entry.toLowerCase()));
        return highscores;
    }

    private static void addHighScoreDisplay(Group root, double game_height, double scene_width) {
        highScoreDisplay = createTextDisplayAndAddToRoot(root, "HIGHSCORE\n" + formatPoints(0), scene_width -
                HIGHSCORE_X_DIST_FROM_SCENE_WIDTH, game_height + HIGHSCORE_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        updateHighScoreDisplay();
    }

    public static void updateHighScoreDisplay() {
        File file = new File(HIGHSCORES_FILE_PATH);
        try {
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                int highScore = Integer.parseInt(myReader.nextLine().split(":")[1]);
                highScoreDisplay.setText("HIGHSCORE\n" + formatPoints(highScore));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading high scores txt file.");
            e.printStackTrace();
        }
    }

    private static void resetPointsDisplay() {
        points = 0;
        pointsDisplay.setText("POINTS\n" + formatPoints(0));
    }

    public static Rectangle getMenuBackground() {
        return menuBackground;
    }

    private static void updateHighScoreList(List<String> highscores) {
        try {
            PrintWriter pw = new PrintWriter(HIGHSCORES_FILE_PATH);
            int numEntries = 0;
            for (String entry : highscores) {
                if (numEntries < 100) {
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
