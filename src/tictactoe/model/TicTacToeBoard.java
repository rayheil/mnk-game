package tictactoe.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;
import gamecore.observe.IObserver;

public class TicTacToeBoard implements ITicTacToeBoard {

	public TicTacToeBoard(int width, int height, int winningLength)
	{
		this.Board = new PieceType[height][width];
		this.Width = width;
		this.Height = height;
		this.WinningLength = winningLength;
		
		for (int i = 0; i < Height(); i++)
			for (int j = 0; j < Width(); j++)
				Board[i][j] = PieceType.NONE;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Ray Heil
	 * @throws IndexOutOfBoundsException Thrown if {@code index} is out of bounds.
	 * @throws NoSuchElementException Thrown if no element exists at ({@code x},{@code y}).
	 * @throws NullPointerException Thrown if {@code index} is null.
	 */
	@Override
	public PieceType Get(Vector2i index) {
		if (index == null)
			throw new NullPointerException();
		
		if (!VectorInBounds(index))
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
	 * @author Ray Heil
	 * @throws IndexOutOfBoundsException Thrown if {@code index} is out of bounds.
	 * @throws NullPointerException Thrown {@code index} is null or if {@code t} is null.
	 */
	@Override
	public PieceType Set(PieceType t, Vector2i index) {
		if (t == null || index == null)
			throw new NullPointerException();
		
		if (!VectorInBounds(index))
			throw new IndexOutOfBoundsException();
		
		Board[index.X][index.Y] = t; 
		return t;
	}

	/**
	 * {@inheritDoc}
	 * @throws IndexOutOfBoundsException Thrown if {@code index} is out of bounds.
	 * @throws NullPointerException Thrown if {@code index} is null.
	 * @author Ray Heil
	 */
	@Override
	public boolean Remove(Vector2i index) {
		if (index == null)
			throw new NullPointerException();
		
		if (!VectorInBounds(index))
			throw new IndexOutOfBoundsException();
		
		if (Board[index.X][index.Y] != PieceType.NONE)
		{
			Board[index.X][index.Y] = PieceType.NONE; 
			return true;
		}
		return false;
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
		if (!nonempty)
			return IndexSet();
		
		// This iterator always skips empty cells, since we take care of !nonempty above
		return new Iterable<Vector2i>()
		{
			public Iterator<Vector2i> iterator()
			{
				return new Iterator<Vector2i>() 
				{
					@Override
					public boolean hasNext() {	
						// If nextItem is set, we have already found a next item
						if (nextItem != null)
							return true;
							
						// If not, try to find the next nonEmpty cell so we can return it
						// If currentIndex is already at the limit, this will stop running.
						int currentRow, currentColumn;
						for (; currentIndex < Width() * Height(); currentIndex++)
						{
							currentRow = currentIndex / Width();
							currentColumn = currentIndex % Width();
							
							if (Get(new Vector2i(currentRow, currentColumn)).equals(PieceType.NONE))
							{
								nextItem = new Vector2i(currentRow, currentColumn);
								return true;
							}		
						}
						
						// If we iterate up to Width() * Height() and find nothing, there are no values left
						return false;
					}

					@Override
					public Vector2i next() {
						if (!hasNext())
							throw new NoSuchElementException();
						
						Vector2i returnVector = nextItem;
						nextItem = null;
						return returnVector;
					}
					
					/**
					 * The current index of the iterator. (0 <= currentIndex < Width() * Height())
					 */
					int currentIndex = 0;
					
					/**
					 * The next item for the iterator to return.
					 */
					Vector2i nextItem = null;
				};
			}
		};
		*/

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
		return NeighborIndexSet(index, false);
	}

	@Override
	public Iterable<Vector2i> NeighborIndexSet(Vector2i index, boolean nonempty) {
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
								PieceType t = Get(targetIndex);
								if (!nonempty || !t.equals(PieceType.NONE)) {
									next = targetIndex;
									return true;
								}
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
	public boolean ContainsIndex(Vector2i index) {
		return (0 <= index.X && index.X < Height() &&
				0 <= index.Y && index.Y < Width());
	}

	@Override
	public boolean Clear() {
		Board = new PieceType[Height()][Width()];
		
		for (int i = 0; i < Height(); i++)
			for (int j = 0; j < Width(); j++)
				Board[i][j] = PieceType.NONE;
		
		return true;
	}

	@Override
	public int Count() {
		// TODO is this a good way to do this? or maybe a terrible way?
		// maybe I should be keeping track of the number of items at a certain time so I don't need to iterate the whole board?
		int count = 0;
		for (Vector2i index : IndexSet(true))
		{
			count++;
		}
		return count;
	}

	@Override
	public int Size() {
		return Width() * Height();
	}

	@Override
	public void Subscribe(IObserver<TicTacToeEvent> eye) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Unsubscribe(IObserver<TicTacToeEvent> eye) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ITicTacToeBoard Clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Vector2i> WinningSet() {
		// TODO Auto-generated method stub
		// for this one, it might make sense to use Ellie's method
		return null;
	}

	@Override
	public Iterable<Vector2i> WinningSet(Vector2i use_me) {
		// TODO Auto-generated method stub
		// for this one, we should branch out from use_me to see if there are winning combos
		// ooh! we could call WinningSet(use_me) on every new move and that'd sorta work.
		return null;
	}
	
	/**
	 * Check a Vector2i's coordinates to see if it is inside
	 * the board's boundaries.
	 * @param index The Vector2i representation of the index to check
	 * @return {@true} if index is within the bounds of the board.
	 */
	protected boolean VectorInBounds(Vector2i index)
	{
		return (index.X >= 0 && index.X < Width() &&
				index.Y >= 0 && index.Y < Height());
	}

	@Override
	public Player Victor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Width() {
		return Width;
	}

	@Override
	public int Height() {
		return Height;
	}

	@Override
	public int WinningLength() {
		return WinningLength;
	}
	
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
	

}
