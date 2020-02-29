
import java.util.Random;
import java.util.Scanner;

/*
 * @Author: Marcelo Sanchez
 * @Version: 6/28/17
 */

public class Minesweeper {

	/*
	 * Cell class refers to the individual places on the board.
	 * Each place is a cell
	 */
	private class Cell{
		boolean reveal;
		char digit;
		String currentStatus;		
		boolean mine;
		
		/*
		 * If current cell has mine or not
		 */
		public boolean checkMine(){
			return mine;
		}
		
		/*
		 * Place mine
		 */
		public void placeMine(){
			mine = true;
			currentStatus = "M";
		}
		
		/*
		 * Create adjacent mines
		 */
		public void setAdjacentMines(int count){
			currentStatus = String.valueOf(count);
		}
		
		/*
		 * Whether or not to reveal the board
		 */
		public boolean getBoardReveal(){
			return reveal;
		}

		public char getDigit() {
			return digit;
		}

		public String getCurrentStatus() {
			return currentStatus;
		}

		public Cell(){
			mine = false;
			reveal = false;
			currentStatus = "*";
		}
	}
	/*
	 * Function that displays the number of surrounding adjacent mines in
	 * the current cells place
	 */
	public void checkAdjacentMines(){
		int count;
		for(int row =1; row < grid[row].length - 1; row++){
			for(int col = 1; col < grid[row].length - 1; col++){
				count = 0;
				if(!grid[row][col].checkMine()){
					for(int j  = col -1; j <= col + 1; j++){
						for(int i = row -1; i <= row + 1; i++){
							if(grid[i][j].checkMine()){
								count++;
							}
						}
					}
					grid[row][col].setAdjacentMines(count);
				}
			}
		}
	}
	
	/*
	 * Randomly set mines throughout the 8x8 grid
	 */
	public void setMines(){
		int count = 0;
		int row = 0;
		int col = 0;
		
		while(count < 10){

			Random generator = new Random();
			row = generator.nextInt(8)+1;
			col = generator.nextInt(8)+1;

			if(grid[row][col].checkMine()){

			}else{
				grid[row][col].placeMine();
				count++;
			}
		}
	}

	public static Cell[][] grid;

	public Minesweeper() {
		grid = new Cell[10][10];

	}
	
	/*
	 * Displays the board.
	 */
	public void printBoard(){
		for(int row =1; row < grid[row].length - 1; row++){
			for(int col = 1; col < grid[row].length - 1; col++){
				if(grid[row][col].getBoardReveal()){
					System.out.print(grid[row][col].getCurrentStatus() + " ");
				}
				else{
					System.out.print(" - ");
				}
			}
			System.out.println();
		}
	}
	
	/*
	 * Function allows for user to see all mines placed throughout board.
	 */
	public void peekBoard(){
		for(int row =1; row < grid[row].length - 1; row++){
			for(int col = 1; col < grid[row].length - 1; col++){
				System.out.print(grid[row][col].getCurrentStatus() + " ");
			}
			System.out.println();
		}
	}
	
	/*
	 * Function initializes the grid with default blank spaces
	 */
	public void initializeGrid(){
		for(int row =0; row < grid.length; row++){
			for(int col = 0; col < grid[row].length; col++){
				grid[row][col] = new Cell();
			}
		}
	}
	
	/*
	 * Win function. Starts the game over.
	 */
	public boolean win(){
		for(int row =0; row < grid.length; row++){
			for(int col = 0; col < grid[row].length; col++){
				if(!grid[row][col].equals(" - ")){
					return false;
				}
			}
		}
		return true;
	}
	/*
	 * Function stops the game. User is able to play again if they want.
	 */
	public void gameOver(){
		System.out.println(" You've guessed wrong! You've lost! Game over");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	public static void main (String[] args){

		Minesweeper ms = new Minesweeper();
		Scanner scan = new Scanner(System.in);

		int max = 9;
		int min = 0;
		int rowGuess;
		int colGuess;

		String cont= "y";
		String yes = "yes";
		String no = "no";
		String peek;
		String guessMine;
		
		/*
		 * Loop that continues as long as the user wants to play.
		 */

		while (cont.equals("y")) {
			
			System.out.println();
			System.out.println(" -----------------------------------------------MINESWEEPER----------------------------------------------");
			System.out.println("");
			System.out.println(" Instructions: | Choose a coordinate on the 8x8 grid. Then guess if there is a mine there. If you guess |");
			System.out.println("               | correctly, the number of adjacent mines will be displayed in your guessed spot or the  |");
			System.out.println("               | letter \"M\" for a mine. Correctly guess every cell to win.                              |");
			System.out.println("               | Good luck.                                                                             |");
			System.out.println();
			System.out.println(" --------------------------------------------------------------------------------------------------------");
			System.out.println();
			ms.initializeGrid();
			ms.setMines();
			ms.checkAdjacentMines();

			System.out.println(" Enter \"y\" to play");
			cont= scan.next();


			while (cont.equals("y")) {
				
				//Uncomment this snippet to allow for "peeking"
				//Peeking allows for the user to see all the placed mines on the board
				/*
				System.out.println("(Enter \"yes\" to take a peek at the board, otherwise enter \"no\")");
				peek = scan.next();
				System.out.println();

				if(peek.equals("yes")){
					ms.peekBoard();
					System.out.println();
					System.out.println("The location of all the mines are shown");
					System.out.println();
				}
				*/
				
				/*
				 * The following is simple I/O to loop through the game
				 * appropriately.
				 */
				
				System.out.println();
				ms.printBoard();
				System.out.println();

				System.out.println(" Ok, enter a row # 1-8");
				rowGuess = scan.nextInt();

				System.out.println(" Ok, enter a column # 1-8");
				colGuess = scan.nextInt();

				System.out.println(" Is there a mine here? Enter \"yes\" or \"no\"");
				guessMine = scan.next();

				if(guessMine.equals("yes")){

					if(grid[rowGuess][colGuess].mine){
						grid[rowGuess][colGuess].reveal = true;
						ms.printBoard();
						System.out.println(" You are correct! Go for the win! Pick another cordinate");
					}
					if(!grid[rowGuess][colGuess].mine){
						ms.gameOver();
						break;
					}
				}

				if(guessMine.equals("no")){
					if(!grid[rowGuess][colGuess].mine){
						grid[rowGuess][colGuess].reveal = true;
						ms.printBoard();
						System.out.println(" You are correct! Go for the win! Pick another cordinate");
					}
					if(grid[rowGuess][colGuess].mine){
						ms.gameOver();
						break;
					}
				}
				
				if(ms.win() == true){
					System.out.println(" You've won the game!");
					ms.initializeGrid();
					ms.setMines();
					ms.checkAdjacentMines();
					System.out.println(" Enter \"y\" to play again");
					cont = scan.next();
				}		
			}
		}
	}
}
