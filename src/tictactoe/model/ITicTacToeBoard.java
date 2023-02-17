package tictactoe.model;

import gamecore.datastructures.grids.IGrid;
import gamecore.datastructures.vectors.Vector2i;
import gamecore.observe.IObservable;

/**
 * The outline of a Tic Tac Toe board (not necessarily a 3x3 board).
 * Positions are zero indexed in along both the x and y axis.
 * Each time a piece is placed or removed, an event is issued.
 * When the game ends, an OnCompleted event is NOT issued.
 * Instead, an appropriate ordinary event is issued since the game is allowed to be reset.
 * @author Dawn Nye
 */
public interface ITicTacToeBoard extends IGrid<Vector2i,PieceType>, IObservable<TicTacToeEvent>
{
	/**
	 * Clones this board.
	 * The new board does not contain any of this boards subscribers to its events.
	 * @return Returns a deep copy of this board.
	 */
	public ITicTacToeBoard Clone();
	
	/**
	 * Determines if this game is finished.
	 * A game is finished if no more moves can be made or if a player has at least {@code WinningLength()} number of pieces in a row horizontally, vertically, or diagonally.
	 * @return Returns true if the game is over and false otherwise.
	 */
	public boolean IsFinished();
	
	/**
	 * Obtains a winning set of positions if one exists.
	 * @return If no one has won, null is returned.
	 * Otherwise, a set of positions representing a winning set for the winning player is returned.
	 * For example, in a 3x3 game with a winning length of 3, the winning player may win with a diagonal, so an example return value would be the set {(0,0),(1,1),(2,2)}. 
	 */
	public Iterable<Vector2i> WinningSet();
	
	/**
	 * Obtains a winning set of positions using {@code use_me} if one exists.
	 * @param use_me This must be part of the winning set.
	 * @return If no one has won using {@code use_me}, null is returned.
	 * Otherwise, a set of positions representing a winning set for the winning player is returned.
	 * For example, in a 3x3 game with a winning length of 3, the winning player may win with a diagonal, so an example return value would be the set {(0,0),(1,1),(2,2)}. 
	 */
	public Iterable<Vector2i> WinningSet(Vector2i use_me);
	
	/**
	 * Determines the winner of this game.
	 * @return If the game is not finished, {@code NULL} is returned. If the game is a tie, {@code NEITHER} is returned. Otherwise, the winning player is return.
	 */
	public Player Victor();
	
	/**
	 * Obtains the width of the board.
	 */
	public int Width();
	
	/**
	 * Obtains the height of the board.
	 */
	public int Height();
	
	/**
	 * Obtains the number of pieces a player needs in a row to win.
	 */
	public int WinningLength();
}
