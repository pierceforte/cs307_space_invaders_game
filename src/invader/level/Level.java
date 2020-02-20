package invader.level;

import invader.Game;
import invader.StatusDisplay;
import invader.entity.Entity;
import invader.entity.Spaceship;
import invader.projectile.Projectile;
import javafx.scene.Group;
import javafx.scene.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is an abstract class that is used to create all types of levels used in this game.
 *
 * This class is necessary because the levels share a lot of similar methods and properties, but also have unique features for each class.
 *
 * The basic set up of the game and handling the user spaceship would be the similar, so all of them are defined as public methods.
 *
 * The level specific methods like cheat codes or handling the missiles are abstract methods because it should be reimplemented in its
 * subclass, EnemyLevel and BossLevel. Level specific methods have a protected access level.
 *
 * @author Pierce Forte
 * @author Jeff Kim
 */

public abstract class Level {

    public static final String LEVEL_FILE_PATH = "/level_files/level_";
    public static final String LEVEL_FILE_EXTENSION = ".txt";
    public static final int SPACESHIP_LASER_ROTATION = 0;

    protected boolean levelLost = false;
    protected Game myGame;
    protected Group root;
    protected int levelNumber;
    protected Spaceship spaceship;
    protected List<Projectile> spaceshipProjectiles = new ArrayList<>();
    protected List<Projectile> evilEntityProjectiles = new ArrayList<>();

    /**
     * Create a Level
     * @param root: Root of the game
     * @param levelNumber: The level number the game is currently in
     * @param myGame: My game
     */
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

    /**
     * Move the spaceship
     * @param toRight whether spaceship should move right (true) or left (false)
     */
    public void moveSpaceship(boolean toRight) {
        spaceship.setX(spaceship.getX() + spaceship.getXSpeedOnKeyPress() * (toRight ? 1 : -1));
        spaceship.wrap();
    }

    /**
     * Give the spaceship an additional life and add this to the status display
     */
    public void addLife() {
        spaceship.addLife();
        StatusDisplay.updateLifeCountDisplay(spaceship.getLives());
    }

    /**
     * Get current level number
     * @return the current level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Set current level number
     * @param levelNumber the current level number
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * Set whether the level has been lost
     * @param levelLost whether the level has been lost
     */
    private void setLevelLost(boolean levelLost) {
        this.levelLost = levelLost;
    }

    /**
     * Clears everything on the current level
     */
    public abstract void clearLevel();

    /**
     * Add spaceship and enemies or boss to the scene
     */
    public abstract void addEntitiesToScene();

    /**
     * Handle the entities and lasers (collision)
     * @param gameTimer the timer of the entire game
     * @param elapsedTime the time that is elapsed after a single step
     */
    public abstract void handleEntitiesAndLasers(double gameTimer, double elapsedTime);

    /**
     * Check if the victory condition is met
     */
    public abstract void attemptLevelVictory();

    /**
     * Add a random power up to the game
     * @param gameTimer the timer of the entire game
     */
    public abstract void addRandomPowerUp(double gameTimer);

    /**
     * Add a power up to the game that increases spaceship speed on key press
     * @param gameTimer the timer of the entire game
     */
    public abstract void addSpeedPowerUp(double gameTimer);

    /**
     * Add a power up to the game that gives spaceship more powerful projectiles for firing
     * @param gameTimer the timer of the entire game
     */
    public abstract void addMissilePowerUp(double gameTimer);

    /**
     * Add a power up to the game that gives spaceship the ability to burst fire three lasers
     * @param gameTimer the timer of the entire game
     */
    public abstract void addBurstFirePowerUp(double gameTimer);

    /**
     * Destroy the enemy on the screen
     */
    public abstract void destroyFirstEnemy();

    /**
     * Update the scenes' node positions
     * @param elapsedTime the time that is elapsed after a single step
     */
    protected abstract void updateNodePositionsOnStep(double elapsedTime);

    /**
     * Update the positions of the provided projectiles
     * @param elapsedTime the time that is elapsed after a single step
     * @param projectiles the projectiles that need to have their positions updated
     */
    protected void updateProjectilePositionsOnStep(double elapsedTime, List<Projectile> projectiles) {
        for (Projectile projectile : projectiles) {
            projectile.updatePositionOnStep(elapsedTime);
        }
    }

    /**
     * Handle and detect collisions between projectiles and spaceship; on a collision, lower spaceship life by one and, if zero,
     * end level
     * @param evilEntityProjectiles the list of projectiles fired by evil entities in the level
     * @param spaceship the spaceship that the user controls
     */
    protected void handleProjectileCollisionWithSpaceship(List<Projectile> evilEntityProjectiles, Spaceship spaceship) {
        if (handleProjectileCollisions(evilEntityProjectiles, spaceship)) {
            StatusDisplay.updateLifeCountDisplay(spaceship.getLives());
            if (spaceship.getLives() == 0) {
                levelLost = true;
                endLevel();
                StatusDisplay.createGameOverMenu(root);
            }
        }
    }

