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
		/* Catch too many or too few arguments */
		if (args.length < 3 || args.length > 5)
			throw new IllegalArgumentException("Bootstrap: Usage is WIDTH HEIGHT WIN_LEN [AI_PLAYER] [AI_DIFFICULTY]");
		
		/* These three MUST be provided for the program to run correctly */
		Integer width = Integer.parseInt(args[0]);
		Integer height = Integer.parseInt(args[1]);
		Integer winLength = Integer.parseInt(args[2]);
		
		/* Set the AI settings based on any remaining command line arguments */
		Boolean p1IsHuman = true, p2IsHuman = true;
		Integer p1AIDifficulty = 0, p2AIDifficulty = 0;
		switch (args.length)
		{
		// 4 arguments: one player is an AI with hard-coded difficulty
		case 4:
			if (Boolean.parseBoolean(args[3])) {
				p1IsHuman = false;
				p2IsHuman = true;
				p1AIDifficulty = 5;
				p2AIDifficulty = 0;
			} else {
				p1IsHuman = true;
				p2IsHuman = false;
				p1AIDifficulty = 0;
				p2AIDifficulty = 5;
			}
			break;
		// 5 arguments: one player is an AI with specified difficulty
		case 5: 
			if (Boolean.parseBoolean(args[3])) {
				p1IsHuman = false;
				p2IsHuman = true;
				p1AIDifficulty = Integer.parseInt(args[4]);
				p2AIDifficulty = 0;
			} else {
				p1IsHuman = true;
				p2IsHuman = false;
				p1AIDifficulty = 0;
				p2AIDifficulty = Integer.parseInt(args[4]);
			}
			break;
		}
	
		new Thread(new TicTacToe(width, height, winLength, p1IsHuman, p1AIDifficulty, p2IsHuman, p2AIDifficulty)).start();
		return;
	}
}
