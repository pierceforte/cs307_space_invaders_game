package invader.level;

import invader.Game;
import invader.StatusDisplay;
import invader.entity.Boss;
import invader.entity.Spaceship;
import invader.projectile.Projectile;
import javafx.scene.Group;

import java.util.Scanner;

/**
 * This class inherits from the abstract class Level, implementing a level with a boss.
 *
 * This class handles the nodes, collisions, and game state while user fights a boss.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */


public class BossLevel extends Level {
    public static final int DEFAULT_SPACESHIP_LIVES = 5;

    private Boss boss;
    private int bossLives;
    private double invulnerableTimer = 0;

    /**
     * Create a boss level
     * @param root: Root of the game
     * @param levelNumber: The level number the game is currently in
     * @param myGame: My game
     */
    public BossLevel(Group root, int levelNumber, Game myGame){
        super(root, levelNumber, myGame);
        spaceship.setLives(DEFAULT_SPACESHIP_LIVES);
        StatusDisplay.updateLifeCountDisplay(DEFAULT_SPACESHIP_LIVES);
    }

    @Override
    public void clearLevel() {
        clearNodesFromSceneAndLevel(spaceship);
        clearNodesFromSceneAndLevel(boss);
        clearNodesFromSceneAndLevel(evilEntityProjectiles);
        clearNodesFromSceneAndLevel(spaceshipProjectiles);
    }

    @Override
    public void addEntitiesToScene() {
        root.getChildren().add(boss);
        spaceship = new Spaceship(Spaceship.DEFAULT_X_POS, Spaceship.DEFAULT_Y_POS);
        root.getChildren().add(spaceship);
    }

    @Override
    public void handleEntitiesAndLasers(double gameTimer, double elapsedTime) {
        updateNodePositionsOnStep(elapsedTime);
        handleEvilEntitiesMovement();
        handleEvilEntityLasers(gameTimer);
        attemptBossFire();
        attemptVulnerabilitySwitch(gameTimer);
        handleSpaceshipProjectiles();
        attemptLevelVictory();
    }

    @Override
    public void attemptLevelVictory() {
        if(!levelLost && boss.getLives() == 0) {
            initiateLevelVictory();
        }
    }

    @Override
    public void addRandomPowerUp(double gameTimer) {
        return;
    }

    @Override
    public void addSpeedPowerUp(double gameTimer) {
        return;
    }

    @Override
    public void addMissilePowerUp(double gameTimer) {
        return;
    }

    @Override
    public void addBurstFirePowerUp(double gameTimer) {
        return;
    }

    @Override
    public void destroyFirstEnemy() {
        boss.setLives(0);
    }

    @Override
    protected void createEvilEntities() {
        boss = new Boss(Game.GAME_WIDTH/2 - Boss.DEFAULT_WIDTH /2, Game.GAME_HEIGHT/2 - Boss.DEFAULT_HEIGHT /2,
                Boss.DEFAULT_SPEED, Boss.DEFAULT_SPEED, bossLives);
    }

    @Override
    protected void updateNodePositionsOnStep(double elapsedTime) {
        boss.updatePositionOnStep(elapsedTime);
        updateProjectilePositionsOnStep(elapsedTime, evilEntityProjectiles);
        updateProjectilePositionsOnStep(elapsedTime, spaceshipProjectiles);
    }

    @Override
    protected void handleEvilEntitiesMovement() {
        updateBossPosition();
    }

    @Override
    protected void handleEvilEntityLasers(double gameTimer) {
        handleProjectileCollisionWithSpaceship(evilEntityProjectiles, spaceship);
        handleEvilEntityProjectileBounds();
    }

    @Override
    protected void handleSpaceshipProjectiles() {
        handleProjectileCollisions(spaceshipProjectiles, boss);
        if (boss.getLives() == 0) clearNodesFromSceneAndLevel(boss);
    }

    @Override
    protected void handleFileLines(Scanner myReader) {
        String data = myReader.nextLine();
        bossLives = Integer.parseInt(data);
    }

    private void updateBossPosition() {
        if (boss.isOutOfXBounds()) {
            boss.reverseXDirection();
            boss.setRandomSpeed();
        }
        if (boss.isOutOfYBounds()) {
            boss.reverseYDirection();
            boss.setRandomSpeed();
        }
    }

    private void attemptVulnerabilitySwitch(double gameTimer) {
        if (gameTimer >= boss.getSwitchVulnerabilityTime()) {
            boss.switchVulnerabilityStatus();
            if (boss.isVulnerable()) {
                boss.setHasBurstFire(true);
                blastFire(boss, evilEntityProjectiles);
                boss.setHasBurstFire(false);
            }
        }
    }

    private void attemptBossFire() {
        if (!boss.isVulnerable()) invulnerableTimer++;
        if (!boss.isVulnerable() && invulnerableTimer >= boss.getStartShootingTime()) {
            shootProjectile(boss, evilEntityProjectiles, Projectile.DEFAULT_PROJECTILE_ROTATION);
        }
    }
}