    /**
     * Handle and detect projectile collisions with any entity
     * @param projectiles The projectiles that will be considered for the collision
     * @param entity The entity that is being considered for the collsion
     * @return returns whether a collision was detected or not
     */
    protected boolean handleProjectileCollisions(List<Projectile> projectiles, Entity entity) {
        boolean isCollision = false;
        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            if (projectile.intersects(entity) || projectile.isOutOfYBounds()) {
                projectilesToRemove.add(projectile);
                if (projectile.intersects(entity)) {
                    entity.removeLives(projectile.getDamage());
                    StatusDisplay.updatePointsDisplay(entity.getPointsPerHit());
                    isCollision = true;
                }
            }
        }
        projectiles.removeAll(projectilesToRemove);
        root.getChildren().removeAll(projectilesToRemove);
        return isCollision;
    }

    /**
     * Handle spaceship firing
     * @param gameTimer the timer of the entire game
     */
    public void attemptSpaceshipFire(double gameTimer) {
        attemptProjectileFire(gameTimer, spaceship, spaceshipProjectiles, SPACESHIP_LASER_ROTATION);
    }

    protected void attemptProjectileFire(double gameTimer, Entity entity, List<Projectile> projectiles, double rotation) {
        if (gameTimer >= entity.getStartShootingTime()) {
            if (entity.hasBurstFire()) {
                blastFire(entity, projectiles);
            }
            else shootProjectile(entity, projectiles, rotation);
        }
    }

    protected Projectile shootProjectile(Entity entityShooting, List<Projectile> lasers, double rotation) {
        Projectile projectile = entityShooting.createProjectile(rotation, entityShooting.getCurProjectileIdNumber());
        entityShooting.incrementCurProjectileIdNumber();
        lasers.add(projectile);
        root.getChildren().add(projectile);
        entityShooting.addToStartShootingTime(entityShooting.getTimeBetweenShots());
        return projectile;
    }

    protected <T extends Entity> void blastFire(T entity, List<Projectile> projectiles) {
        shootProjectile(entity, projectiles, Projectile.DEFAULT_PROJECTILE_ROTATION);
        Projectile leftProjectile = shootProjectile(entity, projectiles, Projectile.LEFT_PROJECTILE_ROTATION);
        Projectile rightProjectile = shootProjectile(entity, projectiles, Projectile.RIGHT_PROJECTILE_ROTATION);
        leftProjectile.setXSpeed(Projectile.LEFT_PROJECTILE_X_SPEED);
        rightProjectile.setXSpeed(Projectile.RIGHT_PROJECTILE_X_SPEED);
    }

    protected void handleEvilEntityProjectileBounds() {
        for (Projectile evilEntityProjectile : evilEntityProjectiles) {
            if (evilEntityProjectile.isOutOfXBounds()) {
                double rotation = evilEntityProjectile.getRotate();
                if (rotation != Projectile.DEFAULT_PROJECTILE_ROTATION) {
                    evilEntityProjectile.setRotate(rotation*-1);
                }
                evilEntityProjectile.reverseXDirection();
            }
        }
    }

    // Clear nodes from scene and level
    protected <T extends Node> void clearNodesFromSceneAndLevel(T node) {
        root.getChildren().remove(node);
    }

    // Clear nodes from scene and level (overloading)
    protected <T extends Node> void clearNodesFromSceneAndLevel(List<T> nodes) {
        root.getChildren().removeAll(nodes);
        nodes.clear();
    }

    // Clear the nodes from the scene and level (overloading)
    protected <T extends Node> void clear2dNodesFromSceneAndLevel(List<List<T>> nodes) {
        for (List<T> row : nodes) {
            root.getChildren().removeAll(row);
        }
        nodes.clear();
    }

    // Initiate the level victory
    protected void initiateLevelVictory() {
        endLevel();
        if (getLevelNumber() == Game.MAX_LEVEL) {
            StatusDisplay.createVictoryMenu(root);
        } else if (getLevelNumber() == Game.MAX_LEVEL - 1){
            StatusDisplay.createBossLevelMenu(root);
        } else {
            StatusDisplay.createLevelIntermissionMenu(root);
        }
    }

    // Handle evil entities movement
    protected abstract void handleEvilEntitiesMovement();

    // Handle evil entity lasers
    protected abstract void handleEvilEntityLasers(double gameTimer);

    // Handle spaceship projectiles
    protected abstract void handleSpaceshipProjectiles();

    // Create evil entities
    protected abstract void createEvilEntities();

    // Handle file lines
    protected abstract void handleFileLines(Scanner myReader);

    private void endLevel() {
        myGame.setMenuActive(true);
        myGame.setGameOverMenuActive(true);
        clearLevel();
    }

    private void readFile(String levelFile) {
        try {
            String filePath = this.getClass().getResource(levelFile).getPath();
            File file = new File(filePath);
            Scanner myReader = new Scanner(file);
            handleFileLines(myReader);
            myReader.close();
        } catch (FileNotFoundException e) {
            StatusDisplay.logError(e);
        }
    }
}
