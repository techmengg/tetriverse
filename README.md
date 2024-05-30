# > Tetriverse (Tetris Game) 👾

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![PHP](https://img.shields.io/badge/php-%23777BB4.svg?style=for-the-badge&logo=php&logoColor=white)
![NetBeans IDE](https://img.shields.io/badge/NetBeansIDE-1B6AC6.svg?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)

## Overview

This project is a Java implementation of the classic Tetris game. Tetris is a tile-matching puzzle video game where players manipulate falling tetrominoes (geometric shapes composed of four square blocks) to create complete horizontal lines without gaps. When a line is completed, it disappears, and any blocks above it fall to fill the space. The game ends if the stack of blocks reaches the top of the playing field.

## Mechanics

- **Shapes**: The game includes seven different tetromino shapes: I, T, L, J, S, Z, and O.
- **Movement**: Players can move the falling tetromino left or right, rotate it clockwise, or make it fall faster.
- **Line Clearing**: When a horizontal line is completely filled with blocks, it disappears, and any blocks above it fall down to fill the space.
- **Scoring**: Players earn points for each line cleared. The score increases as more lines are cleared simultaneously.

## How to Play

1. **Controls**:
   - Use the arrow keys to move the falling tetromino left, right, or down.
   - Press the up arrow key to rotate the tetromino clockwise.
   - Press the spacebar to make the tetromino fall instantly.
2. **Objective**:
   - The goal is to create horizontal lines with the falling tetrominoes without leaving any gaps.
   - Clear as many lines as possible to prevent the stack of blocks from reaching the top of the playing field.
3. **Game Over**:
   - The game ends when the stack of blocks reaches the top of the playing field and no new tetromino can be placed.

## Features

- **Pause**: Press the "Pause" button to pause the game at any time.
- **Restart**: Press the "Restart" button to start a new game.
- **Next Shape Preview**: A preview of the next tetromino shape is displayed on the side of the game board.
- **Score Display**: The current score is displayed on the screen, indicating the number of lines cleared.

## Installation

To run the game locally, follow these steps:

1. Clone the repository to your local machine.
2. Ensure you have Java installed on your system.
3. Compile the Java files and run the `WindowGame.java` file to start the game.

```bash
javac *.java
java WindowGame
