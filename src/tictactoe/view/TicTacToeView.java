package tictactoe.view;

import java.io.File;

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
		
		MultiImageComponent temp = new MultiImageComponent();
		temp.AddImage(new File("assets/images/Cross.png"));
		System.out.println("yep we initialized it but why does it not show.");
		temp.SetSelectedImage(0);
		temp.Show();
		
		/*for (int x = 0; x < Height; x++) {
			for (int y = 0; y < Width; y++) {
				MultiImageComponent current = Pieces[x][y];
				current = new MultiImageComponent();
				
				// Add all piece images, index 0 thru 3 IDK HELPPPP
				current.AddImage(new File("assets/images/Circle.png"));
				current.AddImage(new File("assets/images/Cross.png"));
				current.AddImage(new File("assets/images/Golden Circle.png"));
				current.AddImage(new File("assets/images/Golden Cross.png"));
				current.Translate(30.0, 30.0);
				current.Show();
				current.SetSelectedImage(0);			
			}
		}*/
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

}
