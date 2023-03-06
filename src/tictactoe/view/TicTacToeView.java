package tictactoe.view;

import java.io.File;

import gamecore.GameEngine;
import gamecore.datastructures.vectors.Vector2i;
import gamecore.gui.gamecomponents.MultiImageComponent;

import tictactoe.model.PieceType;

public class TicTacToeView implements ITicTacToeView {

	public TicTacToeView(int width, int height)
	{
		Width = width;
		Height = height;
		Disposed = false;
		CursorPosition = new Vector2i(0, 0);
		Pieces = new MultiImageComponent[Height][Width];		
		Grid = new MultiImageComponent[Height][Width];
		
		/* Place pieces and cells around the board.
		 * The cells will immediately be visible, but
		 * the pieces will be hidden.
		 */
		for (int y = 0; y < Height; y++) {
			for (int x = 0; x < Width; x++) {
				// Initialize the correct array index with a MultiImageComponent and 
				// tell the game engine to track it
				Pieces[y][x] = new MultiImageComponent();
				Grid[y][x] = new MultiImageComponent();
				GameEngine.Game().AddComponent(Pieces[y][x]);
				GameEngine.Game().AddComponent(Grid[y][x]);
				
				// Add the relevant images to Pieces[y][x] and place it in the right location
				Pieces[y][x].Translate(168 * x + 10, 168 * y + 22.5);
				Pieces[y][x].AddImage(new File("assets/images/Circle.png"));
				Pieces[y][x].AddImage(new File("assets/images/Cross.png"));
				Pieces[y][x].AddImage(new File("assets/images/Golden Circle.png"));
				Pieces[y][x].AddImage(new File("assets/images/Golden Cross.png"));
				
				// The grid cell only requires one image, and is shown right away
				Grid[y][x].Translate(168 * x + 8, 168 * y + 19.5);
				Grid[y][x].AddImage(new File("assets/images/GridCell.png"));
				Grid[y][x].SetSelectedImage(0);	
			}
		}
		
		PlacePiece(new Vector2i(1, 1), PieceType.CROSS);
		PlacePiece(new Vector2i(1, 2), PieceType.CIRCLE);
		PlacePiece(new Vector2i(3, 1), PieceType.CROSS);


	}
	
	@Override
	public void PlacePiece(Vector2i pos, PieceType piece) {
		if (Disposed)
			return;
		
		if (pos == null || piece == null)
			throw new NullPointerException();
		
		// TODO Bounds checking on pos is probably needed, but fuck do I not want to
		if (pos.X < 0 || pos.X > Width() || pos.Y < 0 || pos.Y > Height())
			throw new IndexOutOfBoundsException();
		
		switch (piece) {
		case CIRCLE:
			Pieces[pos.Y][pos.X].SetSelectedImage(0);
			break;
		case CROSS:
			Pieces[pos.Y][pos.X].SetSelectedImage(1);
			break;
		case NONE:
			Pieces[pos.Y][pos.X].Hide();
			break;
		default:
			break;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public boolean MakeGolden(Vector2i pos) {
		if (Disposed)
			return false;
		
		if (pos == null)
			throw new NullPointerException();
		
		switch (Pieces[pos.X][pos.Y].GetSelectedImage())
		{
		case 0: // Circle
			Pieces[pos.X][pos.Y].SetSelectedImage(2);
			break;
		case 1: // Cross
			Pieces[pos.X][pos.Y].SetSelectedImage(3);
			break;
		default:
			return false;
		}
		
		return true;
	}

	@Override
	public void Clear() {
		for (int x = 0; x < Height(); x++)
			for (int y = 0; y < Width(); y++)
				PlacePiece(new Vector2i(x, y), PieceType.NONE);
	}

	@Override
	public boolean MoveCursor(Vector2i dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Dispose() {
		Disposed = true;
		// TODO how do we do this thru the game engine?		
	}

	@Override
	public boolean Disposed()
	{return Disposed;}

	@Override
	public Vector2i CursorPosition() 
	{return CursorPosition;}

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
	
	/**
	 * The position of the cursor on this view (zero-indexed).
	 */
	protected Vector2i CursorPosition;
	
	/**
	 * Whether this view has been disposed.
	 */
	protected boolean Disposed;
	
	/**
	 * Height by width array containing MultiImageComponents to display pieces
	 */
	protected MultiImageComponent[][] Pieces;
	
	/**
	 * Height by width array containing MultiImageComponents to display the grid
	 */
	protected MultiImageComponent[][] Grid;

}
