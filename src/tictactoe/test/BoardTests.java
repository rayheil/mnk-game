package tictactoe.test;

import tictactoe.model.TicTacToeBoard;
import tictactoe.model.PieceType;
import tictactoe.model.Player;
import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BoardTests
{
	@BeforeClass
	public static void Constructor()
	{return;} // This runs before any other test code executes
	
	@Before
	public void SetUp()
	{return;} // This runs before each test
	
	@After
	public void TearDown()
	{return;} // This runs after each test, even if the test (or SetUp) fails or throws an exception
	
	@AfterClass
	public static void Destructor()
	{return;} // This runs after any other test code executes
	
	@Test
	public void BoardDefaultConstructor()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 3, 3);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				assertEquals(PieceType.NONE, b.Get(new Vector2i(i, j)));
	}
	
	@Test
	public void Get()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 3, 3);
		b.Set(PieceType.CROSS, new Vector2i(2, 0));
		assertEquals(PieceType.CROSS, b.Get(new Vector2i(2, 0)));
	}
		
	@Test
	public void WinNoPlays()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(0, 2));
		assertNull(winning);
	}
	
	@Test
	public void WinHorizontal()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CROSS, new Vector2i(0, 0));
		b.Set(PieceType.CROSS, new Vector2i(0, 1));
		b.Set(PieceType.CROSS, new Vector2i(0, 2));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(0, 2));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void WinHorizontalNoWinner()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(0, 1));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(0, 1));
		assertNull(winning);
	}
	
	@Test
	public void WinVertical()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CROSS, new Vector2i(0, 0));
		b.Set(PieceType.CROSS, new Vector2i(1, 0));
		b.Set(PieceType.CROSS, new Vector2i(2, 0));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(0, 0));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void WinVerticalNoWinner()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 0));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 0));
		assertNull(winning);
	}
	
	@Test
	public void WinDiagonalUp()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CROSS, new Vector2i(0, 0));
		b.Set(PieceType.CROSS, new Vector2i(1, 1));
		b.Set(PieceType.CROSS, new Vector2i(2, 2));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 1));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void WinDiagonalUpNoWinner()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 1));
		b.Set(PieceType.CIRCLE, new Vector2i(2, 1));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 0));
		assertNull(winning);
	}
	
	@Test
	public void WinDiagonalDown()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CROSS, new Vector2i(2, 0));
		b.Set(PieceType.CROSS, new Vector2i(1, 1));
		b.Set(PieceType.CROSS, new Vector2i(0, 2));

		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 1));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void WinDiagonalDownNoWinner()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CIRCLE, new Vector2i(2, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 1));
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 0));
		assertNull(winning);
	}
	
	@Test
	public void WinLargeBoardHorizontal()
	{
		TicTacToeBoard b = new TicTacToeBoard(15, 15, 5);
		b.Set(PieceType.CROSS, new Vector2i(7, 4));
		b.Set(PieceType.CROSS, new Vector2i(7,6));
		b.Set(PieceType.CROSS, new Vector2i(7,8));
		b.Set(PieceType.CROSS, new Vector2i(7,5));
		b.Set(PieceType.CROSS, new Vector2i(7,7));
				
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(7, 8));
		assertEquals(5, LINQ.Count(winning));
	}
	
	@Test
	public void WinLargeBoardVertical()
	{
		TicTacToeBoard b = new TicTacToeBoard(15, 15, 5);
		b.Set(PieceType.CROSS, new Vector2i(4, 7));
		b.Set(PieceType.CROSS, new Vector2i(5, 7));
		b.Set(PieceType.CROSS, new Vector2i(6, 7));
		b.Set(PieceType.CROSS, new Vector2i(7, 7));
		b.Set(PieceType.CROSS, new Vector2i(3, 7));
				
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(6, 7));
		assertEquals(5, LINQ.Count(winning));
	}
	
	@Test
	public void WinLargeBoardDiagonalDown()
	{
		TicTacToeBoard b = new TicTacToeBoard(15, 15, 5);
		b.Set(PieceType.CROSS, new Vector2i(4, 1));
		b.Set(PieceType.CROSS, new Vector2i(5, 2));
		b.Set(PieceType.CROSS, new Vector2i(6, 3));
		b.Set(PieceType.CROSS, new Vector2i(7, 4));
		b.Set(PieceType.CROSS, new Vector2i(8, 5));
				
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(7, 4));
		assertEquals(5, LINQ.Count(winning));
	}
	
	@Test
	public void WinLargeBoardDiagonalUp()
	{
		TicTacToeBoard b = new TicTacToeBoard(15, 15, 5);
		b.Set(PieceType.CROSS, new Vector2i(4, 5));
		b.Set(PieceType.CROSS, new Vector2i(5, 4));
		b.Set(PieceType.CROSS, new Vector2i(6, 3));
		b.Set(PieceType.CROSS, new Vector2i(7, 2));
		b.Set(PieceType.CROSS, new Vector2i(8, 1));
				
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(7, 2));
		assertEquals(5, LINQ.Count(winning));
	}
	
	@Test
	public void WinRectangularBoardHorizontal()
	{
		TicTacToeBoard b = new TicTacToeBoard(10, 3, 4);
		b.Set(PieceType.CIRCLE, new Vector2i(1, 4));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 5));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 6));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 7));
		
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 7));
		assertEquals(4, LINQ.Count(winning));
	}
	
	@Test
	public void WinRectangularBoardVertical()
	{
		TicTacToeBoard b = new TicTacToeBoard(10, 3, 3);
		b.Set(PieceType.CIRCLE, new Vector2i(1, 4));
		b.Set(PieceType.CIRCLE, new Vector2i(0, 4));
		b.Set(PieceType.CIRCLE, new Vector2i(2, 4));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 7));
		
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(1, 4));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void WinRectangularBoardDiagonalDown()
	{
		TicTacToeBoard b = new TicTacToeBoard(10, 3, 3);
		b.Set(PieceType.CIRCLE, new Vector2i(1, 5));
		b.Set(PieceType.CIRCLE, new Vector2i(0, 4));
		b.Set(PieceType.CIRCLE, new Vector2i(2, 6));
				
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(0, 4));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void WinRectangularBoardDiagonalUp()
	{
		TicTacToeBoard b = new TicTacToeBoard(10, 3, 3);
		b.Set(PieceType.CIRCLE, new Vector2i(0, 2));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 1));
		b.Set(PieceType.CIRCLE, new Vector2i(2, 0));
				
		Iterable<Vector2i> winning = b.WinningSet(new Vector2i(2, 0));
		assertEquals(3, LINQ.Count(winning));
	}
	
	@Test
	public void IsFinishedStalemate()
	{
		TicTacToeBoard b = new TicTacToeBoard(2,2,3); // impossible to win
		b.Set(PieceType.CIRCLE, new Vector2i(0,0));
		b.Set(PieceType.CIRCLE, new Vector2i(1,1));
		b.Set(PieceType.CROSS, new Vector2i(1,0));
		b.Set(PieceType.CROSS, new Vector2i(0,1));
		
		assertTrue(b.IsFinished());
	}
	
	@Test
	public void IsFinishedWinner()
	{
		// reasonably big board, will make it check many cells
		// it seems fast enough, mehhh
		TicTacToeBoard b = new TicTacToeBoard(1920,1080,3); 
		b.Set(PieceType.CROSS, new Vector2i(1079, 1917));
		b.Set(PieceType.CROSS, new Vector2i(1079, 1918));
		b.Set(PieceType.CROSS, new Vector2i(1079, 1919));

		assertTrue(b.IsFinished());
	}
	
	@Test
	public void IsFinishedNotFinished()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 0));

		assertFalse(b.IsFinished());
	}
	
	@Test
	public void VictorCross()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CROSS, new Vector2i(0, 0));
		b.Set(PieceType.CROSS, new Vector2i(1, 0));
		b.Set(PieceType.CROSS, new Vector2i(2, 0));
		
		assertEquals(Player.CROSS, b.Victor());
	}
	
	@Test
	public void VictorCircle()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(2, 0));
		
		assertEquals(Player.CIRCLE, b.Victor());
	}
	
	@Test
	public void VictorNeither()
	{
		TicTacToeBoard b = new TicTacToeBoard(2,2,3); // impossible to win
		b.Set(PieceType.CIRCLE, new Vector2i(0, 0));
		b.Set(PieceType.CIRCLE, new Vector2i(1, 1));
		b.Set(PieceType.CROSS, new Vector2i(0, 1));
		b.Set(PieceType.CROSS, new Vector2i(1, 0));
		
		assertEquals(b.Victor(), Player.NEITHER);
	}
	
	@Test
	public void VictorNull()
	{
		TicTacToeBoard b = new TicTacToeBoard(3,3,3);
		assertEquals(b.Victor(), Player.NULL);
	}
}
