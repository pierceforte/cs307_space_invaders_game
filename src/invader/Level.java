package invader;

import invader.entity.Enemy;
import invader.entity.Spaceship;
import invader.projectile.Laser;
import javafx.scene.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {

    public static final double ENEMY_SPACING = 10;

    private int levelNumber;
    private int lives;
    private int rows;
    private Spaceship spaceship;
    private List<Laser> spaceshipLasers = new ArrayList<>();
    private List<List<Integer>> enemyIdentifiers = new ArrayList<>();
    private List<List<Enemy>> enemies = new ArrayList<>();
    private List<Laser> enemyLasers = new ArrayList<>();

    public Level(String levelFile, int levelNumber) {
        readFile(levelFile);
        this.levelNumber = levelNumber;
        this.lives = 3;
        createEnemies();
    }


    public void handleEnemyLasers(Group root, double gameTimer, double elapsedTime) {
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                if (gameTimer >= enemy.getStartShootingTime()) {
                    Laser curLaser = new Laser(enemy.getX() + Enemy.WIDTH/2, enemy.getY(), true);
                    enemy.addToStartShootingTime(50);
                    enemyLasers.add(curLaser);
                    root.getChildren().add(curLaser);
                }
            }
        }

        List<Laser> lasersToRemove = new ArrayList<>();
        for (Laser laser : enemyLasers) {
            laser.updatePositionOnStep(elapsedTime);
            if (didCollide(root, laser, spaceship)) {
                lasersToRemove.add(laser);
            }
        }
        enemyLasers.removeAll(lasersToRemove);
        root.getChildren().removeAll(lasersToRemove);
    }

    public void handleSpaceshipLasers(Group root, double elapsedTime) {
        List<Laser> spaceshipLasersToRemove = new ArrayList<>();
        for (Laser laser : spaceshipLasers) {
            laser.updatePositionOnStep(elapsedTime);
            if (didCollide(root, laser, enemies)) {
                spaceshipLasersToRemove.add(laser);
            }
        }
        root.getChildren().removeAll(spaceshipLasersToRemove);
        spaceshipLasers.removeAll(spaceshipLasersToRemove);
    }
    public void handleSpaceshipFire(Group root, double gameTimer) {
        System.out.println(gameTimer + ", " + spaceship.getStartShootingTime());
        if (gameTimer >= spaceship.getStartShootingTime()) {
            spaceship.addToStartShootingTime(1);
            Laser spaceshipLaser = new Laser(spaceship.getX() + Spaceship.WIDTH/2, spaceship.getY(), false);
            spaceshipLasers.add(spaceshipLaser);
            root.getChildren().add(spaceshipLaser);
        }
    }

    public void addEnemiesToSceneAndSpaceship(Group root) {
        for (List<Enemy> enemyRow : enemies) root.getChildren().addAll(enemyRow);
        spaceship = new Spaceship(Game.GAME_WIDTH/2 - Spaceship.WIDTH/2, Game.GAME_HEIGHT - 30);
        root.getChildren().add(spaceship);
    }

    private boolean didCollide(Group root, Laser laser, List<List<Enemy>> enemies) {
        List<Enemy> enemiesToRemove = new ArrayList<>();
        boolean didCollide = false;
        for (List<Enemy> enemyRow : enemies) {
            for (Enemy enemy : enemyRow) {
                if (enemy.getBoundsInParent().intersects(laser.getBoundsInLocal())) {
                    enemiesToRemove.add(enemy);
                    didCollide = true;
                }
            }
        }
        root.getChildren().removeAll(enemiesToRemove);
        for(List<Enemy> e : enemies) {
            e.removeAll(enemiesToRemove);
        }
        return didCollide;
    }

    private boolean didCollide(Group root, Laser laser, Spaceship spaceship) {
        boolean didCollide = false;
        if (spaceship.getBoundsInParent().intersects(laser.getBoundsInLocal())) {
            didCollide = true;
        }
        return didCollide;
    }

    private void createEnemies() {
        // get height of first brick row to ensure they are centered
        //double yPos = Main.GAME_HEIGHT/2.0 - Enemy.ADJUSTED_HEIGHT*rows/2.0;
        double yPos = Game.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int i = 0; i < enemyIdentifiers.size(); i++) {
            List<Enemy> tempRow = new ArrayList<>();
            double xPos = (Game.GAME_WIDTH - enemyIdentifiers.get(0).size() * (ENEMY_SPACING + Enemy.WIDTH) - ENEMY_SPACING)/2;
            for (int j = 0; j < enemyIdentifiers.get(0).size(); j++) {
                Enemy curEnemy = new Enemy(xPos, yPos, enemyIdentifiers.get(i).get(j));
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

    public void moveSpaceshipRight() {
        spaceship.setX(spaceship.getX() + Spaceship.SPACESHIP_SPEED);
    }

    public void moveSpaceshipLeft() {
        spaceship.setX(spaceship.getX() - Spaceship.SPACESHIP_SPEED);
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
