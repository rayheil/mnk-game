package tictactoe.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;
import gamecore.observe.IObserver;

/**
 * 
 * Implementation of ITicTacToeBoard using a backing 2D array.
 * 
 * @author Ray Heil
 * 
 */
public class TicTacToeBoard implements ITicTacToeBoard {
	
	public TicTacToeBoard(int width, int height, int winningLength)
	{
		this.Board = new PieceType[height][width];
		this.Width = width;
		this.Height = height;
		this.WinningLength = winningLength;
		this.Count = 0;
		this.Observers = new LinkedList<IObserver<TicTacToeEvent>>();
		this.Winner = Player.NULL;
		
		for (int i = 0; i < Height(); i++)
			for (int j = 0; j < Width(); j++)
				Board[i][j] = PieceType.NONE;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IndexOutOfBoundsException Thrown if {@code index} is out of bounds.
	 * @throws NoSuchElementException Thrown if no element exists at ({@code x},{@code y}).
	 * @throws NullPointerException Thrown if {@code index} is null.
	 */
	@Override
	public PieceType Get(Vector2i index) {
		if (index == null)
			throw new NullPointerException();
		
		if (!ContainsIndex(index))
			throw new IndexOutOfBoundsException();
		
		/* If the board initialized correctly there should be no null cells,
		 * but this behavior is specified in the doc so I implemented it to be safe
		 */
		PieceType returnType = Board[index.X][index.Y];
		if (returnType == null)
			throw new NoSuchElementException();
		
		return returnType;
	}
	

	/**
	 * {@inheritDoc}
	 * @throws IndexOutOfBoundsException Thrown if {@code index} is out of bounds.
	 * @throws NullPointerException Thrown {@code index} is null or if {@code t} is null.
	 */
	@Override
	public PieceType Set(PieceType t, Vector2i index) {
		if (t == null || index == null)
			throw new NullPointerException();
		
		if (!ContainsIndex(index))
			throw new IndexOutOfBoundsException();
		
		// Increase count if we are filling a new cell
		if (Get(index) == PieceType.NONE)
			Count++;
		
		Board[index.X][index.Y] = t;
		
		// Notify all observers of PIECE_PLACEMENT
		for (IObserver<TicTacToeEvent> eye : Observers)
			eye.OnNext(new TicTacToeEvent(index, t));
		
		return t;
	}

	/**
	 * {@inheritDoc}
	 * @throws IndexOutOfBoundsException Thrown if {@code index} is out of bounds.
	 * @throws NullPointerException Thrown if {@code index} is null.
	 */
	@Override
	public boolean Remove(Vector2i index) {
		if (index == null)
			throw new NullPointerException();
		
		if (!ContainsIndex(index))
			throw new IndexOutOfBoundsException();
		
		// If the position was already null, we don't remove anything
		if (Board[index.X][index.Y] == PieceType.NONE)
			return false;
	
		Board[index.X][index.Y] = PieceType.NONE;
		Count--;
		
		// Notify all observers of PIECE_REMOVAL
		for (IObserver<TicTacToeEvent> eye : Observers)
			eye.OnNext(new TicTacToeEvent(index));
		
		return true;
	}

	@Override
	public boolean IsCellOccupied(Vector2i index) {
		PieceType t = Get(index);
		return (t.equals(PieceType.CIRCLE) || t.equals(PieceType.CROSS));
	}

	@Override
	public boolean IsCellEmpty(Vector2i index) {
		PieceType t = Get(index);
		return (t.equals(PieceType.NONE));
	}

	@Override
	public Iterable<PieceType> Items() {
		return new Iterable<PieceType>() 
		{
			public Iterator<PieceType> iterator()
			{
				return new Iterator<PieceType>()
				{
					@Override
					public boolean hasNext() {
						return indexIterator.hasNext();
					}

					@Override
					public PieceType next() {
						return Get(indexIterator.next());
					}
					
					/**
					 * An iterator over all valid indexes on the board.
					 */
					protected Iterator<Vector2i> indexIterator = IndexSet().iterator();
				};
			}
		};
	}

	@Override
	public Iterable<Vector2i> IndexSet() {
		return new Iterable<Vector2i>()
		{
			public Iterator<Vector2i> iterator()
			{
				return new Iterator<Vector2i>() 
				{
					@Override
					public boolean hasNext() {	
						return currentIndex < Width() * Height();
					}

					@Override
					public Vector2i next() {
						if (!hasNext())
							throw new NoSuchElementException();
						
						Vector2i returnVector = new Vector2i(currentIndex / Width(), currentIndex % Width());
						currentIndex++;
						return returnVector;
					}
					
					/**
					 * The current index of the iterator. (0 <= currentIndex < Width() * Height())
					 */
					protected int currentIndex = 0;
				};
			}
		};
	}

	@Override
	public Iterable<Vector2i> IndexSet(boolean nonempty) {
		if (nonempty)
			return LINQ.Where(IndexSet(), t -> IsCellOccupied(t));
		return IndexSet();
	}

	@Override
	public Iterable<PieceType> Neighbors(Vector2i index) {
		return new Iterable<PieceType>()
		{
			public Iterator<PieceType> iterator()
			{
				return new Iterator<PieceType>()
				{
					@Override
					public boolean hasNext() {
						return indexIter.hasNext();
					}

					@Override
					public PieceType next() {
						if (!hasNext())
							throw new NoSuchElementException();
						
						return Get(indexIter.next());
					}
					
					/**
					 * Iterator over all neighbor indices, both empty and nonempty
					 */
					Iterator<Vector2i> indexIter = NeighborIndexSet(index).iterator();
				};
			}
		};
	}

	@Override
	public Iterable<Vector2i> NeighborIndexSet(Vector2i index) {
		return new Iterable<Vector2i>() 
		{
			public Iterator<Vector2i> iterator()
			{
				return new Iterator<Vector2i>()
				{
					@Override
					public boolean hasNext() {
						if (next != null)
							return true;
						
						/* Search possible surrounding cells from the top left to the bottom right,
						 * and if they exist let them be the next returned value. This function only
						 * returns false if we have iterated over all possible neighbor cells already.
						 */
						for (; currentCell < 9; currentCell++)
						{
							// Calculate the index of a potential cell based on its relevant position to thisIndex
							Vector2i targetIndex = new Vector2i(thisIndex);
							Vector2i diff = new Vector2i(currentCell / 3 - 1, currentCell % 3 - 1);
							targetIndex.Add(diff);
							
							// Do not include this cell with diff (0, 0) in the neighbors iterator
							if (diff.equals(Vector2i.ZERO))
								continue;
							
							/* If targetIndex is on the Board:
							 *   if nonempty is false, it can always be next
							 *   if nonempty is true,  it can only be next if it is nonempty
							 */
							if (ContainsIndex(targetIndex)) {
								next = targetIndex;
								return true;
							}
						}
						
						return false;
					}

					@Override
					public Vector2i next() {
						if (!hasNext())
							throw new NoSuchElementException();
						
						Vector2i returnVector = next;
						next = null;
						return returnVector;
					}
					
					/**
					 * The index of the cell this iterator is checking the neighbors of.
					 */
					final Vector2i thisIndex = index;
					
					/**
					 * The next index to return.
					 */
					Vector2i next = null;
					
					/**
					 * The current index with relation to the center cell.
					 * 0 is the top left, 8 is the bottom right.
					 */
					int currentCell = 0;
				};
			}
		};
	}

	@Override
	public Iterable<Vector2i> NeighborIndexSet(Vector2i index, boolean nonempty) {
		if (nonempty)
			return LINQ.Where(NeighborIndexSet(index), t -> IsCellOccupied(t));
		return NeighborIndexSet(index);
	}

	@Override
	public boolean ContainsIndex(Vector2i index) {
		return (0 <= index.X && index.X < Height() &&
				0 <= index.Y && index.Y < Width());
	}

	@Override
	public boolean Clear() {
		// Initialize the board again.
		for (int i = 0; i < Height(); i++)
			for (int j = 0; j < Width(); j++)
				Board[i][j] = PieceType.NONE;
		
		// Notify all observers of CLEAR
		for (IObserver<TicTacToeEvent> eye : Observers)
			eye.OnNext(new TicTacToeEvent());
		return true;
	}

	@Override
	public int Count()
	{return Count;}

	@Override
	public int Size() 
	{return Width() * Height();}

	@Override
	public void Subscribe(IObserver<TicTacToeEvent> eye) {
		Observers.add(eye);
	}

	@Override
	public void Unsubscribe(IObserver<TicTacToeEvent> eye) {
		Observers.remove(eye);		
	}

	@Override
	public ITicTacToeBoard Clone() {
		TicTacToeBoard clonedBoard = new TicTacToeBoard(Width(), Height(), WinningLength());
		for (int i = 0; i < Height(); i++) {
			for (int j = 0; j < Width; j++) {
				Vector2i pos = new Vector2i(i, j);
				PieceType t = this.Get(pos);
				clonedBoard.Set(t, pos);
			}
		}
		return clonedBoard;
	}

	@Override
	public boolean IsFinished() {
		Iterable<Vector2i> win_set = WinningSet();
		if (Count() == Size() || win_set != null) {
			for (IObserver<TicTacToeEvent> eye : Observers)
				// TODO this calls winningSet so many times, that is a pain so so so much.
				// I am trying to introduce the "Winner" variable to see if that helps, but I'm not sure it will
				// gotta go to do some math now .-. 
				eye.OnNext(new TicTacToeEvent(Victor(), win_set));
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <b>DON'T USE THIS!</b> This method is much less efficient than the version that takes a Vector2i as input,
	 * but it is provided anyway.
	 */
	@Override
	public Iterable<Vector2i> WinningSet() {
		for (int i = 0; i < Height(); i++) {
			for (int j = 0; j < Width(); j++) {
				Iterable <Vector2i> win_set = WinningSet(new Vector2i(i, j));
				if (win_set != null)
					return win_set;
			}
		}
		return null;
	}

	@Override
	public Iterable<Vector2i> WinningSet(Vector2i use_me) {
		if (use_me == null)
			throw new NullPointerException();
		
		if (Get(use_me).equals(PieceType.NONE))
		   return null;
		
		// Horizontal
		Iterable<Vector2i> currentLine = LongestLine(use_me, new Vector2i(0,1));
		if (LINQ.Count(currentLine) >= WinningLength()) {
			for (IObserver<TicTacToeEvent> eye : Observers)
				eye.OnNext(new TicTacToeEvent(GetPlayer(currentLine.iterator().next()), currentLine));
			return currentLine;
		}
		
		// Vertical
		currentLine = LongestLine(use_me, new Vector2i(1,0));
		if (LINQ.Count(currentLine) >= WinningLength()) {
			for (IObserver<TicTacToeEvent> eye : Observers)
				eye.OnNext(new TicTacToeEvent(GetPlayer(currentLine.iterator().next()), currentLine));
			return currentLine;
		}

		// Diagonal down
		currentLine = LongestLine(use_me, new Vector2i(1,1));
		if (LINQ.Count(currentLine) >= WinningLength()) {
			for (IObserver<TicTacToeEvent> eye : Observers)
				eye.OnNext(new TicTacToeEvent(GetPlayer(currentLine.iterator().next()), currentLine));
			return currentLine;
		}
		
		// Diagonal up
		currentLine = LongestLine(use_me, new Vector2i(-1,1));
		if (LINQ.Count(currentLine) >= WinningLength()) {
			for (IObserver<TicTacToeEvent> eye : Observers)
				eye.OnNext(new TicTacToeEvent(GetPlayer(currentLine.iterator().next()), currentLine));
			return currentLine;
		}

		return null;
	}
	
	/**
	 * Get the player who has played at a certain index, inferring from PieceType.
	 * @param index The index of the position to check.
	 * @return The player whose piece has been played.
	 */
	protected Player GetPlayer(Vector2i index)
	{
		Player returnType = null;
		switch (Get(index))
		{
		case CIRCLE:
			returnType = Player.CIRCLE;
			break;
		case CROSS:
			returnType = Player.CROSS;
			break;
		case NONE:
			returnType = Player.NULL;
			break;
		default:
			throw new NullPointerException();
		}
		return returnType;
	}
	
	/**
	 * Obtain the largest possible line in a direction specified by an offset vector.
	 * @param center The starting point of the line, will be searched on both sides.
	 * @param offset The direction to search in.
	 * @return An iterable containing each cell in the longest line in that direction. Order may not be correct.
	 */
	protected Iterable<Vector2i> LongestLine(Vector2i start, Vector2i offset)
	{
		PieceType searchType = Get(start);
		Vector2i furthestBack = start;

		// Calculate the first cell in this line of cells
		while (ContainsIndex(furthestBack.Subtract(offset)))
		{
			if (Get(furthestBack.Subtract(offset)).equals(searchType)) {
				furthestBack = furthestBack.Subtract(offset);
			} else {
				// If the next cell down is NOT of the same type, we have the furthestBack cell
				break;
			}
		}
		// Need to have a final or effectively final version.
		final Vector2i startVector = furthestBack;
		
		return new Iterable<Vector2i>() 
		{
			public Iterator<Vector2i> iterator()
			{
				return new Iterator<Vector2i>() 
				{
					@Override
					public boolean hasNext() {
						return (ContainsIndex(currentIndex) && Get(currentIndex).equals(searchType));
					}

					@Override
					public Vector2i next() {
						if (!hasNext())
							throw new NoSuchElementException();
						
						Vector2i toReturn = currentIndex;
						currentIndex = currentIndex.Add(offset);
						return toReturn;
					}
					
					/**
					 * The index of the current box
					 */
					Vector2i currentIndex = startVector;
				};
			}
		};		
	}

	@Override
	public Player Victor() {
		if (!IsFinished())
			return Player.NULL;
		
		Iterable<Vector2i> winSet = WinningSet();
		
		if (winSet == null)
			return Player.NEITHER;
		
		Iterator<Vector2i> winIter = winSet.iterator();
		PieceType winningPiece = Get(winIter.next());
		return winningPiece.equals(PieceType.CIRCLE) ? Player.CIRCLE : Player.CROSS;
	}

	@Override
	public int Width()
	{return Width;}

	@Override
	public int Height()
	{return Height;}

	@Override
	public int WinningLength()
	{return WinningLength;}
	
	/**
	 * This game board.
	 */
	protected PieceType[][] Board;
	
	/**
	 * The width of this board.
	 */
	protected int Width;
	
	/**
	 * The height of this board.
	 */
	protected int Height;
	
	/**
	 * The winning length of this board.
	 */
	protected int WinningLength;
	
	/**
	 * The number of cells currently filled on this board.
	 */
	protected int Count;
	
	/**
	 * The player that has won, if one exists.
	 */
	protected Player Winner;
	
	/**
	 * A list of every eye observing this board.
	 */
	protected LinkedList<IObserver<TicTacToeEvent>> Observers;

}
