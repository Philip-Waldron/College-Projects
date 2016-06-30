import javax.swing.JOptionPane;

public class Exercise3_17 
{
	public static void main(String [] args)
	{
		String result;
		String userChoiceStr;

		while(true)
		{
			userChoiceStr = JOptionPane.showInputDialog(null, "Welcome To Rock Paper Scissors, Please make your selection \n 0. Scissors \n 1. Rock \n 2. Paper", null, 1);
			if (userChoiceStr == null)
				break;
			else if (userChoiceStr.matches("[0-2]{1}"))
			{
				int userChoice = Integer.parseInt(userChoiceStr);
				int computerChoice =  (int) (Math.random() * 3);
				result = calcResult(userChoice, computerChoice);
				JOptionPane.showMessageDialog(null, result, "Rock Paper Scissors", 1);
				break;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please enter a valid Selection (0, 1 or 2)");
			}
		}
	}

	public static String calcResult(int userChoice, int computerChoice)
	{
		String result = "";
		switch (computerChoice)
		{
			case 0: if (userChoice == 0)
						result = "The Computer's scissors matches your scissors, you draw!";
					else if (userChoice == 1)
						result = "The Computer's scissors is blocked by your rock, you win!";
					else
						result = "The Computer's scissors cuts your paper, you lose!";
					break;
			case 1: if (userChoice == 0)
						result = "The Computer's rock smashes your scissors, you lose!";
					else if (userChoice == 1)
						result = "The Computer's rock matches your rock, you draw!";
					else
						result = "The Computer's rock is wrapped by your paper, you win!";
					break;
			case 2: if (userChoice == 0)
						result = "The Computer's paper is cut by your scissors, you win!";
					else if (userChoice == 1)
						result = "The Computer's paper wraps your rock, you lose!";
					else
						result = "The Computer's paper matches your paper, you draw!";
					break;
		}
		return result;
	}
}
