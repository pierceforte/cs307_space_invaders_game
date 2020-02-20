# Game Design Final
### Names
Jeff Kim (ek111)
Pierce Forte (phf7)

### Note
See the "General" section of "Assumptions" before reading this document.

## Team Roles and Responsibilities

Because our team only consisted of two members, we chose not to assign specific roles and instead worked together when possible and took on specific tasks as necessary. While this worked, in the future it would be nice to define more specific roles!

 * Team Member #1  
 Pierce Forte
 

 * Team Member #2
 
  Jeff Kim

## Design goals

#### What Features are Easy to Add  
- It is very simple to add a new power up to the game. To do so, one just has to create a new class that extends the abstract class "PowerUp." Next, they must implement (override) the three required methods – activate, deactivate, and reactivate – that describe how the power up should modify the game – and call the PowerUp constructor within the new power up's constructor (by calling super). Here, one must also specify the duration of the power up and its image. Beyond this, the only other requirement is to go into the EnemyLevel class and add the class name to the constant list called "POWER_UP_TYPES," which is used to randomly assign power ups to enemies.
- To add a new key input, one simply has to go into the KeyHandler class and modify the initializeKeyToActionMap method, which adds each of the keys and their desired actions to a hashmap. In the map, the key input (the map's key) has a value that is a runnable, which can be whatever the user wants to happen when the key is pressed – they can have a method call here or simply write the desired functionality directly. Similarly, it is easy to modify what a key should do – one simply has to make a call to change one of the key-value pairs in the hashmap (though there is not a function for this in our code, it would be easy to implement).
- To create a new object to be added to the scene, we have built a framework that makes it quite easy. First, the abstract MovingObject class extends from the ImageView class, and objects of this class can be set to have certain images, different speeds, and update their position on each step. Classes that extend from MovingObject are Entity and Projectile, both of which are abstract. The Entity class's subclasses are each of the "players" on screen – the Spaceship, Boss, and Enemy objects. To create a new type of entity, one just has to create a constructor that calls super, choose an image and default speed, position, etc. values, and implement the "createProjectile" method, which determines how the Entity should fire projectiles. Similarly, the Projectile's subclasses are Laser, Fireball, and Missile, each of which describes a different type of projectile – to create a new one, one simply has to make a new class, choose an image, and determine how much damage the projectile should do to other entities.
- It is rather easy to add a new level to the game. The one complexity that arises is that there are two types of levels – an enemy level, and a boss level, each of which extend from the Level class and have different features. To add one of the enemy levels, one must simply write up another level_#.txt file for level "#", in which they include the rows of enemies with 9 columns (this is a requirement we set in the code, but it could be changed easily be changing (or removing) the constant "ENEMIES_PER_ROW" in the Level class (note this was made constant to ensure a reasonable spacing between enemies)). The number that identifies each enemy represents its lives, and we currently have enemy images for lives 1-3. Further, writing a number with a "-" will cause the enemy to shoot in "burst fire," which is three lasers in different directions. Beyond this, one simply has to change the "MAX_LEVEL" constant in the Game class to reflect the new max.
	- Note that given the way our code is written, the only inflexible aspect of this is that the last level must be the boss level, and all the levels before it must be enemy levels (the "normal" levels) – see assumptions below for more information. To change this, one simply has to go into the KeyHandler class and change which level numbers correspond to which type of level in the "goToLevel" method. We would have liked to make it such that the Level class itself can determine which level to create within its constructor but were unsure if this was possible.

## High-level Design
- We have a Game class, that runs the game by calling the step function for each frame. Excluding the main Game class, the project has 5 big components: Entity, Level, PowerUp, Projectile, and StatusDisplay. 
- In the Game class's step function, the current level is told to "update." This involves updating the position of all of the objects on the scene, detecting collisions, and determining game state, like victory or loss.
- Also in the Game class, key detection is handled with the "KeyHandler" object. This KeyHandler determines what to do when a key is pressed, and it determines the appropriate functionality based on the game's state (e.g. in play or in a menu).
- Finally, the StatusDisplay class acts as a utility class, and it can be called to create menus or update the status indicators like lives or points.

#### Core Classes
- Game
	- Main class the runs the entire game. It sets up the scene, key handles, background music, and other necessary components for the game

- MovingObject
	- Abstract class that inherits the ImageView class. We've decided to make this class because all of our moving objects shared the common feature of an ImageView, and reduced a lot of duplicate code. The constructor sets the common properties of all moving objects like the xSpeed, ySpeed, image, and others. It is inherited by all moving objects in our game including Enemy, Boss, Spaceship, Laser, and Missiles. 
    - Although the class does not have any abstract methods, we've created as an abstract classs so that we don't mistakenly create an object of this class. 
    
- Entity
	- Abstract class that inherits from the MovingObject class. It is the core class that is used to create the Enemy, Boss, and Spaceship class. This abstract class makes it easier to create any other moving entities if we happen to do so. 

- Level
	- Abstract class that is used to create all levels of entities used in this game. This class is necessary because the levels share a lot of similar methods and properties, but also have unique features for each class. The basic set up of the game and handling the user spaceship would be common among all levels, so all of them are defined as public methods. The level specific methods like cheat codes or handling the missiles are abstract methods because it should be overriden in its subclass, EnemyLevel and BossLevel.
    
- PowerUp
	- Abstract class that is used to create BurstFire, Missile, and Speed powerups. Again, all the common methods like setting powerup activation time is public, whereas the actual effect like damage or activate, deactivate is abstract. 

- Projectile
	-  Abstract class that is inherited by Fireball, laser, and missile. Although it does not have any abstract methods, we decided to make it abstract so that we don't accidentally instantiate this class in our code. Methods like setting and getting the damage of that specific projectile is common among all methods, and the specific damage or powerup is set in the subclasses. 
    
- StatusDisplay
	- Class that is used to display all messages on the screen.  It deals with the splash screens before, between, and after each level and the current status for that level. All variables and methods are static because it could be used anytime during the game and do not need multiple instances. As such, the class is a final class. This class, if time had allowed, would have been a focus for refactoring given its length. While the number of constants is high, we wanted to focus on flexibility and eliminate duplication/magic values.

## Assumptions that Affect the Design
- General
	- We chose to implement a variant of Space Invaders with the advice of Professor Duvall. I (Pierce) had already worked on Breakout before switching into this class from 308, and Jeff was initially in 308 as well. During our efforts on the Space Invaders game, we found that there were many similarities to Breakout (which will make it easier to grade!) but enough to make this a challenging and unique experience. In particular, I (Pierce) really enjoyed this opportunity because it allowed me to focus on good design, and we both agree that this was a great way to build our coding skills.



- Peformance
	- Performance is not a big issue, so it might be algorithmically inefficient

- Level
	- Levels become more difficult because more enemies are present, the number of lives each enemy has is higher, and each enemy moves to the left and right faster. Overall, this makes the game harder because these more difficult elements are added while others stay constant, like the spaceship's lives and the powerups available. However, with more enemies, there are more powerups, which presents an opportunity for the player to better attack the level.
    - Another assumption we made was that the final level of the game would always be the boss level (which was our extra feature) and all levels before it would be the enemy levels (which are the traditional Space Invaders levels).
	

#### Features Affected by Assumptions
- Performance
	- When we create the enemies on the screen, load the image everytime for each enemies, which could actually done by creating a hashmap for all the image files at first, and later access it from each levels instead of loading the image everytime. While this would have been faster, we prioritized our program's functionality and design.
    
- Level
	- Levels are affected by our assumptions because they became more difficult based on these assumptions and mainly have code to handle these assumptions. For example, we assumed that to make a more difficult level, we could increase the number of enemies, how many lives they had, how fast they moved, and how many of them fired the more difficult "burst fire." As such, these were the features we implemented.
    - Because our Boss level must always be the last level, this changed the way that the game progressed and how new levels must be added. See the final bullet point in "What Features are Easy to Add" to find more information on how this design choice could be changed.

## New Features HowTo
- Adding sound effects for collisions or firing 
	- Whenever we are detecting a collision, firing, or game state change (like victory or loss) in our Level class, we could just call a play sound method similar to the one used in the Game class to create the background music. One thing we could do take this even further is create a Music class, and we could create subclasses for specific sounds with specific durations that could be created when necessary.
- Change spaceship image based on the current power up status
	- Detect the current powerups the spaceship is holding, and load different image files for the specific power ups. 

#### Other Features not yet Done
N/A
