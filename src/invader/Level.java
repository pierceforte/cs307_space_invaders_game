package invader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level {

    private int levelNumber;
    private int lives;
    private int rows;
    private List<List<Integer>> enemyIdentifiers;
    private List<List<Enemy>> enemies;

    public Level(String levelFile, int levelNumber) {
        readFile(levelFile);
        this.levelNumber = levelNumber;
        this.lives = 3;
        createEnemies();
    }

    private void createEnemies() {
        enemies = new ArrayList<>();
        // get height of first brick row to ensure they are centered
        //double yPos = Main.GAME_HEIGHT/2.0 - Enemy.ADJUSTED_HEIGHT*rows/2.0;
        double yPos = Main.GAME_HEIGHT/2.0 - Enemy.HEIGHT*rows/2.0;
        for (int i = 0; i < enemyIdentifiers.size(); i++) {
            List<Enemy> tempRow = new ArrayList<>();
            double xPos = 0;
            for (int j = 0; j < enemyIdentifiers.get(0).size(); j++) {
                Enemy curEnemy = new Enemy(xPos, yPos);
                /*
                if (!powerUpGrid.get(i*BRICKS_PER_ROW + j).equals("none")) {
                    curBrick.setPowerUp(powerUpGrid.get(i*BRICKS_PER_ROW + j));
                }
                */
                tempRow.add(curEnemy);
                xPos += Enemy.WIDTH;
            }
            yPos += Enemy.HEIGHT;
        }
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
