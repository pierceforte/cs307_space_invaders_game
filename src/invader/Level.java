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

    protected int curSpaceshipLaserIdNumber = 0;

    protected boolean levelLost = false;

    protected Game myGame;
    protected Group root;

    protected int levelNumber;

    protected Spaceship spaceship;
    protected List<Laser> spaceshipLasers = new ArrayList<>();

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

    protected void attemptSpaceshipFire(double gameTimer) {
        attemptLaserFire(gameTimer, spaceship, spaceshipLasers, 1);
    }

    protected void attemptLaserFire(double gameTimer, Entity entity, List<Laser> lasers, double timeBeforeNextShot) {
        if (gameTimer >= entity.getStartShootingTime()) {
            shootLaser(entity, lasers, timeBeforeNextShot);
        }
    }

    protected abstract void handleEvilEntitiesMovement();

    protected abstract void handleEvilEntityLasers(double gameTimer);

    protected abstract void handleSpaceshipLasers();

    protected abstract Entity handleLaserCollisions(List<Laser> lasers, Entity entity);

    protected abstract void shootLaser(Entity entityShooting, List<Laser> lasers, double timeBeforeNextShot);

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
