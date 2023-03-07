package tictactoe.controller;

import java.io.File;

import gamecore.GameEngine;
import gamecore.datastructures.vectors.Vector2i;
import gamecore.gui.gamecomponents.MultiImageComponent;
import gamecore.input.InputManager;
import tictactoe.AI.ITicTacToeAI;
import tictactoe.AI.TicTacToeAI;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.PieceType;
import tictactoe.model.Player;
import tictactoe.model.TicTacToeBoard;
import tictactoe.model.TicTacToeEvent;
import tictactoe.view.ITicTacToeView;
import tictactoe.view.TicTacToeView;

/**
 * Controls a Tic Tac Toe game.
 * @author Dawn Nye
 * @author Ray Heil
 */
public class TicTacToeController implements ITicTacToeController
{
	/**
	 * Creates a new Tic Tac Toe controller with two human players.
	 * @param width The width of the game board.
	 * @param height The height of the game board.
	 * @param win_len The required combo length to have a winning position.
	 */
	public TicTacToeController(int width, int height, int win_len)
	{
		this(width,height,win_len,true,0,true,0);
		return;
	}
	
	/**
	 * Creates a new Tic Tac Toe controller.
	 * @param width The width of the game board.
	 * @param height The height of the game board.
	 * @param win_len The required combo length to have a winning position.
	 * @param player1_human If true, then the first player is human. If false, the first player is an AI.
	 * @param ai1_difficulty If player 1 is an AI, this is its difficulty.
	 * @param player2_human If true, then the second player is human. If false, the second player is an AI.
	 * @param ai1_difficulty If player 2 is an AI, this is its difficulty.
	 */
	public TicTacToeController(int width, int height, int win_len, boolean player1_human, int ai1_difficulty, boolean player2_human, int ai2_difficulty)
	{
		Width = width;
		Height = height;
		WinningLength = win_len;
		
		IsPlayerOneHuman = player1_human;
		
		if(!IsPlayerOneHuman)
			PlayerOneAI = new TicTacToeAI(Player.CROSS,ai1_difficulty);
		
		IsPlayerTwoHuman = player2_human;
		
		if(!IsPlayerTwoHuman)
			PlayerTwoAI = new TicTacToeAI(Player.CIRCLE,ai2_difficulty);
		
		return;
	}
	
	public void Initialize()
	{
		// Create the board
		Model = new TicTacToeBoard(Width,Height,WinningLength);
		Model.Subscribe(this);
		
		// Create the view
		View = new TicTacToeView(Width,Height);
		
		// Initialize the game state
		// TODO OK BUT HOW
		// TODO here is where we need to initialize stuff
		
		Initialized = true;
		return;
	}
	
	public boolean Initialized()
	{return Initialized;}
	
	public void Update(long delta)
	{
		int delta_x = 0;
		int delta_y = 0;
		
		// Update our input manager
		Input = GameEngine.Game().GetService(InputManager.class);
		
		// Allow the player to move the cursor as desired
		if (Input.GracelessInputSatisfied("Left"))
			delta_x--;
		
		if (Input.GracelessInputSatisfied("Right"))
			delta_x++;
		
		if (Input.GracelessInputSatisfied("Down"))
			delta_y--;
		
		if (Input.GracelessInputSatisfied("Up"))
			delta_y++;
		
		Vector2i delta_p = new Vector2i(delta_x, delta_y);
		
		if (!delta_p.IsZero())
			View.MoveCursor(delta_p);
		
		// Animate victory if necessary
		// TODO make Victor() and IsFinished more efficient, by not checking each time
		// and maybe doing the better win detecting function on piece placement.
		if (Model.IsFinished()) {
			for (Vector2i index : Model.WinningSet()) {
				View.MakeGolden(index);
			}
		}
		
		// Handle AI logic before human selections so that we have at least one frame after a human selection (if any humans exist) before the AI makes its move
		// This frame is important because the AI may lag the game, and the human will want to see their move
		// TODO AI hmmm how SIMPLE
		
		// Now process selections (we do this after victory animation so that we don't skip a frame in the animation)
		if (Input.GracelessInputSatisfied("Select")) {
			PieceType Piece = PieceType.CROSS;
			View.PlacePiece(View.CursorPosition(), Piece);
			Model.Set(Piece, View.CursorPosition());
		}
 		
		return;
	}
	
	public void Dispose()
	{
		if(Disposed())
			return;
		
		if(!View.Disposed())
			View.Dispose();
		
		Disposed = true;
		return;
	}
	
	public boolean Disposed()
	{return Disposed;}
	
	public void OnNext(TicTacToeEvent event)
	{
		switch (event.Type) {
		case CLEAR:
			View.Clear();
			break;
		case GAME_OVER:
			// TODO hers does this SLOWLY though.
			for (Vector2i pos : event.WinningSet)
				View.MakeGolden(pos);
			break;
		case PIECE_PLACEMENT:
			View.PlacePiece(event.PiecePosition, event.PlacedPieceType);
			break;
		case PIECE_REMOVAL:
			View.PlacePiece(event.RemovedPosition, PieceType.NONE);
			break;
		default:
			break;
		}
	}
	
	public void OnError(Exception e)
	{return;}
	
	public void OnCompleted()
	{return;}
	
	public void ResetGame()
	{
		// TODO need to write this, but nowhere near ready to do so
		
		return;
	}
	
	public Player ActivePlayer()
	{return ActivePlayer;}
	
	public int Width()
	{return Width;}
	
	public int Height()
	{return Height;}
	
	public int WinningLength()
	{return WinningLength;}
	
	/**
	 * The Tic Tac Toe board.
	 */
	public ITicTacToeBoard Model;
	
	/**
	 * The Tic Tac Toe view.
	 */
	public ITicTacToeView View;
	
	/**
	 * The input manager for the game.
	 * This is registered as a service.
	 */
	protected InputManager Input;
	
	/**
	 * The width of the board.
	 */
	protected int Width;
	
	/**
	 * The height of the board.
	 */
	protected int Height;
	
	/**
	 * The winning length of the board.
	 */
	protected int WinningLength;
	
	/**
	 * If true, then player 1 (the cross player) is human controlled.
	 */
	protected boolean IsPlayerOneHuman;
	
	/**
	 * If player 1 is controlled by an AI, this is it.
	 */
	protected ITicTacToeAI PlayerOneAI;
	
	/**
	 * If true, then player 2 (the circle player) is human controlled.
	 */
	protected boolean IsPlayerTwoHuman;
	
	/**
	 * If player 2 is controlled by an AI, this is it.
	 */
	protected ITicTacToeAI PlayerTwoAI;
	
	/**
	 * The active player (if any).
	 */
	protected Player ActivePlayer;
	
	/**
	 * The amount of time delay between goldenizations upon victory.
	 */
	protected final long GoldenLag = 100;
	
	/**
	 * If true, then this component has been initialized.
	 */
	protected boolean Initialized;
	
	/**
	 * If true, then this component has been disposed.
	 */
	protected boolean Disposed;
}
