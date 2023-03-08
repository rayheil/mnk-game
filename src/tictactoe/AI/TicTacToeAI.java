package tictactoe.AI;

import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.Player;

public class TicTacToeAI implements ITicTacToeAI {

	/**
	 * Default constructor that creates an AI of difficulty 5. Why not?
	 */
	public TicTacToeAI(Player player)
	{
		this(player, 5);
	}
	
	/**
	 * Constructor that takes a difficulty level 1-10 as input.
	 */
	public TicTacToeAI(Player player, int difficulty)
	{
		if (player == null)
			throw new NullPointerException();
		
		if (difficulty < 1 || difficulty > 10)
			throw new IllegalArgumentException("Difficulty must be between 1 and 10 (inclusive)");
		
		Player = player;
		Difficulty = difficulty;
		// TODO the rest of the dang constructor lol
		// is it even a thing that happens? this thing has so few required attributes.
	}
	
	@Override
	public Vector2i GetNextMove(ITicTacToeBoard board) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Return an iterator containing all the playable states from a given board state
	 * (all those cells that are empty).
	 * @param board The current board state.
	 * @return Iterable over all empty cells in the board
	 */
	protected Iterable<Vector2i> GetChildStates(ITicTacToeBoard board) {
		return LINQ.Where(board.IndexSet(), t -> board.IsCellEmpty(t));
	}

	@Override
	public Player GetPlayer() 
	{return Player;}

	/**
	 * The difficulty of this AI.
	 */
	protected int Difficulty;
	
	/**
	 * Which player (CROSS or CIRCLE) this AI is playing
	 */
	protected Player Player;
}
