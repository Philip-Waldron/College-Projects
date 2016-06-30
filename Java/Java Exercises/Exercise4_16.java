import java.util.Scanner;

//The following assumes the user inputs are in the correct format (integer)
public class Exercise4_16
{
	public static void main(String [] args)
	{
		String factors = "";
		int currentNumber;
		int prevDiv = 0, div = 2;

		Scanner input = new Scanner(System.in);
		System.out.print("Please enter an integer: ");
		currentNumber = Integer.parseInt(input.nextLine());
		while (currentNumber != 1 && currentNumber != 0)
		{
			if ((currentNumber % div) == 0)
			{
				currentNumber = currentNumber / div;
				if (prevDiv != div)
				{
					factors += div + ", ";
					prevDiv = div;
				}
			}
			else
				div++;
		}
		System.out.print(factors);
	}
}
