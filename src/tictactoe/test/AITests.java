package tictactoe.test;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import gamecore.LINQ.LINQ;
import gamecore.datastructures.vectors.Vector2i;
import tictactoe.AI.TicTacToeAI;
import tictactoe.model.ITicTacToeBoard;
import tictactoe.model.PieceType;
import tictactoe.model.Player;
import tictactoe.model.TicTacToeBoard;

public class AITests {

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
	public void UnplayedDefaultBoard()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 3, 3);
		TicTacToeAI ai = new TicTacToeAI(Player.CROSS);
		Iterable<Vector2i> states = ai.GetChildStates(b);
		assertEquals(9, LINQ.Count(states));
	}
	
	@Test
	public void UnplayedLargeBoard()
	{
		TicTacToeBoard b = new TicTacToeBoard(10, 10, 10);
		TicTacToeAI ai = new TicTacToeAI(Player.CROSS);
		Iterable<Vector2i> states = ai.GetChildStates(b);
		assertEquals(100, LINQ.Count(states));
	}
	
	@Test
	public void UnplayedRectangleBoard()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 7, 3);
		TicTacToeAI ai = new TicTacToeAI(Player.CROSS);
		Iterable<Vector2i> states = ai.GetChildStates(b);
		assertEquals(21, LINQ.Count(states));
	}
	
	@Test
	public void OneMoveDefaultBoard()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 3, 3);
		TicTacToeAI ai = new TicTacToeAI(Player.CROSS);
		for (int i = 0; i < 9; i++)
		{
			ITicTacToeBoard copy = b.Clone();
			copy.Set(PieceType.CIRCLE, new Vector2i(i % 3, i / 3));
			Iterable<Vector2i> states = ai.GetChildStates(copy);
			assertEquals("Failed on iteration " + i + ".", 8, LINQ.Count(states));
		}
	}

}
