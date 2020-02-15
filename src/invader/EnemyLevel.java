package invader;


import invader.entity.Enemy;
import invader.entity.Entity;
import invader.entity.Spaceship;
import invader.powerup.PowerUp;
import invader.powerup.SpaceshipSpeedPowerUp;
import invader.projectile.Laser;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnemyLevel extends Level {
    public static final double ENEMY_SPACING = 10;
    public static final int ENEMIES_PER_ROW = 9;
    public static final int POINTS_PER_ENEMY_HIT = 25;
    public static final int ENEMY_SPEED_FACTOR_BY_LEVEL = 10;

    private int curEnemyLaserIdNumber = 0;
    private int curCheatKeyPowerUpIdNumber = 0;

    private int rows;

    private List<List<Integer>> enemyIdentifiers = new ArrayList<>();
    private List<List<Enemy>> enemies;
    private List<Laser> enemyLasers = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<List<Integer>> powerUpGrid = new ArrayList<>();
    //private Map<Integer, PowerUp> powerUpIntegerToType = Map.of(1, SpaceshipSpeedPowerUp);

    public EnemyLevel(Group root, int levelNumber, Game myGame){
        super(root, levelNumber, myGame);
    }

    @Override
    public void clearLevel() {
        root.getChildren().remove(spaceship);
        spaceship = null;
        root.getChildren().removeAll(enemyLasers);
        root.getChildren().removeAll(spaceshipLasers);
        root.getChildren().removeAll(powerUps);
        for (List<Enemy> enemyRow : enemies) {
            root.getChildren().removeAll(enemyRow);
        }
        for (List list : List.of(enemies, enemyLasers, spaceshipLasers, powerUps)) {
            list.clear();
        }
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
        handleSpaceshipLasers();
        handlePowerUps(gameTimer);
        attemptLevelVictory();
    }

    @Override
    public void attemptLevelVictory() {
        if(!levelLost && enemies.size() == 0) {
            myGame.setMenuActive();
            clearLevel();
            if (getLevelNumber() == Game.MAX_LEVEL) {
                StatusDisplay.createVictoryMenu(root);
            } else {
                StatusDisplay.createLevelIntermissionMenu(root);
            }
        }
    }

    @Override
    public void addPowerUp(double gameTimer) {
        SpaceshipSpeedPowerUp powerUp = new SpaceshipSpeedPowerUp(Game.GAME_WIDTH/2, Game.GAME_HEIGHT/2,
                "cheatPowerUp" + curCheatKeyPowerUpIdNumber++);
        powerUp.setTimeActive(gameTimer);
        powerUps.add(powerUp);
        root.getChildren().add(powerUp);
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
        updateLaserPositionsOnStep(elapsedTime, enemyLasers);
        updateLaserPositionsOnStep(elapsedTime, spaceshipLasers);
    }

    @Override
    public List<List<Enemy>> getEvilEntities() {
        return enemies;
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
                attemptLaserFire(gameTimer, enemy, enemyLasers, Enemy.TIME_BETWEEN_SHOTS);
            }
        }
        if (handleLaserCollisions(enemyLasers, spaceship) != null) {
            spaceship.lowerLives();
            StatusDisplay.updateLifeCountDisplay(spaceship.getLives());
            if (spaceship.getLives() == 0) {
                levelLost = true;
                myGame.setMenuActive();
                clearLevel();
                StatusDisplay.createGameOverMenu(root);
            }
        }
    }

    @Override
    protected void handleSpaceshipLasers() {
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                Enemy enemyToRemove = (Enemy) handleLaserCollisions(spaceshipLasers, enemy);
                if (enemyToRemove != null) {
                    enemy.lowerLives();
                    if (enemy.getLives() == 0) {
                        enemiesToRemove.add(enemyToRemove);
                        if (enemy.hasPowerUp()) {
                            enemy.getPowerUp().setX(enemy.getX());
                            enemy.getPowerUp().setY(enemy.getY());
                            powerUps.add(enemy.getPowerUp());
                            root.getChildren().add(enemy.getPowerUp());
                        }
                    } else {
                        enemy.setImage(enemy.makeImage("enemy" + enemy.getLives() + ".png"));
                    }
                }
            }
        }
        removeInactiveEnemies(enemiesToRemove);
    }

    @Override
    protected Entity handleLaserCollisions(List<Laser> lasers, Entity entity) {
        List<Laser> lasersToRemove = new ArrayList<>();
        boolean removeEntity = false;
        for (Laser laser : lasers) {
            if (laser.intersects(entity) || laser.isOutOfYBounds()) {
                lasersToRemove.add(laser);
                if (laser.intersects(entity)) {
                    removeEntity = true;
                    if (entity.getClass() == Enemy.class) {
                        StatusDisplay.updatePointsDisplay(POINTS_PER_ENEMY_HIT);
                    }
                }
            }
        }
        lasers.removeAll(lasersToRemove);
        root.getChildren().removeAll(lasersToRemove);
        return removeEntity ? entity : null;
    }

    @Override
    protected void shootLaser(Entity entityShooting, List<Laser> lasers, double timeBeforeNextShot) {
        boolean isEnemy = entityShooting.getClass() == Enemy.class;
        Laser laser = new Laser(entityShooting.getX() + Entity.NON_BOSS_WIDTH/2,
                entityShooting.getY(), isEnemy, isEnemy ? curEnemyLaserIdNumber++ : curSpaceshipLaserIdNumber++);
        lasers.add(laser);
        root.getChildren().add(laser);
        entityShooting.addToStartShootingTime(timeBeforeNextShot);
    }

    @Override
    protected void createEvilEntities() {
        enemies = new ArrayList<>();
        // get height of first brick row to ensure they are centered
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int row = 0; row < enemyIdentifiers.size(); row++) {
            List<Enemy> tempRow = new ArrayList<>();
            double xPos = (Game.GAME_WIDTH - enemyIdentifiers.get(0).size() * (ENEMY_SPACING + Enemy.WIDTH) - ENEMY_SPACING)/2;
            for (int col = 0; col < enemyIdentifiers.get(0).size(); col++) {
                SpaceshipSpeedPowerUp curPowerUp = null;
                if ((col == 1 && row % 2 != 0) || (col == 4 && (row % 2 == 0)) || (col == 7 && row == 3)) {
                    curPowerUp = new SpaceshipSpeedPowerUp(xPos + Enemy.WIDTH/2, yPos,
                            "enemyPowerUp" + col + row*ENEMIES_PER_ROW);
                }
                Enemy curEnemy = new Enemy(xPos, yPos, ENEMY_SPEED_FACTOR_BY_LEVEL*enemyIdentifiers.get(row).get(col),
                        0, enemyIdentifiers.get(row).get(col), col + row*ENEMIES_PER_ROW, curPowerUp);
                /*
                if (!powerUpGrid.get(i*BRICKS_PER_ROW + j).equals("none")) {
                    curBrick.setPowerUp(powerUpGrid.get(i*BRICKS_PER_ROW + j));
                }
                */
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

    private void removeInactiveEnemies(List<Enemy> enemiesToRemove) {
        root.getChildren().removeAll(enemiesToRemove);
        for(List<Enemy> enemyRow : enemies) {
            enemyRow.removeAll(enemiesToRemove);
        }
        List<List<Enemy>> enemyRowsToRemove = new ArrayList<>();
        for(List<Enemy> enemyRow : enemies) {
            if (enemyRow.size() == 0) enemyRowsToRemove.add(enemyRow);
        }
        enemies.removeAll(enemyRowsToRemove);
    }
}
