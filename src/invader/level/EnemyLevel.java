package invader.level;

import invader.Game;
import invader.StatusDisplay;
import invader.entity.Enemy;
import invader.entity.Entity;
import invader.entity.Spaceship;
import invader.powerup.BurstFirePowerUp;
import invader.powerup.MissilePowerUp;
import invader.powerup.PowerUp;
import invader.powerup.SpaceshipSpeedPowerUp;
import javafx.scene.Group;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Pierce Forte
 * @author Jeff Kim
 * Specific class that inherits from the abstract class Level
 * Sets up the game settings for an enemy level.
 */

public class EnemyLevel extends Level {
    public static final double ENEMY_SPACING = 10;
    public static final int ENEMIES_PER_ROW = 9;
    public static final int ENEMY_SPEED_FACTOR_BY_LEVEL = 10;
    public static final int ENEMY_LASER_ROTATION = 0;
    public static final double DECREASE_TIME_BETWEEN_SHOTS_QUOTIENT = 30;
    public static final double PERCENT_ENEMIES_WITH_EACH_POWERUP = 0.10;
    public static final List<Class> POWER_UP_TYPES = List.of(BurstFirePowerUp.class, MissilePowerUp.class, SpaceshipSpeedPowerUp.class);
    public static final int NUM_POWER_UP_TYPES = POWER_UP_TYPES.size();

    private int curCheatKeyPowerUpIdNumber = 0;

    private int rows;
    private int numEnemies;

    private List<List<Integer>> enemyIdentifiers = new ArrayList<>();
    private List<List<Enemy>> enemies;
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<List<Class>> powerUpGrid;

    public EnemyLevel(Group root, int levelNumber, Game myGame){
        super(root, levelNumber, myGame);
    }

    @Override
    public void clearLevel() {
        clearNodesFromSceneAndLevel(spaceship);
        clearNodesFromSceneAndLevel(evilEntityProjectiles);
        clearNodesFromSceneAndLevel(spaceshipProjectiles);
        clearNodesFromSceneAndLevel(powerUps);
        clear2dNodesFromSceneAndLevel(enemies);
    }

    @Override
    public void addEntitiesToScene() {
        for (List<Enemy> enemyRow : enemies) root.getChildren().addAll(enemyRow);
        spaceship = new Spaceship(Spaceship.DEFAULT_X_POS, Spaceship.DEFAULT_Y_POS);
        root.getChildren().add(spaceship);
    }

    @Override
    public void handleEntitiesAndLasers(double gameTimer, double elapsedTime) {
        updateNodePositionsOnStep(elapsedTime);
        handleEvilEntitiesMovement();
        handleEvilEntityLasers(gameTimer);
        handleSpaceshipProjectiles();
        handlePowerUps(gameTimer);
        attemptLevelVictory();
    }

    @Override
    public void attemptLevelVictory() {
        if(!levelLost && enemies.isEmpty()) {
            initiateLevelVictory();
        }
    }

