package invader;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class StatusDisplay {

    public static final Paint MENU_BACKGROUND = Color.GRAY;
    public static final Paint INTERFACE_BACKGROUND = Color.GRAY;
    public static final Paint TEXT_COLOR = Color.MAROON;
    public static final String RESTART_AND_CHANGE_LEVEL = "\n\nPRESS R TO RESTART LEVEL\n\nPRESS 1-9 TO CHANGE LEVEL";
    public static final String HEART_IMAGE = "heart.png";
    public static final int HEART_IMAGE_X_POS = 15;
    public static final int HEART_IMAGE_Y_DIST_FROM_GAME_HEIGHT = 20;
    public static final int HEART_IMAGE_SCALE_DOWN_FACTOR = 75;
    public static final int LIFE_COUNT_X_DIST_FROM_HEART = 35;
    public static final int LIFE_COUNT_Y_DIST_FROM_GAME_HEIGHT = 45;
    public static final int LEVEL_NUM_X_DIST_FROM_SCENE_WIDTH = -90;
    public static final int LEVEL_NUM_Y_DIST_FROM_GAME_HEIGHT = 45;
    public static final int POINTS_X_DIST_FROM_SCENE_CENTER = -35;
    public static final int POINTS_Y_DIST_FROM_GAME_HEIGHT = 35;

    private static Rectangle menuBackground;
    private static Rectangle userInterfaceArea;
    private static ImageView heartImageDisplay;


    private static Text menuText;
    private static Text lifeCountText;
    private static Text levelNumberDisplay;
    private static Text pointsDisplay;
    private static int points;

    public static void createInterfaceAndAddToRoot(Group root, int game_height, int scene_width, int scene_height) {
        createInterfaceBackground(root, game_height, scene_width, scene_height);
        heartImageDisplay = createImageDisplay(root, HEART_IMAGE_X_POS, game_height +
                HEART_IMAGE_Y_DIST_FROM_GAME_HEIGHT, HEART_IMAGE, HEART_IMAGE_SCALE_DOWN_FACTOR);
        lifeCountText = createTextDisplayAndAddToRoot(root, " * 3", heartImageDisplay.getX() +
                LIFE_COUNT_X_DIST_FROM_HEART, game_height + LIFE_COUNT_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        levelNumberDisplay = createTextDisplayAndAddToRoot(root, "LEVEL 1", scene_width +
                LEVEL_NUM_X_DIST_FROM_SCENE_WIDTH, game_height + LEVEL_NUM_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        pointsDisplay = createTextDisplayAndAddToRoot(root, "POINTS\n" + formatPoints(points), scene_width/2 +
                POINTS_X_DIST_FROM_SCENE_CENTER, game_height + POINTS_Y_DIST_FROM_GAME_HEIGHT, TEXT_COLOR);
        points = 0;
    }

    public static void updateLifeCountDisplay(int lives) {
        lifeCountText.setText(" * " + lives);
    }

    public static void updateLevelNumberDisplay(int levelNumber) {
        levelNumberDisplay.setText("LEVEL " + levelNumber);
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
        tempDisplay.setFont(Font.font ("Verdana", 15));
        tempDisplay.setX(xPos);
        tempDisplay.setY(yPos);
        tempDisplay.setFill(color);
        return tempDisplay;
    }

    private static String formatPoints(int points) {
        return String.format("%06d", points);
    }


    private static void createMenu(Group root, double xPos, double yPos, String text) {
        menuBackground = new Rectangle(0, 0, Game.SCENE_WIDTH, Game.SCENE_HEIGHT);
        menuBackground.setFill(MENU_BACKGROUND);
        root.getChildren().add(menuBackground);
        menuText = createTextDisplayAndAddToRoot(root, text, xPos, yPos, Color.MAROON);
        menuText.setTextAlignment(TextAlignment.CENTER);
    }

    public static void createStartMenu(Group root) {
        createMenu(root, 50, 30, "BREAKOUT BY PIERCE FORTE\n\n\nDIRECTIONS\n\nBREAK BRICKS TO EARN POINTS\n\nBE CAREFUL!\n" +
                "YOU START WITH 3 LIVES\n\nRELEASE THE BALL WITH THE SPACE BAR\n\nMOVE THE PADDLES WITH\nTHE LEFT AND RIGHT KEYS\n\n" +
                "COLLECT POWER UPS TO HELP\n(OR HURT!) YOU\n\nBEAT ALL 4 LEVELS TO WIN!\n\n\nCHEAT CODES\n\n1-9   SKIP TO LEVEL\n\nJ   SKIP " +
                "TO NEXT LEVEL\n\nR   RESET LEVEL\n\nL   ADD 1 LIFE\n\nC   RESET BALL AND PADDLE\n(HELPFUL WHEN ENCOUNTERING BUGS)\n\n\nPRESS SPACE TO BEGIN");
    }

    public static void removeMenu(Group root) {
        root.getChildren().remove(menuBackground);
        root.getChildren().remove(menuText);
    }

    public static void createGameOverMenu(Group root) {
        createMenu(root, 85, 275, "GAME OVER!\n" + RESTART_AND_CHANGE_LEVEL);
    }

    public static void createLevelIntermissionMenu(Group root) {
        createMenu(root, 85, 275, "LEVEL COMPLETE!\n\n\nPRESS S TO ADVANCE" + RESTART_AND_CHANGE_LEVEL);
    }
    public static void createVictoryMenu(Group root) {
        createMenu(root, 85, 300, "YOU WIN!\n" + RESTART_AND_CHANGE_LEVEL);
        //resetPointsDisplay();
    }

    private static void resetPointsDisplay() {
        //pointsDisplay.setText("POINTS\n" + formatPoints(0));
    }

    public static Rectangle getMenuBackground() {
        return menuBackground;
    }

    public static Text getMenuText() {
        return menuText;
    }
}
