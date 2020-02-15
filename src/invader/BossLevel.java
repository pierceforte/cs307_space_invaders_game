package invader;

import invader.entity.Boss;
import invader.entity.Enemy;
import invader.entity.Spaceship;
import javafx.scene.Group;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BossLevel extends Level {

    private Boss boss;
    private int bossLives;
    private int curBossLaserIdNumber = 0;

    public BossLevel(Group root, int levelNumber, Game myGame){
        super(root, levelNumber, myGame);
    }

    @Override
    public void clearLevel() {
        clearNodesFromSceneAndLevel(spaceship);
        clearNodesFromSceneAndLevel(boss);
        clearNodesFromSceneAndLevel(evilEntityLasers);
        clearNodesFromSceneAndLevel(spaceshipLasers);
    }

    @Override
    public void addEntitiesToScene() {
        root.getChildren().add(boss);
        spaceship = new Spaceship(Spaceship.DEFAULT_X_POS, Spaceship.DEFAULT_Y_POS);
        root.getChildren().add(spaceship);
    }

    @Override
    protected void createEvilEntities() {
        boss = new Boss(Game.GAME_WIDTH/2 - Boss.DEFAULT_WIDTH /2, Game.GAME_HEIGHT/2 - Boss.DEFAULT_HEIGHT /2,
                Boss.DEFAULT_SPEED, Boss.DEFAULT_SPEED, bossLives);
    }

    @Override
    public void handleEntitiesAndLasers(double gameTimer, double elapsedTime) {
        updateNodePositionsOnStep(elapsedTime);
        handleEvilEntitiesMovement();
        handleEvilEntityLasers(gameTimer);
        handleSpaceshipLasers();
        attemptLevelVictory();
    }

    @Override
    public void attemptLevelVictory() {
        if(!levelLost && boss.getLives() == 0) {
            initiateLevelVictory();
        }
    }

    @Override
    protected void updateNodePositionsOnStep(double elapsedTime) {
        boss.updatePositionOnStep(elapsedTime);
        updateLaserPositionsOnStep(elapsedTime, evilEntityLasers);
        updateLaserPositionsOnStep(elapsedTime, spaceshipLasers);
    }

    @Override
    public List<List<Enemy>> getEvilEntities() {
        return null;
    }

    @Override
    protected void handleEvilEntitiesMovement() {
        if (boss.isOutOfXBounds()) {
            boss.reverseXDirection();
            boss.setRandomXSpeed();
        }
        if (boss.isOutOfYBounds()) {
            boss.reverseYDirection();
            boss.setRandomYSpeed();
        }
    }

    @Override
    protected void handleEvilEntityLasers(double gameTimer) {
        attemptLaserFire(gameTimer, boss, evilEntityLasers, Boss.TIME_BETWEEN_SHOTS, curBossLaserIdNumber);
        handleLaserCollisionWithSpaceship(evilEntityLasers, spaceship);
    }

    @Override
    protected void handleSpaceshipLasers() {
        Boss bossCollision = (Boss) handleLaserCollisions(spaceshipLasers, boss);
        if (bossCollision != null) {
            boss.lowerLives();
            if (boss.getLives() == 0) clearNodesFromSceneAndLevel(boss);
        }
    }

    @Override
    protected void handleFileLines(Scanner myReader) {
        String data = myReader.nextLine();
        bossLives = Integer.parseInt(data);
    }

    @Override
    public void addPowerUp(double gameTimer) {
        return;
    }

    @Override
    public void destroyFirstEnemy() {
        return;
    }

}
