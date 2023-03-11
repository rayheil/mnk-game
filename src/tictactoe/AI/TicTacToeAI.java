package tictactoe.AI;

import java.util.Iterator;
import java.util.NoSuchElementException;

import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.Player;
import tictactoe.model.PieceType;

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
		
		if (!player.equals(tictactoe.model.Player.CIRCLE) && !player.equals(tictactoe.model.Player.CROSS))
			throw new IllegalArgumentException("Player must be either CIRCLE or CROSS.");
		
		Player = player;
		Difficulty = difficulty;
		// TODO the rest of the dang constructor lol
		// is it even a thing that happens? this thing has so few required attributes.
	}
	
	@Override
	public Vector2i GetNextMove(ITicTacToeBoard board)
	{
		if (board.IsFinished())
			throw new IllegalStateException("Board is finished and has no next move.");
		
		double best_score = Double.NEGATIVE_INFINITY;
		Vector2i best_move = null;
		// Search every child state of this board, keeping track of which gets the best minimax score when maximizing
		for (Vector2i move : LINQ.Where(board.IndexSet(), t -> board.IsCellEmpty(t)))
		{
			ITicTacToeBoard cloned = board.Clone();
			cloned.Set(GetPieceType(), move);
			double score = Minimax(cloned, Difficulty, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
			if (score > best_score) {
				best_score = score;
				best_move = move;
			}
		}
		return best_move;
	}
	
	/**
	 * Perform the minimax algorithm and return the best value.
	 * TODO not seeing how this connects to the program. I need not only the most correct value,
	 * but also what move it corresponds to. So maybe need to track that too. But I really don't know.
	 * @param state The current state of the board.
	 * @param depth The depth to search.
	 * @param alpha The current best maximum.
	 * @param beta The current worst minimum.
	 * @param maximizing Whether we start by maximizing or by minimizing
	 * @return The min or max value available from this board state.
	 */
	protected double Minimax(ITicTacToeBoard state, int depth, double alpha, double beta, boolean maximizing)
	{
		if (depth == 0 || state.IsFinished()) {
			return EvaluateState(state);
		}
		
		if (maximizing) {
			double max = Double.NEGATIVE_INFINITY;
			for (ITicTacToeBoard next : GetChildStates(state, GetPieceType())) {
				max = Double.max(max, Minimax(next, depth-1, alpha, beta, false));
				// If we did too well the minimizer will never choose this, prune
				if (max > beta)
					break;
				alpha = Double.max(alpha, max);
			}
			return max;
		}
		else {
			double min = Double.POSITIVE_INFINITY;
			for (ITicTacToeBoard next : GetChildStates(state, GetOpponentPieceType())) {
				min = Double.min(min, Minimax(next, depth-1, alpha, beta, true));
				// If we did too poorly the maximizer will never choose this, prune
				if (min < alpha)
					break;
				beta = Double.min(beta, min);
			}
			return min;
		}
	}
	
	/**
	 * 
	 */
	protected double EvaluateState(ITicTacToeBoard state)
	{
		// Return inf if we won, -inf if we lost, and 0 if we tied.
		if (state.IsFinished())
		{
			if (state.Victor().equals(this.Player))
				return Double.POSITIVE_INFINITY;
			else if (state.Victor().equals(tictactoe.model.Player.NEITHER))
				return 0;
			else
				return Double.NEGATIVE_INFINITY;
		}
		
		// Count up all the squares and weight them by value.
		// TODO this is terrible, but something bigger is broken and
		// needs fixing before I handle this
		int value = 0;
		for (Vector2i pos : state.IndexSet(true)) {
			// If the square is ours it has a modifier of 1 and helps the state's score,
			// If it is theirs it has a modifier of -1 and hurts the state's score.
			int modifier = state.Get(pos) == GetPieceType() ? 1 : -1;
			
			// Center squares do not have X or Y coordinate along an edge
			if ((pos.X > 0 && pos.X < state.Width() - 1) && (pos.Y > 0 && pos.Y < state.Height() - 1))
				value += modifier * CenterWeight;
			
			// Corners have both X and Y coordinates along an edge
			else if ((pos.X == 0 || pos.X == state.Width() - 1) && (pos.Y == 0 || pos.Y == state.Height() - 1))
				value += modifier * CornerWeight;
			
			// Edges have exactly one coordinate along an edge
			else
				value += modifier * EdgeWeight;
		}
		
		return value;
	}
	
	/**
	 * Return an iterator containing all the possible play states from the current state
	 * @param board The current board state.
	 * @param played The type of piece that will be played.
	 * @return Iterable over all empty cells in the board
	 */
	protected Iterable<ITicTacToeBoard> GetChildStates(ITicTacToeBoard board, PieceType playedPiece)
	{
		return new Iterable<ITicTacToeBoard>()
		{
			@Override
			public Iterator<ITicTacToeBoard> iterator() {
				return new Iterator<ITicTacToeBoard>()
				{
					@Override
					public boolean hasNext() {
						return Iter.hasNext();
					}

					@Override
					public ITicTacToeBoard next() {
						if (!Iter.hasNext())
							throw new NoSuchElementException();
						
						ITicTacToeBoard playedBoard = board.Clone();
						playedBoard.Set(playedPiece, Iter.next());
						return playedBoard;
					}
					
					// TODO will Where ever be null? Would cause a null pointer exception I don't wanna handle
					protected Iterator<Vector2i> Iter = LINQ.Where(board.IndexSet(), t -> board.IsCellEmpty(t)).iterator();
				};
			}
			
		};
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
		switch (GetPlayer())
		{
		case CIRCLE:
			return PieceType.CIRCLE;
		case CROSS:
			return PieceType.CROSS;
		default:
			// If player is neither circle nor cross, the constructor will fail.
			return null;
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
			// If player is neither circle nor cross, the constructor will fail.
			return null;
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
	
	/**
	 * Weights for the locations of cells on the board, used
	 * to decide the value of a move.
	 */
	protected final int CenterWeight = 3;
	protected final int CornerWeight = 2;
	protected final int EdgeWeight = 1;
}
