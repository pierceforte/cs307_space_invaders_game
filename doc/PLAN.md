# Game Plan
## NAMEs 
Pierce Forte (phf7)
Jeff Kim (ek111)

### NOTE  
After talking to Professor Duvall, we have decided to create a Space Invaders game instead of Breakout.

### Space Invaders Variant  
One interesting variant of Space Invaders that we found is called Chicken Invaders: Revenge of the Yolk.  While the concept behind this game is rather silly and intended to be humorous, we thought that this was a unique way to modify such a well known game and make it truly feel original. There are many different weapon types, the user-controlled spaceship has free range of movement (which is pretty impressive), and there are many different chicken-themed enemies, each of which has its own abilities and adds a significant element to the game. Most remarkably, however, is that the game is multiplayer, and users can play together over the Internet! While our team certainly will not be able to implement that type of functionality, it has inspired us to think beyond the basic concept of Space Invaders.  

Description: https://www.mobygames.com/game/chicken-invaders-revenge-of-the-yolk

Gameplay: https://www.youtube.com/watch?v=Ha3gYz-3xcM

### General Level Descriptions
1. Level 1 

    1,1,1,1,1,1,1,1,1  
    1,1,1,1,1,1,1,1,1   
    1,1,1,1,1,1,1,1,1    
    1,1,1,1,1,1,1,1,1  
    
    - Enemy Specification
        - Will be placed in the orientation shown above
        - 1 health
        - Only able to shoot vertical lasers
        - Either moves very slowly or not at all
        
    - Spaceship Specification
        - Will start in the center of the bottom

2. Level 2

    3,3,3,3,3,3,3,3,3  
    2,2,2,2,2,2,2,2,2    
    1,1,1,1,1,1,1,1,1    
    2,2,2,2,2,2,2,2,2    
    2,2,2,2,2,2,2,2,2  
    
    - Enemy Specification
        - Will be placed in the orientation shown above
        - **Varying health for each enemy**
        - **Able to move** (slightly faster than previous level)
        - Shoot vertical lasers
        
    - Spaceship Specification
        - Will start in the center of the bottom
        
3. Level 3

    3,3,3,3,3,3,3,3,3  
    3,3,3,3,3,3,3,3,3    
    1,3,2,1,3,2,1,3,2  
    3,2,1,3,2,1,3,2,1    
    2,2,2,2,2,2,2,2,2    
    2,2,2,2,2,2,2,2,2  
    
    - Enemy Specification
        - Will be placed in the orientation shown above
        - Varying health for each enemy
        - Able to move (slightly faster than previous level)
        - **Shoot both vertical and diagonal lasers**
        
    - Spaceship Specification
        - Will start in the center of the bottom

4. Boss round

    - One big boss in the center
    - Very high health count
    - Shoot in all direction
    - Able to move
    - If time allows:
    	- Boss changes size
        - Has intervals when it is invincible and when it can be damaged
        - Add a boss between each level with increasing level of difficulty

### Enemy Ideas  
- Life count
    - More life counts for upper levels
- Missile type
	- Regular missile (shot downward in regular interval)
    - Diagonal missile (bounce off walls)
- Looks
    - Different images for enemies based on initial health
- Speed 
    - Higher speed for upper levels
- Contains random power up
- Movement
	- left, right, down

### Spaceship
- Life count
- Missile type
- Movement
	- left, right

### Power Up Ideas  
- Bomb Missile 
	- Reduces 2 health counts instead of 1
- Increase life counts
- Speed up the spaceship

  **If time allows**
  - Laser beam
	- Beam from spaceship that instantly hits enemies in path (could last around 1-2 seconds or deactivate after a certain number of enemies are destroyed)
  - Double spaceships
      - Control two spaceships at once 

### Cheat Key Ideas    
- P: pause game
- S: skip level
- D: delete lowest enemy row
- R: reset level
- I: unlimited life counts 
- L: add life
- 1-4+: change to selected level

### Something Extra  
- Adding music
- Final boss between levels (or at the end)
	- Will have a variety of missiles, like a larger missile, one that shoots in multiple directions, etc.
    - If there is a boss between levels, it will become progressively harder with more lives

### Possible Classes  
- Main
	- Begin game
- Game
	- Handle basic game mechanics like step and animation
- Moving Object
	- Abstract
    - Subclass of ImageView
    - Will be used for any object that is 1) added to the scene and 2) has the potential to move/interact with other nodes on scene
- Entity
	- Abstract
    - Will extend MovingObject
- Projectile
	- Abstract
	- Will extend MovingObject
- Enemy (subclass of Entity)
    - The enemies are the entities that shoot lasers periodically (in a set interval, but beginning at different times to avoid all shooting at once)
    - These will have different amounts of lives and different abilities
    - These will move left to right and bounce of the sides (exact implementation depends on level)
- Spaceship (subclass of Entity)
    - This is the user controlled object that shoots lasers at the enemies
    - Will have a given number of lives to determine game overs
- Boss (subclass of Entity)
    - This entity will appear at the end of all the levels (or in between levels if time allows)
    - Will have more advanced capabilities than enemies and free range of movement 
- Laser (subclass of projectile)
    - Simple projectile that reduces 1 health
    - Can be fired by any entity
- Bomb (subclass of projectile)
    - More advanced projectile that reduces 2 health
- Level
    - The class that "builds" the level: adds in all scene elements in their proper orientation based on a txt file
    - Keeps track of state of game (eg. lives, points, etc.) and the objects on screen 
- Setup
    - Not exactly sure how to implement yet, but may take on some of the work from Level in actually building the scene
- ReadFile
	- Reads input
- UserInput
    - Handles user input for cheat keys and object movement
- LevelStatsDisplay
	- Updates the level, lives, etc. on the screen
    