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

public abstract class Level {

    public static final String LEVEL_FILE_PATH = "resources/level_files/level_";
    public static final String LEVEL_FILE_EXTENSION = ".txt";
    public static final int SPACESHIP_LASER_ROTATION = 0;

    protected int curSpaceshipLaserIdNumber = 0;

    protected boolean levelLost = false;

    protected Game myGame;
    protected Group root;

    protected int levelNumber;

    protected Spaceship spaceship;
    protected List<Laser> spaceshipLasers = new ArrayList<>();
    protected List<Laser> evilEntityLasers = new ArrayList<>();

    private List<Node> nodes = new ArrayList<>();

    public Level(Group root, int levelNumber, Game myGame){
        this.root = root;
        String levelFile = LEVEL_FILE_PATH + levelNumber + LEVEL_FILE_EXTENSION;
        readFile(levelFile);
        this.levelNumber = levelNumber;
        createEvilEntities();
        addEntitiesToScene();
        this.myGame = myGame;
        StatusDisplay.updateLevelNumberDisplay(levelNumber);
        StatusDisplay.updateLifeCountDisplay(spaceship.getLives());
    }

    public abstract void clearLevel();

    public abstract void addEntitiesToScene();

    public void addLife() {
        spaceship.addLife();
        StatusDisplay.updateLifeCountDisplay(spaceship.getLives());
    }

    public abstract void handleEntitiesAndLasers(double gameTimer, double elapsedTime);

    public abstract void attemptLevelVictory();

    public abstract void addPowerUp(double gameTimer);

    public abstract void destroyFirstEnemy();

    protected abstract void updateNodePositionsOnStep(double elapsedTime);

    protected void updateLaserPositionsOnStep(double elapsedTime, List<Laser> lasers) {
        for (Laser laser : lasers) {
            laser.updatePositionOnStep(elapsedTime);
        }
    }

    protected void handleLaserCollisionWithSpaceship(List<Laser> evilEntityLasers, Spaceship spaceship) {
        if (handleLaserCollisions(evilEntityLasers, spaceship) != null) {
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

    protected Entity handleLaserCollisions(List<Laser> lasers, Entity entity) {
        List<Laser> lasersToRemove = new ArrayList<>();
        boolean removeEntity = false;
        for (Laser laser : lasers) {
            if (laser.intersects(entity) || laser.isOutOfYBounds()) {
                lasersToRemove.add(laser);
                if (laser.intersects(entity)) {
                    removeEntity = true;
                    StatusDisplay.updatePointsDisplay(entity.getPointsPerHit());
                }
            }
        }
        lasers.removeAll(lasersToRemove);
        root.getChildren().removeAll(lasersToRemove);
        return removeEntity ? entity : null;
    }

    protected void attemptSpaceshipFire(double gameTimer) {
        attemptLaserFire(gameTimer, spaceship, spaceshipLasers, 1, SPACESHIP_LASER_ROTATION, curSpaceshipLaserIdNumber);
    }

    protected void attemptLaserFire(double gameTimer, Entity entity, List<Laser> lasers, double timeBeforeNextShot, double rotation,
                                    int idNumber) {
        if (gameTimer >= entity.getStartShootingTime()) {
            shootLaser(entity, lasers, timeBeforeNextShot, rotation, idNumber);
        }
    }

    protected Laser shootLaser(Entity entityShooting, List<Laser> lasers, double timeBeforeNextShot, double rotation, int idNumber) {
        boolean isSpaceship = entityShooting.getClass() == Spaceship.class;
        Laser laser;
        if (isSpaceship) {
            laser = createSpaceshipLaser(spaceship, rotation, idNumber);
        }
        else {
            laser = createEvilEntityLaser(entityShooting, rotation, idNumber);
        }

        lasers.add(laser);
        root.getChildren().add(laser);
        entityShooting.addToStartShootingTime(timeBeforeNextShot);
        return laser;
    }

    protected Laser createSpaceshipLaser(Spaceship spaceship, double rotation, int idNumber) {
        Laser laser = new Laser(spaceship.getX() + spaceship.getFitWidth()/2,
                spaceship.getY(), false, rotation, idNumber++);
        return laser;
    }

    protected abstract Laser createEvilEntityLaser(Entity entityShooting, double rotation, int idNumber);

    protected <T extends Node> void clearNodesFromSceneAndLevel(T node) {
        root.getChildren().remove(node);
        node = null;
    }

    protected <T extends Node> void clearNodesFromSceneAndLevel(List<T> nodes) {
        root.getChildren().removeAll(nodes);
        nodes.clear();
    }

    protected <T extends Node> void clear2dNodesFromSceneAndLevel(List<List<T>> nodes) {
        for (List<T> row : nodes) {
            root.getChildren().removeAll(row);
        }
        nodes.clear();
    }

    protected void clearNodesFromSceneAndLevel(List<Node> nodes, List<Object> list) {
        root.getChildren().removeAll(nodes);
        list.removeAll(nodes);
    }

    /*
    protected void removeNodesFromSceneAndLevel(Node node, List<Object> list) {
        root.getChildren().remove(node);
        list.remove(node);
    }

    protected void removeNodesFromSceneAndLevel(List<Node> nodes, List<Object> list) {
        root.getChildren().removeAll(nodes);
        list.removeAll(nodes);
    }*/

    protected void initiateLevelVictory() {
        myGame.setMenuActive();
        clearLevel();
        if (getLevelNumber() == Game.MAX_LEVEL) {
            StatusDisplay.createVictoryMenu(root);
        } else {
            StatusDisplay.createLevelIntermissionMenu(root);
        }
    }

    protected abstract void handleEvilEntitiesMovement();

    protected abstract void handleEvilEntityLasers(double gameTimer);

    protected abstract void handleSpaceshipLasers();

    protected abstract void createEvilEntities();

    public void moveSpaceship(boolean toRight) {
        spaceship.setX(spaceship.getX() + spaceship.getXSpeedOnKeyPress() * (toRight ? 1 : -1));
        spaceship.wrap();
    }

    private void readFile(String levelFile) {
        try {
            File file = new File(levelFile);
            Scanner myReader = new Scanner(file);
            handleFileLines(myReader);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading level layout txt file: " + levelFile);
            e.printStackTrace();
        }
    }

    protected abstract void handleFileLines(Scanner myReader);

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public abstract List<List<Enemy>> getEvilEntities();

    public void setLevelLost(boolean levelLost) {
        this.levelLost = levelLost;
    }
}
