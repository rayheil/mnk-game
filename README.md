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


# A note on efficiency

The AI does not work as well as I think it should, but I've put in all the time
that I'm willing to on this project. I know I could have gotten an extension,
but I can't cope with having this hanging over my head for any longer. Detail on
what this code can and can't handle below.

The AI runs well (under 30s evaluation times) at level 10 on a 4x4 board, but a
4x5 board is too much for the max level AI. With a lower difficulty level such
as 7 (which is still very competent at the game especially against
sleep-deprived Ray), the size of the board can be pushed up to a 6x6, which
covers my screen. 

It's not great efficiency, but I've taken by best attempts at
alpha-beta pruning, static state evaluation, and child state generation and I
don't think I can improve them any more.

I understand if this is basis to fail this part of the project, but I hope you
can understand.

# Run Configuration

The program takes three to five command line arguments. Bracketed parameters are optional.

```
WIDTH HEIGHT WIN_LENGTH [AI_PLAYER [AI_DIFFICULTY]]
```

The meaning of each parameter is detailed below.

- `WIDTH` is the width of the board in squares.

- `HEIGHT` is the height of the board in squares.

- `WIN_LENGTH` is the number of symbols in a row a player knows to win.

- - It cannot be less than either `WIDTH` or `HEIGHT`.

- `AI_PLAYER` toggles which player is an AI. If it is not present, both players
  will be controlled by humans. If it is `true` the first player will be
  controlled by an AI, and if it is `false` the second player will be controlled
  by an AI.

- - It is not possible for both players to be controlled by AI.

- `AI_DIFFICULTY` modifies the difficulty of the AI player. It should be between
  1 and 10 (inclusive), with 1 being the easiest and 10 being the hardest. If it
  is not present, the AI will default to a dificulty of 5.

- - If set to 1, the AI
    will play completely randomly. Both the theoretically best and worst player,
    depending on how the universe feels today!