    @Override
    public void addRandomPowerUp(double gameTimer) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, NUM_POWER_UP_TYPES);
        Class powerUpClass = POWER_UP_TYPES.get(randomIndex);
        PowerUp powerUp = createPowerUpFromClassName(powerUpClass, Game.GAME_WIDTH/2,Game.GAME_HEIGHT/2,
                PowerUp.CHEAT_POWER_UP_IDENTIFIER + curCheatKeyPowerUpIdNumber);
        addCheatPowerUp(gameTimer, powerUp);
    }

    @Override
    public void addSpeedPowerUp(double gameTimer) {
        PowerUp powerUp = new SpaceshipSpeedPowerUp(Game.GAME_WIDTH/2, Game.GAME_HEIGHT/2,
                PowerUp.CHEAT_POWER_UP_IDENTIFIER + curCheatKeyPowerUpIdNumber);
        addCheatPowerUp(gameTimer, powerUp);
    }

    @Override
    public void addMissilePowerUp(double gameTimer) {
        PowerUp powerUp = new MissilePowerUp(Game.GAME_WIDTH/2, Game.GAME_HEIGHT/2,
                PowerUp.CHEAT_POWER_UP_IDENTIFIER + curCheatKeyPowerUpIdNumber);
        addCheatPowerUp(gameTimer, powerUp);
    }

    @Override
    public void addBurstFirePowerUp(double gameTimer) {
        PowerUp powerUp = new BurstFirePowerUp(Game.GAME_WIDTH/2, Game.GAME_HEIGHT/2,
                PowerUp.CHEAT_POWER_UP_IDENTIFIER + curCheatKeyPowerUpIdNumber);
        addCheatPowerUp(gameTimer, powerUp);
    }

    @Override
    public void destroyFirstEnemy() {
        Enemy firstEnemy = enemies.get(enemies.size()-1).get(0);
        removeInactiveEnemies(List.of(firstEnemy));
    }

    @Override
    protected void updateNodePositionsOnStep(double elapsedTime) {
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                enemy.updatePositionOnStep(elapsedTime);
            }
        }
        for (PowerUp powerUp: powerUps) powerUp.updatePositionOnStep(elapsedTime);
        updateProjectilePositionsOnStep(elapsedTime, evilEntityProjectiles);
        updateProjectilePositionsOnStep(elapsedTime, spaceshipProjectiles);
    }

    @Override
    protected void handleEvilEntitiesMovement() {
        boolean reverseMovement = false;
        for (List<Enemy> enemyRow : enemies) {
            if (enemyRow.get(0).isOutOfXBounds() || enemyRow.get(enemyRow.size()-1).isOutOfXBounds()) {
                reverseMovement = true;
                break;
            }
        }
        if (reverseMovement) {
            for (List<Enemy> enemyRow : enemies) {
                for (Enemy enemy : enemyRow) {
                    enemy.reverseXDirection();
                }
            }
        }
    }

    @Override
    protected void handleEvilEntityLasers(double gameTimer) {
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                attemptProjectileFire(gameTimer, enemy, evilEntityProjectiles, ENEMY_LASER_ROTATION);
                updateTimeBetweenEnemyShots(enemy);
            }
        }
        handleProjectileCollisionWithSpaceship(evilEntityProjectiles, spaceship);
    }

    @Override
    protected void handleSpaceshipProjectiles() {
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                boolean isCollision = handleProjectileCollisions(spaceshipProjectiles, enemy);
                if (enemy.getLives() <= 0) {
                    enemiesToRemove.add(enemy);
                    attemptToAddPowerUp(enemy);
                } else if (isCollision){
                    enemy.setImage(enemy.makeImage("enemy" + enemy.getLives() + ".png"));
                }
            }
        }
        removeInactiveEnemies(enemiesToRemove);
    }

    @Override
    protected void createEvilEntities() {
        createPowerUpGrid();
        enemies = new ArrayList<>();
        // get height of first enemy row to ensure they are centered
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int row = 0; row < enemyIdentifiers.size(); row++) {
            List<Enemy> tempRow = new ArrayList<>();
            double xPos = (Game.GAME_WIDTH - enemyIdentifiers.get(0).size() * (ENEMY_SPACING + Enemy.WIDTH) - ENEMY_SPACING)/2;
            for (int col = 0; col < enemyIdentifiers.get(0).size(); col++) {
                Enemy curEnemy = createEnemy(row, col, xPos, yPos);
                tempRow.add(curEnemy);
                xPos += Enemy.WIDTH + ENEMY_SPACING;
            }
            yPos += Enemy.HEIGHT;
            enemies.add(tempRow);
        }
    }

    @Override
    protected void handleFileLines(Scanner myReader) {
        enemyIdentifiers = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String [] cleanRow = data.split(",");
            List<Integer> row = new ArrayList<>();
            for (String brick : cleanRow) {
                row.add(Integer.parseInt(brick));
            }
            enemyIdentifiers.add(row);
        }
        rows = this.enemyIdentifiers.size();
        numEnemies = rows * ENEMIES_PER_ROW;
    }

    private Enemy createEnemy(int row, int col, double xPos, double yPos) {
        PowerUp curPowerUp = null;
        int lives = enemyIdentifiers.get(row).get(col);
        if (powerUpGrid.get(row).get(col) != null) {
            curPowerUp = createPowerUpFromClassName(powerUpGrid.get(row).get(col),
                    xPos + Enemy.WIDTH/2, yPos, PowerUp.ENEMY_POWERUP_IDENTIFIER + col + row*ENEMIES_PER_ROW);
        }
        Enemy curEnemy = new Enemy(xPos, yPos, ENEMY_SPEED_FACTOR_BY_LEVEL*levelNumber,
                Enemy.DEFAULT_Y_SPEED, Math.abs(lives), col + row*ENEMIES_PER_ROW, curPowerUp);
        if (lives < 0) curEnemy.setHasBurstFire(true);
        return curEnemy;
    }

    private void handlePowerUps(double gameTimer) {
        List<PowerUp> powerUpsToRemoveFromScene = new ArrayList<>();
        List<PowerUp> powerUpsToRemoveFromGame = new ArrayList<>();
        for (PowerUp powerUp: powerUps) {
            if (powerUp.hasBeenActivated()) {
                if (!powerUp.updateAndGetActiveStatus(gameTimer, spaceship)) {
                    powerUp.deactivate(gameTimer, spaceship);
                    powerUpsToRemoveFromGame.add(powerUp);
                }
            }
            else if (powerUp.intersects(spaceship) || powerUp.isOutOfYBounds()) {
                if (powerUp.intersects(spaceship)) {
                    powerUp.activate(gameTimer, spaceship);
                }
                else {
                    powerUpsToRemoveFromGame.add(powerUp);
                }
                powerUpsToRemoveFromScene.add(powerUp);
            }
        }
        root.getChildren().removeAll(powerUpsToRemoveFromScene);
        powerUps.removeAll(powerUpsToRemoveFromGame);
    }

    private void attemptToAddPowerUp(Enemy enemy) {
        if (enemy.hasPowerUp()) {
            enemy.getPowerUp().setX(enemy.getX());
            enemy.getPowerUp().setY(enemy.getY());
            powerUps.add(enemy.getPowerUp());
            root.getChildren().add(enemy.getPowerUp());
        }
    }

    private void removeInactiveEnemies(List<Enemy> enemiesToRemove) {
        root.getChildren().removeAll(enemiesToRemove);
        for(List<Enemy> enemyRow : enemies) {
            enemyRow.removeAll(enemiesToRemove);
        }
        List<List<Enemy>> enemyRowsToRemove = new ArrayList<>();
        for(List<Enemy> enemyRow : enemies) {
            if (enemyRow.isEmpty()) enemyRowsToRemove.add(enemyRow);
        }
        enemies.removeAll(enemyRowsToRemove);
    }

    private void addCheatPowerUp(double gameTimer, PowerUp powerUp) {
        curCheatKeyPowerUpIdNumber++;
        powerUp.setTimeActive(gameTimer);
        powerUps.add(powerUp);
        root.getChildren().add(powerUp);
    }

    private void updateTimeBetweenEnemyShots(Enemy enemy) {
        int enemiesLeft = 0;
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy e : enemyRow) {
                enemiesLeft++;
            }
        }
        double changedTime = Enemy.DEFAULT_TIME_BETWEEN_SHOTS * enemiesLeft * DECREASE_TIME_BETWEEN_SHOTS_QUOTIENT;
        // adjust new time between shot so it is between bounds
        double newTime = Math.min(Math.max(changedTime, Enemy.MIN_TIME_BETWEEN_SHOTS), Enemy.DEFAULT_TIME_BETWEEN_SHOTS);
        enemy.setTimeBetweenShots(newTime);
    }

    private void createPowerUpGrid() {
        powerUpGrid = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            powerUpGrid.add(new ArrayList<>());
            for (int col = 0; col < ENEMIES_PER_ROW; col++) {
                powerUpGrid.get(row).add(null);
            }
        }
        List<Integer> enemyIndexes = IntStream.range(0, numEnemies).boxed().collect(Collectors.toList());
        int numOfEachPowerUpType = (int) (numEnemies * PERCENT_ENEMIES_WITH_EACH_POWERUP);
        Collections.shuffle(enemyIndexes);
        int curPowerUpIndex = 0;
        for (Class powerUpClass : POWER_UP_TYPES) {
            for (int numOfCurPowerUpType = 0; numOfCurPowerUpType < numOfEachPowerUpType; numOfCurPowerUpType++) {
                int curEnemyIndex = enemyIndexes.get(curPowerUpIndex);
                curPowerUpIndex++;
                powerUpGrid.get(curEnemyIndex / ENEMIES_PER_ROW).set(curEnemyIndex%ENEMIES_PER_ROW, powerUpClass);
            }
        }
    }

    private PowerUp createPowerUpFromClassName(Class powerUpClass, double xPos, double yPos, String idName) {
        try {
            Constructor<?> constructor = powerUpClass.getConstructor(double.class, double.class, String.class);
            PowerUp powerUp = (PowerUp) constructor.newInstance(xPos, yPos, idName);
            return powerUp;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            StatusDisplay.logError(e);
        }
        return null;
    }
}
