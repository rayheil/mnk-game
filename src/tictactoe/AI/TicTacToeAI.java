package tictactoe.AI;

import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.Player;

public class TicTacToeAI implements ITicTacToeAI {

	/**
	 * Default constructor that creates an AI of difficulty 5.
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
		Player = player;
		Difficulty = difficulty;
		// TODO the rest of the dang constructor lol
	}
	
	@Override
	public Vector2i GetNextMove(ITicTacToeBoard board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player GetPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The difficulty of this AI.
	 */
	protected int Difficulty;
	
	/**
	 * Which player (CROSS or CIRCLE) this AI is playing
	 */
	protected Player Player;
}
