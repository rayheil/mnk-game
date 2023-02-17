package tictactoe.model;

import gamecore.datastructures.vectors.Vector2i;

/**
 * An event from a Tic Tac Toe game.
 * @author Dawn Nye
 */
public class TicTacToeEvent
{
	/**
	 * Creates a clear event.
	 */
	public TicTacToeEvent()
	{
		Type = EventType.CLEAR;
		
		PlacedPieceType = PieceType.NONE;
		PiecePosition = null;
		
		RemovedPosition = null;
		
		Winner = Player.NULL;
		WinningSet = null;
		
		return;
	}
	
	/**
	 * Creates a piece removal event.
	 * @param pos The position the piece was removed from.
	 */
	public TicTacToeEvent(Vector2i pos)
	{
		Type = EventType.PIECE_REMOVAL;
		
		PlacedPieceType = PieceType.NONE;
		PiecePosition = null;
		
		RemovedPosition = pos;
		
		Winner = Player.NULL;
		WinningSet = null;
		
		return;
	}
	
	/**
	 * Creates a piece placement event.
	 * @param pos The position the piece was placed at.
	 * @param piece The piece which was placed (can be NONE).
	 */
	public TicTacToeEvent(Vector2i pos, PieceType piece)
	{
		Type = EventType.PIECE_PLACEMENT;
		
		PlacedPieceType = piece;
		PiecePosition = pos;
		
		RemovedPosition = null;
		
		Winner = Player.NULL;
		WinningSet = null;
		
		return;
	}
	
	/**
	 * Creates a game over event.
	 * @param winner The winning player.
	 * @param win_set The winning set.
	 */
	public TicTacToeEvent(Player winner, Iterable<Vector2i> win_set)
	{
		Type = EventType.GAME_OVER;
		
		PlacedPieceType = PieceType.NONE;
		PiecePosition = null;
		
		RemovedPosition = null;
		
		Winner = winner;
		WinningSet = Winner == Player.NEITHER ? null : win_set; // Ensure that the winning set is null if the game is a tie
		
		return;
	}
	
	/**
	 * If true, then this is a piece placement event.
	 * The values of interest for this event type are PlacedPieceType and PiecePosition.
	 */
	public boolean IsPiecePlacementEvent()
	{return Type == EventType.PIECE_PLACEMENT;}
	
	/**
	 * If true, then this is a clear event.
	 * This occurs when all pieces are removed from the board simultaneously.
	 * There are no associated values with this event.
	 */
	public boolean IsClearEvent()
	{return Type == EventType.CLEAR;}
	
	/**
	 * If true, then this is a piece removal event.
	 * The value of interest for this event type is RemovedPosition.
	 */
	public boolean IsRemoveEvent()
	{return Type == EventType.PIECE_REMOVAL;}
	
	/**
	 * If true, then this is a game over event.
	 * If values of interest for this event type are Winner and WinningSet.
	 */
	public boolean IsGameOverEvent()
	{return Type == EventType.GAME_OVER;}
	
	/**
	 * This is the type of event this represents.
	 * For more information, check the IsX functions.
	 */
	public final EventType Type;
	
	/**
	 * If this is a piece placement event, then this contains what type of piece was placed.
	 */
	public final PieceType PlacedPieceType;
	
	/**
	 * If this is a piece placement event, then this contains the position the piece was placed at.
	 */
	public final Vector2i PiecePosition;
	
	/**
	 * If this is a piece removal event, then this contains the position that was removed.
	 */
	public final Vector2i RemovedPosition;
	
	/**
	 * If this is a game over event, then this contains the winning player.
	 */
	public final Player Winner;
	
	/**
	 * If this is a game over event, then this contains the winning set.
	 * If the game is a draw, this value will be null.
	 */
	public final Iterable<Vector2i> WinningSet;
	
	/**
	 * Represents a type of Tic Tac Toe event.
	 * @author Dawn Nye
	 */
	public enum EventType
	{
		PIECE_PLACEMENT,
		CLEAR,
		PIECE_REMOVAL,
		GAME_OVER
	}
}
