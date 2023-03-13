# Tic Tac Toe 

This is my implementation of an [m,n,k
game](https://en.wikipedia.org/wiki/M,n,k-game). It's Tic-Tac-Toe, but with
variable sizes. Also, there's AI. (What fun!) Project for CSC207 at Grinnell
College in the Spring 2023 semester.


# Running

The class that contains the `main` method to run this code is
`tictactoe.Bootstrap`.

To run through eclipse, open the project and select Run > Run Configurations
from the topbar. Under the Main tab, click to search for a main class and select
`tictactoe.Bootstrap`.

Under the Arguments tab, you need to enter the game parameters. For a basic game
of tic-tac-toe where you play first against an AI, enter the program arguments
`3 3 3 false`. Then, click Run at the bottom of the popup window to start the
game. For more complex games, see the Run Configuration section below.


# Controls

Use WASD or arrow keys to move the cursor, and press space to place your piece
on the tile it is hovering over.

Press Tab to reset the game and Esc to quit.

If you are playing against an AI, you cannot move the cursor or play while it
decides its move. It can take a while to do so if the AI is high level or if the
board is large, so be warned!


# Run Configuration

The program takes three to five command line arguments. Bracketed parameters are optional.

```
WIDTH HEIGHT WIN_LENGTH [AI_PLAYER [AI_DIFFICULTY]]
```

The meaning of each parameter is detailed below.

- `WIDTH` is the width of the board in squares.

- `HEIGHT` is the height of the board in squares.

- `WIN_LENGTH` is the number of symbols in a row a player knows to win. It
  cannot be less than either `WIDTH` or `HEIGHT`.

- `AI_PLAYER` toggles which player is an AI. If it is not present, both players
  will be controlled by humans. If it is `true` the first player will be
  controlled by an AI, and if it is `false` the second player will be controlled
  by an AI.

- `AI_DIFFICULTY` modifies the difficulty of the AI player. It should be between
  1 and 10 (inclusive), with 1 being the easiest and 10 being the hardest. If it
  is not present, the AI will default to a dificulty of 5.
