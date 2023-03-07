package tictactoe.view;

import java.io.File;

import gamecore.GameEngine;
import gamecore.datastructures.vectors.Vector2i;
import gamecore.gui.gamecomponents.MultiImageComponent;

import tictactoe.model.PieceType;

public class TicTacToeView implements ITicTacToeView {

	/**
	 * Construct a new view with the given width and height.
	 * @param width The width of the view in cells
	 * @param height The height of the view in cells
	 */
	public TicTacToeView(int width, int height)
	{
		Width = width;
		Height = height;
		Disposed = false;
		CursorPosition = new Vector2i(0, 0);
		Pieces = new MultiImageComponent[Height][Width];		
		Grid = new MultiImageComponent[Height][Width];
		Cursor = new MultiImageComponent();
		
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
				Pieces[y][x].Translate(CellSize * x + 10, CellSize * y + 22.5);
				Pieces[y][x].AddImage(new File("assets/images/Circle.png"));
				Pieces[y][x].AddImage(new File("assets/images/Cross.png"));
				Pieces[y][x].AddImage(new File("assets/images/Golden Circle.png"));
				Pieces[y][x].AddImage(new File("assets/images/Golden Cross.png"));
				
				// The grid cell only requires one image, and is shown right away
				Grid[y][x].Translate(CellSize * x + 8, CellSize * y + 19.5);
				Grid[y][x].AddImage(new File("assets/images/GridCell.png"));
				Grid[y][x].SetSelectedImage(0);	
			}
		}
		
		/*
		 * Initialize the cursor and add it as a component
		 */
		GameEngine.Game().AddComponent(Cursor);
		Cursor.AddImage(new File("assets/images/Selection.png"));
		Cursor.SetSelectedImage(0);
		Cursor.Translate(11, 22.5);
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
		
		switch (Pieces[pos.Y][pos.X].GetSelectedImage())
		{
		case 0: // Circle
			Pieces[pos.Y][pos.X].SetSelectedImage(2);
			break;
		case 1: // Cross
			Pieces[pos.Y][pos.X].SetSelectedImage(3);
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
		boolean moved = false;
				
		// Move in X direction if possible
		if (dir.X > 0 && CursorPosition.X < Width()-1) {
			Cursor.Translate(CellSize, 0);
			CursorPosition = CursorPosition.Add(new Vector2i(1, 0));
			moved = true;
		} else if (dir.X < 0 && CursorPosition.X > 0) {
			Cursor.Translate(-1*CellSize, 0);
			CursorPosition = CursorPosition.Add(new Vector2i(-1, 0));
			moved = true;
		}
		
		// Move in Y direction if possible
		if (dir.Y < 0 && CursorPosition.Y < Height()-1) {
			Cursor.Translate(0, CellSize);
			CursorPosition = CursorPosition.Add(new Vector2i(0, 1));
			moved = true;
		} else if (dir.Y > 0 && CursorPosition.Y > 0) {
			Cursor.Translate(0, -1*CellSize);
			CursorPosition = CursorPosition.Add(new Vector2i(0, -1));
			moved = true;
		}
		
		return moved;
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
	 * The size of a cell in pixels
	 */
	protected final int CellSize = 168;
	
	/**
	 * The position of the cursor on this view (zero-indexed).
	 */
	protected Vector2i CursorPosition;
	
	/**
	 * The player's cursor
	 */
	protected MultiImageComponent Cursor;
	
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
