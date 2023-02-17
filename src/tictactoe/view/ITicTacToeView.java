package tictactoe.view;

import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.PieceType;

/**
 * Contains all of the actual drawing logic for a Tic Tac Toe board.
 * It can (and should) delegate subdrawing routines to other game components, but it is in charge of managing them.
 * @author Dawn Nye
 */
public interface ITicTacToeView
{
	/**
	 * Places a piece on the board in cell {@code pos}.
	 * @param pos The cell of the board to place the piece.
	 * @param piece The piece to place. To clear the cell, use NONE.
	 * @throws NullPointerException Thrown if {@code pos} or {@code piece} is null.
	 */
	public void PlacePiece(Vector2i pos, PieceType piece);
	
	/**
	 * Makes the piece in cell {@code pos} golden.
	 * @param pos The cell of the board containing the piece to make golden.
	 * @return Returns true if a piece was made golden and false otherwise. This should only fail if no piece is in the cell or if the piece is already golden.
	 * @throws NullPointerException Thrown if {@code pos} is null.
	 */
	public boolean MakeGolden(Vector2i pos);
	
	/**
	 * Clears the board by removing all pieces from its cells.
	 */
	public void Clear();
	
	/**
	 * Moves the cursor in the direciton {@code dir}.
	 * The cursor can only move one square at a time (diagonals count as one square).
	 * If the cursor would move diagonally off the board but can move a space horizontally or vertically, the latter movement will occur.
	 * @param dir The direction to move the cursor. Only the sign of each of its values will be used, not its magnitude.
	 * @return Returns true if the cursor moved (if not as far as originally intended) and false otherwise, such as if it attempted to move out of bounds.
	 * @throws NullPointerException Thrown if {@code dir} is null.
	 */
	public boolean MoveCursor(Vector2i dir);
	
	/**
	 * Removes any components this view added to the game engine from the engine.
	 * This operation cannot be undone.
	 */
	public void Dispose();
	
	/**
	 * If true, then this view has been disposed. Returns false otherwise.
	 */
	public boolean Disposed();
	
	/**
	 * Obtains the current cursor position.
	 */
	public Vector2i CursorPosition();
	
	/**
	 * Obtains the width of the game board (in cells).
	 */
	public int Width();
	
	/**
	 * Obtains the height of the game board (in cells).
	 */
	public int Height();
}
