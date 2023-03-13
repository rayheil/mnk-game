package tictactoe.AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.Player;
import tictactoe.model.PieceType;

/**
 * 
 * Tic Tac Toe AI that controls one player with variable difficulty, from 1 to 10.
 * 
 * @author Ray Heil
 *
 */
public class TicTacToeAI implements ITicTacToeAI 
{
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
		
		if (!player.equals(tictactoe.model.Player.CIRCLE) && !player.equals(tictactoe.model.Player.CROSS))
			throw new IllegalArgumentException("Player must be either CIRCLE or CROSS.");
		
		Player = player;
		Difficulty = difficulty;
	}
	
	@Override
	public Vector2i GetNextMove(ITicTacToeBoard board)
	{
		if (board.IsFinished())
			throw new IllegalStateException("Board is finished and has no next move.");
		
		// Difficulty 1 is the worst thing I could come up with, playing randomly
		if (Difficulty == 1)
			return GetRandomMove(board);
		
		// Every other difficulty does minimax with varying depth
		double best_score = Double.NEGATIVE_INFINITY;
		Vector2i best_move = null;
		for (Vector2i move : GetChildStates(board))
		{
			ITicTacToeBoard cloned = board.Clone();
			cloned.Set(GetPieceType(), move);

			// We start with depth of difficulty-2 because 1 level is covered by random play,
			// and 1 level is covered by this move selection loop
			double score = Minimax(cloned, Difficulty-2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
			if (score >= best_score) { // if no state is winnable (noticable on a 2x2) we should still decide a move.
				best_score = score;
				best_move = move;
			}
		}
		if (best_move == null)
			throw new NullPointerException("AI was unable to get next move.");
		return best_move;
	}
	
	/**
	 * Get a random valid move on the board.
	 * @param state The board to use.
	 * @return A valid move on the board, chosen randomly.
	 */
	public Vector2i GetRandomMove(ITicTacToeBoard board)
	{
		Iterable<Vector2i> open = LINQ.Where(board.IndexSet(), t -> board.IsCellEmpty(t));
		ArrayList<Vector2i> playable = new ArrayList<Vector2i>();
		for (Vector2i pos : open)
			playable.add(pos);
		
        Random rand = new Random();
        return playable.get(rand.nextInt(playable.size()));		
	}
	
	/**
	 * Perform the minimax algorithm and return the best value.
	 * @param state The current state of the board.
	 * @param depth The depth to search.
	 * @param alpha The current best maximum.
	 * @param beta The current worst minimum.
	 * @param maximizing Whether we start by maximizing or by minimizing
	 * @return The min or max value available from this board state.
	 */
	protected double Minimax(ITicTacToeBoard state, int depth, double alpha, double beta, boolean maximizing)
	{
		if (depth == 0 || state.IsFinished())
			return StaticEvalutation(state);
		
		if (maximizing) {
			double maxEval = Double.NEGATIVE_INFINITY;
			
			// For each child position, recursively find the move that helps the AI most
			for (Vector2i next : GetChildStates(state)) {
				ITicTacToeBoard cloned = state.Clone();
				cloned.Set(GetPieceType(), next);
				maxEval = Double.max(maxEval, Minimax(cloned, depth-1, alpha, beta, false));
				// If we did too well the minimizer will never choose this, prune
				alpha = Double.max(alpha, maxEval);
				if (beta <= alpha)
					break;
			}
			return maxEval;
		}
		else {
			double minEval = Double.POSITIVE_INFINITY;
			
			// For each child position, recursively find the move that hurts the AI most
			for (Vector2i next : GetChildStates(state)) {
				ITicTacToeBoard cloned = state.Clone();
				cloned.Set(GetOpponentPieceType(), next);
				minEval = Double.min(minEval, Minimax(cloned, depth-1, alpha, beta, true));
				// If we did too poorly the maximizer will never choose this, prune
				beta = Double.min(beta, minEval);
				if (beta <= alpha)
					break;
			}
			return minEval;
		}
	}
	
	/**
	 * Evaluate a state and return the value of that state.
	 * A positive value indicates that this AI is doing better, a negative value that this AI is doing worse.
	 * @return The goodness of the state (from -inf to inf)
	 */
	protected double StaticEvalutation(ITicTacToeBoard state)
	{
		/*
		 * If state is finished, return a value based on who (if anyone) won
		 */
		if (state.IsFinished())
		{
			if (state.Victor().equals(GetPlayer()))
				return Double.POSITIVE_INFINITY;
			else if (state.Victor().equals(tictactoe.model.Player.NEITHER))
				return 0;
			else
				return Double.NEGATIVE_INFINITY;
		}
		
		int score = 0;
		
		// Reward the longest lines
		HashSet<Vector2i> seen = new HashSet<Vector2i>();
		for (Vector2i pos : state.IndexSet(true))
		{
			if (seen.contains(pos))
				continue;
			
			int modifier = state.Get(pos) == GetPieceType() ? 1 : -1;
			ArrayList<Vector2i> directions = new ArrayList<Vector2i>(4);
			directions.add(new Vector2i(0, 1));
			directions.add(new Vector2i(1, 0));
			directions.add(new Vector2i(1, 1));
			directions.add(new Vector2i(1, -1));

			for (Vector2i dir : directions) {
				Iterable<Vector2i> line = state.LongestLine(pos, dir);
				int count = 0;
				for (Vector2i vec : line) {
					seen.add(vec);
					count++;
				}
				// Subtract 1 from count so lengths of 1 do not help score at all
				score += modifier * (count - 1);
			}
		}
		
		return score;
	}
	
	/**
	 * Return an iterator containing all the possible positions to play from the current state. 
	 * Moves that are the most likely to be useful are returned first, to help with alpha/beta pruning.
	 * @param board The current board state.
	 * @return Iterable over all empty cells in the board
	 */
	public Iterable<Vector2i> GetChildStates(ITicTacToeBoard board)
	{
		LinkedList<Vector2i> childStates = new LinkedList<Vector2i>();
		
		/*
		 * Locate and add the centermost square(s) first
		 */
		
		Vector2i pos;
		
		// Width and height of the center of the board depend on if dimensions are even or odd.
		int centerWidth = (board.Width() % 2 == 0) ? 2 : 1;
		int centerHeight = (board.Height() % 2 == 0) ? 2 : 1;

		// The position that the center of the board starts.
		int centerXStart = (board.Width() - centerWidth) / 2;
		int centerYStart = (board.Height() - centerHeight) / 2;

		// The position that the center of the board ends (not inclusive)
		int centerXEnd = centerXStart + centerWidth;
		int centerYEnd = centerYStart + centerHeight;
		
		// Add all the center squares to the linked list
		for (int x = centerXStart; x < centerXEnd; x++)
		{
			for (int y = centerYStart; y < centerYEnd; y++)
			{
				pos = new Vector2i(x, y);
				if (board.IsCellEmpty(pos))
					childStates.add(pos);
			}
		}
		
		/*
		 * Add the squares at the corners of the grid if they are available.
		 */
		ArrayList<Vector2i> corners = new ArrayList<Vector2i>(4);
		corners.add(new Vector2i(0, 0));
		corners.add(new Vector2i(board.Width() - 1, 0));
		corners.add(new Vector2i(0, board.Height() - 1));
		corners.add(new Vector2i(board.Width() - 1, board.Height() - 1));
		
		for (Vector2i corner : corners) {
			if (board.IsCellEmpty(corner))
				childStates.add(corner);
		}
	
		/*
		 * Add the other squares in the grid, not caring as much about the order now.
		 */
		for (int x = 0; x < board.Width(); x++)
		{
			for (int y = 0; y < board.Height(); y++)
			{
				// Don't add corners or center twice
				if ((x >= centerXStart && x < centerXStart + centerWidth) && (y >= centerYStart && y < centerYStart + centerHeight))
					continue;
				if ((x == 0 || x == board.Width() - 1) && (y == 0 || y == board.Height() - 1))
					continue;
				pos = new Vector2i(x, y);
				if (board.IsCellEmpty(pos))
					childStates.add(pos);
			}
		}
		
		return childStates;
	}
	
	/**
	 * Return a copy of a board with a specified move made.
	 * @param board The board to copy.
	 * @param playedPiece The type of piece to play.
	 * @param move The position at which to play.
	 * @return A copy of the board with the specified move made. The original board will not be modified.
	 */
	protected ITicTacToeBoard PlayBoard(ITicTacToeBoard board, PieceType playedPiece, Vector2i move)
	{
		ITicTacToeBoard copy = board.Clone();
		copy.Set(playedPiece, move);
		return copy;
	}

	@Override
	public Player GetPlayer() 
	{return Player;}
	
	/**
	 * Determine the type of piece that this AI will play.
	 * @return PieceType.CIRCLE or PieceType.CROSS as corresponds with our player
	 */
	public PieceType GetPieceType()
	{
		switch (GetPlayer()) {
		case CIRCLE:
			return PieceType.CIRCLE;
		case CROSS:
			return PieceType.CROSS;
		default:
			throw new IllegalStateException("AI Player was neither CROSS nor CIRCLE.");
		}
	}
	
	/**
	 * Determine the type of piece that the opponent will play.
	 * @return PieceType.CIRCLE or PieceType.CROSS, the opposite of our player
	 */
	public PieceType GetOpponentPieceType()
	{
		switch (GetPlayer())
		{
		case CIRCLE:
			return PieceType.CROSS;
		case CROSS:
			return PieceType.CIRCLE;
		default:
			throw new IllegalStateException("AI Player was neither CROSS nor CIRCLE.");
		}
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
