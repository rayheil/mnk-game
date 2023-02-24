package tictactoe.test;

import tictactoe.model.TicTacToeBoard;
import tictactoe.model.PieceType;
import gamecore.datastructures.vectors.Vector2i;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BoardTests
{
	@BeforeClass
	public static void TestConstructor()
	{return;} // This runs before any other test code executes
	
	@Before
	public void SetUp()
	{return;} // This runs before each test
	
	@After
	public void TearDown()
	{return;} // This runs after each test, even if the test (or SetUp) fails or throws an exception
	
	@AfterClass
	public static void TestDestructor()
	{return;} // This runs after any other test code executes
	
	@Test
	public void TestBoardDefaultConstructor()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 3, 3);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				assertEquals(PieceType.NONE, b.Get(new Vector2i(i, j)));
	}
	
	@Test
	public void TestGet()
	{
		TicTacToeBoard b = new TicTacToeBoard(3, 3, 3);
		b.Set(PieceType.CROSS, new Vector2i(2, 0));
		assertEquals(PieceType.CROSS, b.Get(new Vector2i(2, 0)));
	}
	
	// TODO WHAT IF I NEVER TESTED ANYTHING LMAOOOOOO
}
