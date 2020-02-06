# Game Plan
## NAMEs 
Pierce Forte (phf7)
Jeff Kim (ek111)

### NOTE  
After talking to Professor Duvall, we have decided to create a Space Invaders game instead of Breakout.

### Space Invaders Variant  
One interesting variant of Space Invaders that we found is called Chicken Invaders: Revenge of the Yolk.  While the concept behind this game is rather silly and intended to be humorous, we thought that this was a unique way to modify such a well known game and make it truly feel original. There are many different weapon types, the user-controlled spaceship has free range of movement (which is pretty impressive), and there are many different chicken-themed enemies, each of which has its own abilities and adds a significant element to the game. Most remarkably, however, is that the game is multiplayer, and users can play together over the Internet! While our team certainly will not be able to implement that type of functionality, it has inspired us to think beyond the basic concept of Space Invaders.  

Description: https://www.mobygames.com/game/chicken-invaders-revenge-of-the-yolk

Gameplay: ttps://www.youtube.com/watch?v=Ha3gYz-3xcM

### General Level Descriptions
1. Level 1 

    1,1,1,1,1,1,1,1,1,1  
    1,1,1,1,1,1,1,1,1,1  
    1,1,1,1,1,1,1,1,1,1  
    1,1,1,1,1,1,1,1,1,1
    
    - Enemy Specification
        - Will be placed in the orientation shown above
        - 1 health
        - Only able to shoot vertical lasers
        - Not able to move
        
    - Spaceship Specification
        - Will start in the center of the bottom

2. Level 2

    3,3,3,3,3,3,3,3,3,3  
    1,1,1,1,1,1,1,1,1,1  
    2,2,2,2,2,2,2,2,2,2  
    2,2,2,2,2,2,2,2,2,2
    
    - Enemy Specification
        - Will be placed in the orientation shown above
        - **Varying health for each enemy**
        - **Shoot both vertical and diagonal lasers**
        - Not able to move
        
    - Spaceship Specification
        - Will start in the center of the bottom
        
3. Level 3

    3,3,3,3,3,3,3,3,3,3  
    1,3,2,1,3,2,1,3,2,1  
    2,2,2,2,2,2,2,2,2,2  
    2,2,2,2,2,2,2,2,2,2
    
    - Enemy Specification
        - Will be placed in the orientation shown above
        - Varying health for each enemy
        - Shoot both vertical and diagonal lasers
        - **Able** to move
        
    - Spaceship Specification
        - Will start in the center of the bottom

4. Boss round

    - One big boss in the center
    - Very high health count
    - Shoot in all direction
    - Able to move 

### Enemy Ideas  
- Life count
    - More life counts for upper levels
- Missile type
	- Regular missile (shot downward in regular interval)
    - Diagonal missile (bounce off walls)
- Looks
    - Different images for each row
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
- Double spaceships
	- Control two spaceships at once 

### Cheat Key Ideas    
- Pause the game and shoot enemies
- Unlimited life counts
- Kill all enemy in the same row at once
- Skip the level

### Something Extra  
- Adding music
- Final boss between levels (or at end)
	- Will have a variety of missiles, like a larger missile, one that shoots in multiple directions, etc.
    - If there is a boss between levels, it will become progressively harder with more lives

### Possible Classes  
- Main
- Entity
	- Enemy
    - Spaceship
    - Final Boss
- Laser
- Level
- SceneUtility
- Setup
- ReadFile
	- Reads input 
- LevelInfo
	- It will have the information for the current level the 
    