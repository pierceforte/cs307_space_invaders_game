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

import java.io.InputStream;

public class LevelStatsDisplay {

    public static final Paint MENU_BACKGROUND = Color.GRAY;
    public static final Paint INTERFACE_BACKGROUND = Color.GRAY;
    public static final String HEART_IMAGE = "heart.png";

    private static Rectangle menuBackground;
    private static Rectangle userInterfaceArea;
    private static ImageView heart;
    private static Text menuText;
    private static Text lifeCountText;
    private static Text levelNumberDisplay;
    private static Text pointsDisplay;
    private static int points;

    /*
    public static void createStartMenu(Group root) {
        createMenu(root, 50, 30, "BREAKOUT BY PIERCE FORTE\n\n\nDIRECTIONS\n\nBREAK BRICKS TO EARN POINTS\n\nBE CAREFUL!\n" +
                "YOU START WITH 3 LIVES\n\nRELEASE THE BALL WITH THE SPACE BAR\n\nMOVE THE PADDLES WITH\nTHE LEFT AND RIGHT KEYS\n\n" +
                "COLLECT POWER UPS TO HELP\n(OR HURT!) YOU\n\nBEAT ALL 4 LEVELS TO WIN!\n\n\nCHEAT CODES\n\n1-9   SKIP TO LEVEL\n\nJ   SKIP " +
                "TO NEXT LEVEL\n\nR   RESET LEVEL\n\nL   ADD 1 LIFE\n\nC   RESET BALL AND PADDLE\n(HELPFUL WHEN ENCOUNTERING BUGS)\n\n\nPRESS SPACE TO BEGIN");
    }

    public static void removeMenu(Group root) {
        root.getChildren().removeAll(menuBackground, menuText);
    }

    public static void createGameOverMenu(Group root) {
        createMenu(root, 50, 275, "GAME OVER!\n\n\nPRESS C TO RETRY WITH ANOTHER LIFE\n\nPRESS R TO RESTART LEVEL \n\nPRESS 1-9 TO CHANGE LEVEL");
    }

    public static void createLevelIntermissionMenu(Group root) {
        createMenu(root, 100, 300, "LEVEL COMPLETE!\n\n\nPRESS SPACE TO ADVANCE");
    }
    public static void createVictoryMenu(Group root) {
        createMenu(root, 100, 300, "YOU WIN!\n\n\nUSE CHEAT CODES TO CONTINUE");
    }*/

    public static void createInterfaceAndAddToRoot(Group root, int game_height, int scene_width, int scene_height, Level currentLevel, InputStream heartInputStream) {
        createInterfaceBackground(root, game_height, scene_width, scene_height);
        createHeartImageDisplay(root, game_height, heartInputStream);
        //lifeCountText = createTextDisplayAndAddToRoot(root, " * " + currentLevel.getLives(), heart.getX() + 35, game_height + 45, Color.MAROON);
        //levelNumberDisplay = createTextDisplayAndAddToRoot(root, "LEVEL " + currentLevel.getLevelNumber(), scene_width - 90, game_height + 45, Color.MAROON);
        lifeCountText = createTextDisplayAndAddToRoot(root, " * 3", heart.getX() + 35, game_height + 45, Color.MAROON);
        levelNumberDisplay = createTextDisplayAndAddToRoot(root, "LEVEL 1", scene_width - 90, game_height + 45, Color.MAROON);
        pointsDisplay = createTextDisplayAndAddToRoot(root, "POINTS\n" + formatPoints(points), scene_width/2 - 35, game_height + 35, Color.MAROON);
        points = 0;
    }

    private static void createMenu(Group root, double xPos, double yPos, String text) {
        menuBackground = new Rectangle(0, 0, Main.SCENE_WIDTH, Main.SCENE_HEIGHT);
        menuBackground.setFill(MENU_BACKGROUND);
        root.getChildren().add(menuBackground);
        menuText = createTextDisplayAndAddToRoot(root, text, xPos, yPos, Color.MAROON);
        menuText.setTextAlignment(TextAlignment.CENTER);
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

    private static void createHeartImageDisplay(Group root, int game_height, InputStream heartInputStream) {
        Image heartImage = new Image(heartInputStream);
        heart = new ImageView(heartImage);
        heart.setX(15);
        heart.setY(game_height + 20);
        // not sure what is the most "flexible" way to do this while preserving aspect ratio
        heart.setFitHeight(heartImage.getHeight()/75);
        heart.setFitWidth(heartImage.getWidth()/75);
        root.getChildren().add(heart);
    }

    private static Text createTextDisplayAndAddToRoot(Group root, String text, double xPos, double yPos, Paint color) {
        Text tempDisplay = createTextDisplay(root, text, xPos, yPos, color);
        root.getChildren().add(tempDisplay);
        return tempDisplay;
    }

    private static Text createTextDisplay(Group root, String text, double xPos, double yPos, Paint color) {
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
}
