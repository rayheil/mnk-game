package tictactoe.AI;

import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.Player;

/**
 * The outline of an AI for a Tic Tac Toe game.
 * @author Dawn Nye
 * @param <T> The type of value stored in each cell of the board.
 */
public interface ITicTacToeAI
{
	/**
	 * Gets the AI's next move from the given board state.
	 * @param board The current state of the game.
	 * @return Returns the position the AI will claim next or null if it cannot make a move.
	 * @throws NullPointerException Thrown if {@code board} is null.
	 */
	public Vector2i GetNextMove(ITicTacToeBoard board);
	
	/**
	 * Determines which player the AI controls.
	 */
	public Player GetPlayer();
}
