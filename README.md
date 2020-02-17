game
====

This project implements the game of Space Invader.

Name: Jeff Kim (ek111), Pierce Forte (phf7)

### Timeline

Start Date: January 29th, 2020

Finish Date: February 16th, 2020

Hours Spent: 50

### Resources Used
Image files for the games are from https://www.pngfind.com
Stackoverflow for common bugs (e.g: Running platform.runlater() for our test cases )

### Running the Program

Main class:
Game

Data files needed: 
- Set resources folder as resources directory

Key/Mouse inputs:
- Arrow keys: move spaceship left and right directions
- Space: Shoot lasers
- L: Add spaceship life 
- A: Drop random power up
- B: Drop extra missile power up
- F: Drop speed power up 
- M: Drop stronger missile power up 
- D: Destroy enemy starting from bottom left
- S: Skip level
- R: Reset level 
- W: Reset game
- P: Pause game
- Q: Exit game

Cheat keys:
- L: Add spaceship life 
- A: Drop random power up
- B: Drop extra missile power up
- F: Drop speed power up 
- M: Drop stronger missile power up 
- D: Destroy enemy starting from bottom left
- S: Skip level

Known Bugs:
- Not at the moment

Extra credit:
- Boss level
    - Boss has vulnerable and invincible mode
    - Only damaged during vulnerable mode, which is shown by the change in image of the boss
    - Shoots different types of missiles 
- Background Music
    - Added a background music that gives a real arcade feeling

### Notes/Assumptions
- We assumed that optimization in the logic is not our primary concern. For example, when setting up the enemy, instead of loading 
the image file for each enemy, we could've loaded one image and copy that to create the other enemies which would probably be faster, 
but didn't because it was not our primary concern. 
- We assumed that every moving object will be inheriting and ImageView class, which makes it much easier because we could use the features it provides 
 

### Impressions
- We realized having a good design is really important when we wanted to add extra features. If the class hierarchy is set properly, 
it is easy to add a different type of that specific class. For example, when adding a different power up, we initially had the power up 
as its own class, which made it harder when we tried to make a different type of power up. Having the power up as an abstract class and 
the specific power ups inherit this class made it much easier to implement these features. 


