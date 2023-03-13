# Tic Tac Toe 

This is my implementation of an [m,n,k
game](https://en.wikipedia.org/wiki/M,n,k-game). It's Tic-Tac-Toe, but with
variable sizes. Also, there's AI. (What fun!) Project for CSC207 at Grinnell
College in the Spring 2023 semester.


# Running

The class that contains the `main` method to run this code is
`tictactoe.Bootstrap`. As long as the game engine is working on your end, all
the code should be able to run. If there's some disconnect between what you
expected and what I've sent, let me know and I'll try to fix it right away!

The command line arguments work as specified on the project example page, and
I've also made it clear in the section below. 


# Controls

Use WASD or arrow keys to move the cursor, and press space to place your piece
on the tile it is hovering over.

Press Tab to reset the game and Esc to quit.

If you are playing against an AI, you cannot move the cursor or play while it
decides its move. It can take a while to do so if the AI is high level or if the
board is large, so be warned!


# A note on efficiency

The AI does not work as well as I think it probably should, but I've put in all
the time that I'm willing to on this project. I know I could have gotten an
extension, but I can't cope with having this hanging over my head for any
longer. Detail on what this code can and can't handle below.

The AI runs well (around 10s evaluation times on my computer) at level 10 on a
4x4 board, but a 4x5 board is too much for the max level AI. With a lower
difficulty level such as 7 (which is still very competent at the game especially
against one sleep-deprived Ray), the size of the board can be pushed up to a
6x6, which fills my screen.

It's not great efficiency, but I've taken by best attempts at
alpha-beta pruning, static state evaluation, and child state generation and I
don't think I can improve them any more.

I understand if this is basis to fail this part of the project, but I hope you
understand why I'm calling it at this point.


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
