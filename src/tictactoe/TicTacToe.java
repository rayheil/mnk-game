package tictactoe;

import java.awt.event.KeyEvent;

import gamecore.GameEngine;
import gamecore.input.InputManager;
import gamecore.input.InputMap;
import tictactoe.controller.ITicTacToeController;
import tictactoe.controller.TicTacToeController;
import tictactoe.model.ITicTacToeBoard; // TODO should I really import these here? I feel iffy about it
import tictactoe.model.TicTacToeBoard; // TODO this one too

/**
 * Plays tic tac toe.
 * @author Dawn Nye
 */
public class TicTacToe extends GameEngine
{
	/**
	 * Creates a new tic tac toe game with a width, height, and win_len.
	 * @param width The width of the board. This value must be positive.
	 * @param height The height of the board. This value must be positive.
	 * @param win_len The required winning length of the board. This must be positive and at most {@code width} and at most {@code height}.
	 * @throws IllegalArgumentException Thrown if {@code width}, {@code height}, or {@code win_len} is nonpositive or if {@code win_len} is greater than either {@code width} and {@code height}.
	 */
	public TicTacToe(int width, int height, int win_len)
	{
		this(width, height, win_len, false, 0, false, 0);
	}
	
	/**
	 * Creates a new tic tac toe game with a width, height, and win_len, and allows one or both players
	 * to be controlled by an AI of specified difficulty.
	 * @param width The width of the board. This value must be positive.
	 * @param height The height of the board. This value must be positive.
	 * @param win_len The required winning length of the board. This must be positive and at most {@code width} and at most {@code height}.
	 * @param player1_human Whether the first player is controlled by a human. If false, player 1 will be an AI with difficulty determined by the next patameter.
	 * @param ai1_difficulty If player 1 is not human, the difficulty of the AI controlling them.
	 * @param player2_human Whether the first player is controlled by a human. If false, player 2 will be an AI with difficulty determined by the next patameter.
	 * @param ai2_difficulty If player 2 is not human, the difficulty of the AI controlling them.
	 * @throws IllegalArgumentException Thrown if {@code width}, {@code height}, or {@code win_len} is nonpositive or if {@code win_len} is greater than either {@code width} and {@code height}.
	 */
	public TicTacToe(int width, int height, int win_len, boolean player1_human, int ai1_difficulty, boolean player2_human, int ai2_difficulty)
	{
		// Tell GameEngine to initialize a game window with the right size
		super("CSC 207 Tic Tac Toe",null,168 * width + 16,168 * height + 39);
		
		if (width < 1 || height < 1 || win_len < 1)
			throw new IllegalArgumentException("Nonpositive arguments for width, height, or win_len are illegal.");
		if (win_len > width || win_len > height)
			throw new IllegalArgumentException("It is illegal for win_len to be greater than either width or height.");
		
		Width = width;
		Height = height;
		WinningLength = win_len;
		Player1Human = player1_human;
		Player1AIDifficulty = ai1_difficulty;
		Player2Human = player2_human;
		Player2AIDifficulty = ai2_difficulty;
		
		return;
	}
	
	@Override protected void Initialize()
	{
		// Initialize input data
		Input = new InputManager();
		InputMap Bindings = InputMap.Map();
		
		// Add the input manager to the game and make it as a service
		AddComponent(Input);
		AddService(Input);
		
		// Initialize some key bindings
		Bindings.AddKeyBinding("Exit",KeyEvent.VK_ESCAPE);
		Bindings.AddKeyBinding("Reset",KeyEvent.VK_TAB);
		
		Bindings.AddKeyBinding("m_Select",KeyEvent.VK_SPACE);
		Bindings.AddKeyBinding("a_Select",KeyEvent.VK_ENTER);
		Bindings.AddORBinding("Select","m_Select","a_Select");
		
		Bindings.AddKeyBinding("m_Left",KeyEvent.VK_LEFT);
		Bindings.AddKeyBinding("a_Left",KeyEvent.VK_A);
		Bindings.AddORBinding("Left","m_Left","a_Left");
		
		Bindings.AddKeyBinding("m_Right",KeyEvent.VK_RIGHT);
		Bindings.AddKeyBinding("a_Right",KeyEvent.VK_D);
		Bindings.AddORBinding("Right","m_Right","a_Right");
		
		Bindings.AddKeyBinding("m_Up",KeyEvent.VK_UP);
		Bindings.AddKeyBinding("a_Up",KeyEvent.VK_W);
		Bindings.AddORBinding("Up","m_Up","a_Up");
		
		Bindings.AddKeyBinding("m_Down",KeyEvent.VK_DOWN);
		Bindings.AddKeyBinding("a_Down",KeyEvent.VK_S);
		Bindings.AddORBinding("Down","m_Down","a_Down");
		
		// Initialize some input tracking
		Input.AddInput("Exit",() -> Bindings.GetBinding("Exit").DigitalEvaluation.Evaluate());
		Input.AddInput("Reset",() -> Bindings.GetBinding("Reset").DigitalEvaluation.Evaluate(),true);
		Input.AddInput("Left",() -> Bindings.GetBinding("Left").DigitalEvaluation.Evaluate(),true);
		Input.AddInput("Right",() -> Bindings.GetBinding("Right").DigitalEvaluation.Evaluate(),true);
		Input.AddInput("Up",() -> Bindings.GetBinding("Up").DigitalEvaluation.Evaluate(),true);
		Input.AddInput("Down",() -> Bindings.GetBinding("Down").DigitalEvaluation.Evaluate(),true);
		Input.AddInput("Select",() -> Bindings.GetBinding("Select").DigitalEvaluation.Evaluate(),true);
		
		// Construct the controller
		Controller = new TicTacToeController(Width,Height,WinningLength,Player1Human, Player1AIDifficulty, Player2Human, Player2AIDifficulty);
		AddComponent(Controller);
		
		// Construct the board
		Board = new TicTacToeBoard(Width, Height, WinningLength);
						
		return;
	}
	
	@Override protected void LateInitialize()
	{return;}
	
	@Override protected void Update(long delta)
	{return;}
	
	@Override protected void LateUpdate(long delta)
	{
		if(Input.GracelessInputSatisfied("Exit"))
			Quit();
		else if(Input.GracelessInputSatisfied("Reset"))
			Controller.ResetGame();
		
		return;
	}
	
	@Override protected void Dispose()
	{return;}
	
	@Override protected void LateDispose()
	{return;}
	
	/**
	 * The Tic Tac Toe controller.
	 */
	protected ITicTacToeController Controller;
	
	/**
	 * The Tic Tac Toe board.
	 */
	protected ITicTacToeBoard Board;
	
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
	 * Whether player 1 is human.
	 */
	protected boolean Player1Human;
	
	/**
	 * AI player 1 difficulty.
	 */
	protected int Player1AIDifficulty;
	
	/**
	 * Whether player 2 is human.
	 */
	protected boolean Player2Human;
	
	/**
	 * AI player 2 difficulty.
	 */
	protected int Player2AIDifficulty;
}