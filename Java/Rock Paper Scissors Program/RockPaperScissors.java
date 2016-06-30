import javax.swing.JOptionPane;
public class RockPaperScissors 
{
	public static void main(String [] args)
	{
		String result ="";
		String ComputerChoiceStr = "";
		String UserChoiceStr = JOptionPane.showInputDialog(null, "Welcome To Rock Paper Scissors, Please make your selection \n 1. Rock \n 2. Paper \n 3. Scissors", null, 1);
		if (UserChoiceStr == null)
			System.exit(0);
		if (UserChoiceStr.equals(""))
		{
			JOptionPane.showMessageDialog(null,"No input entered!");
			System.exit(0);
		}
		if (UserChoiceStr.matches("[1-3]{1}"))
		{
			int UserChoice = Integer.parseInt(UserChoiceStr);
			UserChoiceStr = calcUserChoice(UserChoice, UserChoiceStr);

			int ComputerChoice =  (int) (Math.random() * 3)+1;
			ComputerChoiceStr = calcComputerChoice(ComputerChoice, ComputerChoiceStr);

			result = GameWin(result,UserChoiceStr,ComputerChoiceStr,UserChoice,ComputerChoice);
			JOptionPane.showMessageDialog(null,result,"Rock Paper Scissors",1);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Not a valid Selection");
			System.exit(0);
		}
		
	}
	public static String calcUserChoice (int UserChoice, String UserChoiceStr)
	{
		if (UserChoice == 1)
			UserChoiceStr ="Rock";
		else if (UserChoice == 2)
			UserChoiceStr = "Paper";
		else
			UserChoiceStr = "Scissors";
		return UserChoiceStr;
	}
	
	public static String calcComputerChoice (int ComputerChoice, String ComputerChoiceStr)
	{
		if (ComputerChoice == 1)
			ComputerChoiceStr = "Rock";
		else if (ComputerChoice == 2)
			ComputerChoiceStr = "Paper";
		else if (ComputerChoice == 3)
			ComputerChoiceStr = "Scissors";
		else
		{
			JOptionPane.showMessageDialog(null,"The Computer's Choice was Invalid","Error",2);
			System.exit(0);
		}
		return ComputerChoiceStr;
	}

	public static String GameWin (String result, String UserChoiceStr, String ComputerChoiceStr, int UserChoice, int ComputerChoice)
	{
		switch (ComputerChoice)
		{
			case 1: if (UserChoice == 1)
						result += UserChoiceStr + " matched " + ComputerChoiceStr +  " It's A Draw! ";
					else if (UserChoice == 2)
						result += UserChoiceStr + " Wraps " + ComputerChoiceStr + " You win!";
					else
						result += ComputerChoiceStr + " Blocks " + UserChoiceStr + " You Lost!";
					break;
			case 2: if (UserChoice == 1)
						result += ComputerChoiceStr + " Wraps " + UserChoiceStr + " You Lost!";
					else if (UserChoice == 2)
						result += UserChoiceStr + " matched " + ComputerChoiceStr +  " It's A Draw! ";
					else
						result += UserChoiceStr + " Cuts " + ComputerChoiceStr + " You win!";
					break;
			case 3: if (UserChoice == 1)
						result += UserChoiceStr + " Blocks " + ComputerChoiceStr + " You win!";
					else if (UserChoice == 2)
						result += ComputerChoiceStr + " Cuts " + UserChoiceStr + " You Lost!";
					else
						result += UserChoiceStr + " matched " + ComputerChoiceStr +  " It's A Draw! ";
					break;
		}
		return result;
	}
}