package tictactoe.view;

import gamecore.datastructures.vectors.Vector2i;
import tictactoe.model.PieceType;

public class TicTacToeView implements ITicTacToeView {

	public TicTacToeView(int width, int height)
	{
		Width = width;
		Height = height;
	}
	
	@Override
	public void PlacePiece(Vector2i pos, PieceType piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean MakeGolden(Vector2i pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean MoveCursor(Vector2i dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Disposed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector2i CursorPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Width() 
	{return Width;}

	@Override
	public int Height()
	{return Height;}
	
	/**
	 * The height of this view.
	 */
	protected int Height;
	
	/**
	 * The width of this view.
	 */
	protected int Width;

}
