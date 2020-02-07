package invader;

import invader.entity.Enemy;
import invader.entity.Entity;
import invader.entity.Spaceship;
import invader.projectile.Laser;

import javafx.scene.Group;
import javafx.scene.Node;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {

    public static final double ENEMY_SPACING = 10;
    public static final int ENEMIES_PER_ROW = 9;

    private int curSpaceshipLaserIdNumber = 0;
    private int curEnemyLaserIdNumber = 0;

    private int levelNumber;
    private int rows;
    private Spaceship spaceship;
    private List<Laser> spaceshipLasers = new ArrayList<>();
    private List<List<Integer>> enemyIdentifiers = new ArrayList<>();
    private List<List<Enemy>> enemies = new ArrayList<>();
    private List<Laser> enemyLasers = new ArrayList<>();

    public Level(String levelFile, int levelNumber) {
        readFile(levelFile);
        this.levelNumber = levelNumber;
        createEnemies();
    }

    public void addEnemiesAndSpaceshipToScene(Group root) {
        for (List<Enemy> enemyRow : enemies) root.getChildren().addAll(enemyRow);
        spaceship = new Spaceship(Spaceship.DEFAULT_X_POS, Spaceship.DEFAULT_Y_POS);
        root.getChildren().add(spaceship);
    }

    public List<List<Enemy>> getEnemies() {
        return enemies;
    }

    public int getNumberOfRows() {
        return rows;
    }

    public void handleEntitiesAndLasers(Group root, double gameTimer, double elapsedTime) {
        handleEnemyLasers(root, gameTimer, elapsedTime);
        handleSpaceshipLasers(root, elapsedTime);
    }

    public void attemptSpaceshipFire(Group root, double gameTimer) {
        attemptLaserFire(root, gameTimer, spaceship, spaceshipLasers, 1);
    }

    private void attemptLaserFire(Group root, double gameTimer, Entity entity, List<Laser> lasers, double timeBeforeNextShot) {
        if (gameTimer >= entity.getStartShootingTime()) {
            shootLaser(root, entity, lasers, timeBeforeNextShot);
        }
    }

    private void handleEnemyLasers(Group root, double gameTimer, double elapsedTime) {
        updateLaserPositionsOnStep(elapsedTime, enemyLasers);
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                attemptLaserFire(root, gameTimer, enemy, enemyLasers, 50);
            }
        }
        handleLaserCollisions(root, elapsedTime, enemyLasers, spaceship);
    }

    private void handleSpaceshipLasers(Group root, double elapsedTime) {
        updateLaserPositionsOnStep(elapsedTime, spaceshipLasers);
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                enemiesToRemove.add((Enemy) handleLaserCollisions(root, elapsedTime, spaceshipLasers, enemy));
            }
        }
        root.getChildren().removeAll(enemiesToRemove);
        for(List<Enemy> enemyRow : enemies) {
            enemyRow.removeAll(enemiesToRemove);
        }
    }

    private Entity handleLaserCollisions(Group root, double elapsedTime, List<Laser> lasers, Entity entity) {
        List<Laser> lasersToRemove = new ArrayList<>();
        boolean removeEntity = false;
        for (Laser laser : lasers) {
            if (didCollide(laser, entity) || laser.isOutOfBounds()) {
                lasersToRemove.add(laser);
                if (didCollide(laser, entity)) {
                    removeEntity = true;
                }
            }
        }
        lasers.removeAll(lasersToRemove);
        root.getChildren().removeAll(lasersToRemove);
        return removeEntity ? entity : null;
    }

    private boolean didCollide(Laser laser, Node node) {
        return node.getBoundsInParent().intersects(laser.getBoundsInLocal());
    }

    private void updateLaserPositionsOnStep(double elapsedTime, List<Laser> lasers) {
        for (Laser laser : lasers) {
            laser.updatePositionOnStep(elapsedTime);
        }
    }

    private void shootLaser(Group root, Entity entityShooting, List<Laser> lasers, double timeBeforeNextShot) {
        boolean isEnemy = entityShooting.getClass() == Enemy.class;
        Laser laser = new Laser(entityShooting.getX() + Entity.NON_BOSS_WIDTH/2,
                entityShooting.getY(), isEnemy, isEnemy ? curEnemyLaserIdNumber++ : curSpaceshipLaserIdNumber++);
        lasers.add(laser);
        root.getChildren().add(laser);
        entityShooting.addToStartShootingTime(timeBeforeNextShot);
    }

    private void createEnemies() {
        // get height of first brick row to ensure they are centered
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int i = 0; i < enemyIdentifiers.size(); i++) {
            List<Enemy> tempRow = new ArrayList<>();
            double xPos = (Game.GAME_WIDTH - enemyIdentifiers.get(0).size() * (ENEMY_SPACING + Enemy.WIDTH) - ENEMY_SPACING)/2;
            for (int j = 0; j < enemyIdentifiers.get(0).size(); j++) {
                Enemy curEnemy = new Enemy(xPos, yPos, enemyIdentifiers.get(i).get(j), j + i*Level.ENEMIES_PER_ROW);
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

    public void moveSpaceship(boolean toRight) {
        spaceship.setX(spaceship.getX() + Spaceship.X_SPEED_ON_KEY_PRESS * (toRight ? 1 : -1));
        spaceship.wrap();
    }

    private void readFile(String levelFile) {
        try {
            File file = new File(levelFile);
            Scanner myReader = new Scanner(file);
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
            //assignPowerUpsToBricks();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading level layout txt file: " + levelFile);
            e.printStackTrace();
        }
    }

}
