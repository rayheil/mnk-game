package tictactoe;

/**
 * The bootstrap class that starts a game.  
 * @author Dawn Nye
 */
public class Bootstrap
{
	/**
	 * Loads a game and any related settings and mods.
	 * @param The command line arguments.
	 */
	public static void main(String[] args) throws Exception
	{
		new Thread(new TicTacToe(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]))).start();
		return;
	}
}
