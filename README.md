game
====

This project implements the game Space Invaders.

Name: Pierce Forte (phf7), Jeff Kim (ek111)

### Timeline

Start Date: January 29th, 2020

Finish Date: February 16th, 2020

Hours Spent: 50

### Resources Used
- Image files for the games are from https://www.pngfind.com  
- Stackoverflow was used for common bugs (e.g: Running platform.runlater() for our test cases )
- Java's documentation was used to learn about different classes and understand dependencies (such as interactions between JavaFx classes)
- Readings from class were used to implement JUnit tests and concepts like polymorphism

### Running the Program

Main class:
src/invader/Game

Testing class:
test/invader/GameTest
- While our code is designed to catch errors and write them to a logger file (error_log.txt), there are, admittedly, few errors that our code handles without crashing. Given that the game depends on numerous features – like correctly formatted level files, the presence of necessary files, etc., not having these files will make it impossible for the code to properly setup/continue the game. If we had more time, we would have certainly liked to consider this issue further.

Data files needed: 
- Set resources folder as resources directory
- error_log.txt file to write errors to
- Within resources:
    - node images
    - level_files/
        - files level_1.txt through level_4.txt
    - highscores.txt file to read in/write out top 100 high scores
    - game_music.wav

Key/Mouse inputs:
- Arrow keys: move spaceship left and right
- Space: Shoot lasers from spaceship
- 1-9: Jump to level
- L: Add spaceship life 
- A: Drop random power up
- B: Drop extra missile power up
- F: Drop speed power up 
- M: Drop stronger missile power up 
- D: Destroy enemy starting from bottom left
- S: Skip level
- R: Reset level 
- W: Reset game after high score entry
- P: Pause game
- Q: Exit game after high score entry
- ENTER: enter high score when prompted
- High score text field takes all input, except it cuts out the character ":" if present in given name

Cheat keys:
- 1-9: Jump to level
- L: Add spaceship life 
- A: Drop random power up
- B: Drop burst fire power up
- F: Drop speed power up 
- M: Drop stronger missile power up 
- D: Destroy first enemy (defined as leftmost enemy in bottom row, or the boss)
- S: Skip level

Known Bugs:
- The primary bug that we have encountered is the ability to "charge up" the spaceship's laser to a certain degree. The code is written such that the spaceship should only be able to fire once every second, but not firing for a long enough period of time will allow the user to fire multiple shots in a row without waiting for the 1 second timer. While this is an interesting feature – and one that we actually quite like – we have had trouble identifying its root cause. We are happy that it is not game breaking and actually adds an interesting element but would ceratinly like to pinpoint the issue.
- Beyond this bug, though, we have not been able to identify additional bugs/crashes. We spent a lot of time ensuring the game's proper functionality and hope that our tests have been sufficient in identifying errors.

Extra credit:
- Boss level
    - Below are some of this level's features
        - Boss has vulnerable and invincible mode
        - Only damaged during vulnerable mode, which is shown by the change in image of the boss
        - Shoots different types of missiles
    - We thought that the addition of a boss level would be a challenging but rewarding way to improve our game. Many arcade games stick in our minds because of the boss fights, and we wanted to see what it would be like to implement an exciting element like this. Through the creation of an abstract level class, implementing the boss level was far less difficult than it likely could have been. Likewise, because we had an abstract class for the game's entities, creating the boss was, again, far less difficult. I mention this because we specifically designed our game from the beginning so it would be easier/more flexible to add features like the boss level, and we were very happy to see the rewards of our work. 
- Background Music
    - We chose to add background music to give the game a more realistic, tangible arcade feeling. If time had allowed, we would have liked to implement more sounds in the game; however, we were excited to add music, which, while seemingly a small feature, influences the player's experience greatly.

### Notes/Assumptions
- We chose to implement a variant of Space Invaders with the advice of Professor Duvall. I (Pierce) had already worked on Breakout before switching into this class from 308, and Jeff was initially in 308 as well. During our efforts on the Space Invaders game, we found that there were many similarities to Breakout (which will make it easier to grade!) but enough to make this a challenging and unique experience. In particular, I (Pierce) really enjoyed this opportunity because it allowed me to focus on good design, and we both agree that this was a great way to build our coding skills.

- We assumed that the optimization of code was not our primary concern. For example, when creating the enemy nodes, instead of loading the image file for each enemy, we could have loaded one image and copied that to create the other enemies. While this would have been faster, we prioritized our program's functionality and design.

- We were a little unsure about the difference between the cheat keys and key/mouse inputs above, which is why some are listed twice. In general, we made a strong effort to include a wide range of functionality that will both make our game more enjoyable to play and, of course, easier to grade.

- Levels become more difficult because more enemies are present, the number of lives each enemy has is higher, and each enemy moves to the left and right faster. Overall, this makes the game harder because these more difficult elements are added while others stay constant, like the spaceship's lives and the powerups available. However, with more enemies, there are more powerups, which presents an opportunity for the player to better attack the level.
 

### Impressions
- We realized having a good design is really important when we wanted to add extra features. If the class hierarchy is set properly, it is easy to add a different type of that specific class. For example, when adding a different power up, we initially had each power up as its own class with its own methods, which made it far harder when we tried to make a different type of power up. However, implementing the power up as an abstract class and having each specific power up inherit this class made our lives far easier. For example, with the way the code is currently written, to create a new power up all you need to do is override three methods (activate, deactivate, and reactive) and fill in its constructor with basic information like image file name and time active; all in all, this process would take a mere 10 minutes, whereas before it may have taken hours.

- Both of us really valued the opportunity to take on a new game and truly practice good design. We hope that our efforts to implement things like polymorphism were successful, and we are happy with the results of our work!


