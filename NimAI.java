import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
//By: Austin Gonzalez Date:12/9/15 Modified Dr. Glenn's code
/* Sources:
 * http://www.cs.loyola.edu/~jglenn/202/F2005/Examples/Minimax/minimax.html
 * https://www.youtube.com/watch?v=STjW3eH0Cik
 * http://stackoverflow.com/questions/32773102/nim-game-and-ai-player-using-minimax-algorithm-ai-makes-losing-moves
 *
 *
 * Plays a game of Nim perfectly using a AI concept called the minmax algorithm.
 * Its so efficient it doesn't need to be train over 1000 nim games.
 * Minmax breaks down when applied to more complex games like chess.
 * I further modified the rules of the game so that taking 3 buttons is allowed which makes a bigger minmax game tree.
 */
public class NimAI implements Cloneable
{
	 public static void printStory(){
		 System.out.println("\nTHE RULES OF NIM: ");
	     System.out.println("The game starts with the user entering the number of buttons in the game.");
	     System.out.println("The computer takes 1 or 2 or 3 buttons.");
	     System.out.println("Then the user takes 1 or 2 or 3 buttons.");
	     System.out.println("The game continues until there are no buttons left.");
	     System.out.println("Whoever takes the final button wins.");

	}
	private int buttonsLeft;
	private boolean humanTurn;

	//Clones the game
	public NimAI(int b, boolean t){
	buttonsLeft = b;
	humanTurn = t;
    }

	 public Object clone(){

		try {
			return super.clone();
		}
		catch (CloneNotSupportedException cantHappen){
			System.err.println("NimModel.clone: caught an unexpected exception");
			cantHappen.printStackTrace(System.err);
			System.exit(1);
			return null;
		    }
	    }

	 public void makeMove(int buttonsToTake){
		buttonsLeft = buttonsLeft - buttonsToTake; //either one or two
		humanTurn = !humanTurn;
	 }

	 public boolean isHumanTurn(){
		return humanTurn;
	 }

	 public boolean isGameOver(){
		 return (buttonsLeft == 0);
	 }

	 public boolean isLegalMove(int buttonsToTake){
		return (buttonsToTake > 0 && buttonsToTake <= 3 && buttonsToTake <= buttonsLeft);
	 }

	 public boolean humanWins(){
		 return (buttonsLeft == 0 && !humanTurn);
	 }



	 public int minimax(){

		 if(isGameOver()){

			 if (humanTurn) return 0;
			 else return 1;
		 }
		 else{
			 if(humanTurn){
				 int max = 0;
				 for(int b = 0; b <= buttonsLeft; b++ ) if(isLegalMove(b)){
					 NimAI AI  = (NimAI)clone();
					 AI.makeMove(b);
					 max = Math.max(max, AI.minimax());
				 }
				 return max;
			 }

			 else{
				 int min = 1;
				 for(int b = 0; b <= buttonsLeft; b++) if(isLegalMove(b)){
					 NimAI AI = (NimAI)clone();
					 AI.makeMove(b);
					 min = Math.min(min, AI.minimax());
				 }
				 return min;
			 }
		 }
	 }

	 public int getComputerMove(){

		int min = 1;

		for (int b = 0; b <= buttonsLeft; b++) if (isLegalMove(b)){

		    	NimAI AI = (NimAI)clone();
			    AI.makeMove(b);
			    if (AI.minimax() == 0)
				return b;
	    }
		int b = 0;
		while (!isLegalMove(b)) b++;
		return b;
	 }

	 public String toString(){

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < buttonsLeft; i++)
		    result.append('*');

		return result.toString();
	 }

	 public static void main(String[] args) throws IOException{

		printStory();

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("How many buttons do you want to start with?");
		int buttonsToStart = Integer.parseInt(stdin.readLine());



		NimAI test = new NimAI(buttonsToStart, true);
		NimAI game;
		if (test.minimax() == 0) game = test;
		else game = new NimAI(buttonsToStart, false);



		while (!game.isGameOver())
		    {
			System.out.println(game);

			if (game.isHumanTurn()){
				System.out.println("Choose how many buttons to take");
				int humanMove = Integer.parseInt(stdin.readLine());
				while (!game.isLegalMove(humanMove))
				    {
					System.out.println("That is an illegal move," + " try again");
					humanMove = Integer.parseInt(stdin.readLine());
				    }
				game.makeMove(humanMove);
				System.out.println("Human takes "+humanMove+" button(s)");
			}
			else{
				int computerMove = game.getComputerMove();
				game.makeMove(computerMove);
				System.out.println("Computer takes " + computerMove + " button(s)");
			}
		}

		if (game.humanWins()) System.out.println("You win!!!.");

		else System.out.println("The Computer Wins again =(");


	}


}
