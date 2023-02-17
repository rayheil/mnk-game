package tictactoe.controller;

import gamecore.IUpdatable;
import gamecore.observe.IObserver;
import tictactoe.model.Player;
import tictactoe.model.TicTacToeEvent;

/**
 * Controls the model and view of the Tic Tac Toe game.
 * @author Dawn Nye
 */
public interface ITicTacToeController extends IUpdatable, IObserver<TicTacToeEvent>
{
	/**
	 * Resets the game.
	 */
	public void ResetGame();
	
	/**
	 * Obtains the active player (if any).
	 * This value will be NEITHER if the game is over.
	 */
	public Player ActivePlayer();
	
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
