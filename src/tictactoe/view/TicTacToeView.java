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

		
		/*
		MultiImageComponent TestThing = new MultiImageComponent();
		TestThing.AddImage(new File("assets/images/Circle.png"));
		TestThing.AddImage(new File("assets/images/Cross.png"));
		TestThing.AddImage(new File("assets/images/Golden Circle.png"));
		TestThing.AddImage(new File("assets/images/Golden Cross.png"));
	    //AddComponent(TestThing);
		GameEngine.Game().AddComponent(TestThing); // HOLY SHIT TODO TODO TODO YESSSSs
		TestThing.Translate(30, 30);
		TestThing.SetSelectedImage(0);
		*/
		
		/* Place pieces and cells around the board.
		 * The cells will immediately be visible, but
		 * the pieces will be hidden.
		 */
		for (int y = 0; y < Height; y++) {
			for (int x = 0; x < Width; x++) {
				// Initialize the correct 2d array cell with a MultiImageComponent and 
				// tell the game engine to recognize it.
				MultiImageComponent piece = Pieces[y][x];
				MultiImageComponent cell = Grid[y][x];
				piece = new MultiImageComponent();
				cell = new MultiImageComponent();
				GameEngine.Game().AddComponent(piece);
				GameEngine.Game().AddComponent(cell);
				
				// Add the relevant images to piece and place it in the right location
				piece.Translate(168 * x + 8, 168 * y + 19.5);
				piece.AddImage(new File("assets/images/Circle.png"));
				piece.AddImage(new File("assets/images/Cross.png"));
				piece.AddImage(new File("assets/images/Golden Circle.png"));
				piece.AddImage(new File("assets/images/Golden Cross.png"));
				piece.Hide(); // hidden by default, but I want to make the call explicit
				
				// The grid cell only requires one image, and is shown off the bat.
				cell.Translate(168 * x + 8, 168 * y + 19.5);
				cell.AddImage(new File("assets/images/GridCell.png"));
				cell.SetSelectedImage(0);
				
				
			}
		}
	}
	
	@Override
	public void PlacePiece(Vector2i pos, PieceType piece) {
		if (Disposed) // TODO is this what you do if you're disposed?
			return;
		
		if (pos == null || piece == null)
			throw new NullPointerException();
		
		// TODO Bounds checking on pos is probably needed, but fuck do I not want to
		
		switch (piece) {
		case CIRCLE:
			Pieces[pos.X][pos.Y].SetSelectedImage(0);
			break;
		case CROSS:
			Pieces[pos.X][pos.Y].SetSelectedImage(1);
			break;
		case NONE:
			Pieces[pos.X][pos.Y].Hide();
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
