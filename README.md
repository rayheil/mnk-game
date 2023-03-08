# M, N, K Game / Tic Tac Toe 

A [m,n,k game](https://en.wikipedia.org/wiki/M,n,k-game) (like Tic-Tac-Toe, but
with variable sizes). Written in Java for CSC-207 at Grinnell College in the
S23 semester. 

## Installation

TODO this still needs a lot of work

It'll probably be something along the lines of:

- Have the right Java installed

- Extract the file from the archive

- Run the script I made with command line arguments

		- May be less easy than I thought, we will see. Maybe the instructions
		  are just along the lines of "Run through Eclipse."

## Running

The program takes three to five command line arguments, as detailed below.

### Two human players

Enter integers for the width and height of the board, as well as the required
number of squares in a row required to win. The winning length may not be less
than either the width or the height of the board.

```
./runme.sh WIDTH HEIGHT WIN_LENGTH
```

### One AI player

If `AI_PLAYER` is true the AI will play as crosses (first player), and if it is
false the computer will play as circles (second player).

```
./runme.sh WIDTH HEIGHT WIN_LENGTH AI_PLAYER
```

### One AI player with set difficulty

The specified difficulty can be an integer
between 1 and 10, with 1 being the easiest and 10 being the hardest.

```
./runme.sh WIDTH HEIGHT WIN_LENGTH AI_PLAYER DIFFICULTY
```

## Controls

Use WASD or arrow keys to move the cursor, and press space to place your piece
on the tile it is hovering over.

Press Tab to reset the game and Esc to quit.
