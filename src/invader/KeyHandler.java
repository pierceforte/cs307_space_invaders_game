package invader;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyHandler {
    public static final int KEY_CODE_1 = 49;
    public static final int KEY_CODE_3 = 51;
    public static final int KEY_CODE_9 = 57;
    public static final int KEY_CODE_TO_LEVEL_CONVERSION = 48;
    public static final List<KeyCode> KEY_CODES_1_THROUGH_9 = List.of(KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4,
            KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9);

    private Map<KeyCode, Runnable> keyToActionMap = new HashMap<>();
    private Game myGame;

    public KeyHandler(Game game) {
        myGame = game;
        initializeKeyToActionMap();
    }

    public void handleInput(KeyCode code) {
        boolean takeInput = true;
        if (myGame.isMenuActive()) {
            takeInput = handleMenuKeyInput(code);
        }
        if (takeInput && keyToActionMap.containsKey(code)) keyToActionMap.get(code).run();
    }

    private boolean handleMenuKeyInput(KeyCode code) {
        if (List.of(KeyCode.ENTER, KeyCode.E, KeyCode.Q, KeyCode.E, KeyCode.W).contains(code)) {
            return true;
        }
        else if (isKeyCodeADigit(code) || List.of(KeyCode.R, KeyCode.S).contains(code)) {
            myGame.setMenuInActive();
            StatusDisplay.removeMenu(myGame.getRoot());
            return true;
        }
        return false;
    }

    private void goToLevel(int levelNumber) {
        Level curLevel = myGame.getCurLevel();
        curLevel.clearLevel();
        myGame.setGameTimer(0);
        if (levelNumber == Game.MAX_LEVEL) myGame.setCurLevel(new BossLevel(myGame.getRoot(), levelNumber, myGame));
        else myGame.setCurLevel(new EnemyLevel(myGame.getRoot(), levelNumber, myGame));
    }

    private boolean isKeyCodeADigit(KeyCode code) {
        return (code.getCode() >= KEY_CODE_1 && code.getCode() <= KEY_CODE_9);
    }

    private void initializeKeyToActionMap() {
        keyToActionMap.put(KeyCode.RIGHT, () -> myGame.getCurLevel().moveSpaceship(true));
        keyToActionMap.put(KeyCode.LEFT, () -> myGame.getCurLevel().moveSpaceship(false));
        keyToActionMap.put(KeyCode.SPACE, () -> myGame.getCurLevel().attemptSpaceshipFire(myGame.getGameTimer()));
        keyToActionMap.put(KeyCode.L, () -> myGame.getCurLevel().addLife());
        keyToActionMap.put(KeyCode.A, () -> myGame.getCurLevel().addPowerUpSpeed(myGame.getGameTimer()));
        keyToActionMap.put(KeyCode.M, () -> myGame.getCurLevel().addPowerUpMissile(myGame.getGameTimer()));
        keyToActionMap.put(KeyCode.R, () -> goToLevel(myGame.getCurLevel().getLevelNumber()));
        keyToActionMap.put(KeyCode.D, () -> myGame.getCurLevel().destroyFirstEnemy());
        keyToActionMap.put(KeyCode.W, () -> resetGame());
        keyToActionMap.put(KeyCode.Q, () -> exitGame());
        keyToActionMap.put(KeyCode.E, () -> createHighScoreTextField());
        keyToActionMap.put(KeyCode.ENTER, () -> handleHighScoreEntry());
        keyToActionMap.put(KeyCode.P, () -> handlePauseRequest());
        keyToActionMap.put(KeyCode.S, () -> attemptLevelSkip());
        for (KeyCode code : KEY_CODES_1_THROUGH_9) {
            keyToActionMap.put(code, () -> {
                int levelNumber = code.getCode() <= KEY_CODE_3 ? code.getCode()-KEY_CODE_TO_LEVEL_CONVERSION : Game.MAX_LEVEL;
                goToLevel(levelNumber);});
        }
    }

    private void handlePauseRequest() {
        if (myGame.getAnimationStatus() == Animation.Status.RUNNING) {
            myGame.pauseAnimation();
        }
        else {
            myGame.playAnimation();
        }
    }

    private void attemptLevelSkip() {
        Level curLevel = myGame.getCurLevel();
        if (curLevel.getLevelNumber() < Game.MAX_LEVEL) goToLevel(curLevel.getLevelNumber()+1);
    }

    private void createHighScoreTextField() {
        if (myGame.isGameOverMenuActive()) {
            myGame.setHighScoreTextFieldActive();
            StatusDisplay.removeMenu(myGame.getRoot());
            StatusDisplay.createHighScoreTextField(myGame.getRoot());
        }
    }

    private void handleHighScoreEntry() {
        if (myGame.isHighScoreTextFieldActive()) {
            StatusDisplay.storeHighScore(myGame.getRoot());
            StatusDisplay.createRestartOrEndMenu(myGame.getRoot());
            myGame.setHighScoreTextFieldInactive();
            myGame.setHighScoreTextFieldInactive();
            myGame.setQuitGameMenuActive();
        }
    }

    private void resetGame() {
        if (myGame.isQuitGameMenuActive()) {
            StatusDisplay.removeMenu(myGame.getRoot());
            myGame.setMenuInActive();
            myGame.setGameOverMenuInactive();
            myGame.setQuitGameMenuInactive();
            StatusDisplay.updateHighScoreDisplay();
            goToLevel(1);
        }
    }

    private void exitGame() {
        if (myGame.isQuitGameMenuActive()) {
            Platform.exit();
            System.exit(0);
        }
    }
}
