import java.util.Scanner;

public class Exercise7_9
{
	public static char[][] board = new char[3][3];
	public static char currentPlayer = 'X';
	public static boolean full = false;

	public static void main(String [] args)
	{
		int row;
		int column;
		boolean validInput;
		boolean won = false;
		String userInput;
		
		Scanner input = new Scanner(System.in);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = ' ';
		printBoard();
		while(full == false && won == false)
		{
			while(true)
			{
				System.out.print("Enter a row (0, 1 or 2) for player " + currentPlayer + ": ");
				userInput = input.nextLine();
				if (userInput.matches("[0-2]{1}"))
				{
					row = Integer.parseInt(userInput);
					break;
				}
				else
					System.out.println("Invalid input, please re-enter.");
			}
			while(true)
			{
				System.out.print("Enter a column (0, 1 or 2) for player " + currentPlayer + ": ");
				userInput = input.nextLine();
				if (userInput.matches("[0-2]{1}"))
				{
					column = Integer.parseInt(userInput);
					break;
				}
				else
					System.out.println("Invalid input, please re-enter.");
			}
			validInput = placeMarker(row, column);
			if (validInput)
			{
				won = checkForWin();
				if (won)
				{
					printBoard();
					System.out.println(currentPlayer + " player won!");
				}
				else
				{
					printBoard();
					isBoardFull();
					if (full)
						System.out.println("The game ended in a draw!");
					else
						changePlayer();
				}
			}
			else
			{
				System.out.println("Maker already placed there! Please re-enter your position.");
				printBoard();
			}
		}
	}

	public static void printBoard()
	{
		System.out.println("\n-------------");
			for (int i = 0; i < 3; i++)
			{
				System.out.print("| ");
				for (int j = 0; j < 3; j++)
					System.out.print(board[i][j] + " | ");
				System.out.println("\n-------------");
			}
	}
	
	public static boolean checkForWin()
	{
		for (int i = 0; i < 3; i++)
		{
			if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) //Checks rows for winner
				return true;
			else if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) // Checks columns for winner
				return true;
			else if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) // checks diagonals for winner
				return true;
		}
		return false;
	}
	
	public static boolean placeMarker(int row, int column)
	{
		if (board[row][column] == ' ')
		{
			board[row][column] = currentPlayer;
			return true;
		}
		else
			return false;
	}
	
	public static void isBoardFull()
	{
		full = true;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (board[i][j] == ' ')
				{
					full = false;
					return;
				}
	}
	
	public static void changePlayer()
	{
		if (currentPlayer == 'X')
			currentPlayer= 'O';
		else
			currentPlayer= 'X';
	}
}
