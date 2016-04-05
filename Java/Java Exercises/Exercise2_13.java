import java.util.Scanner;

public class Exercise2_13
{
	public static void main(String [] args)
	{
		double savings = acceptAndValidateInput();
		interest(savings);
	}
	
	public static double acceptAndValidateInput()
	{
		double savings;
		Scanner input = new Scanner (System.in);
		while (true)
		{
			System.out.print("Please enter the monthly saving amount: ");
			try
			{
				savings = Double.parseDouble(input.next());
				break;
			}
			catch (NumberFormatException ignore)
			{
				System.out.println("Invalid input");
			}
			catch (Exception e)
			{
				System.exit(1);
			}
		}
		return savings;
	}
	
	public static void interest(double savings)
	{
		double monthlyInterest = 0.00417;
		double monthlySavings = savings;
		savings = monthlySavings * (1 + monthlyInterest);
		for(int c = 0;c < 5;c++)
		{
			savings = (monthlySavings + savings) * (1 + monthlyInterest);
		}
		System.out.printf("After the sixth month, the account value is %.2f", savings);
	}
}
