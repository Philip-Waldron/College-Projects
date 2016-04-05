import java.util.Scanner;

//The following assumes the user inputs are in the correct format
public class Exercise4_25
{
	public static void main(String [] args)
	{
		int x = 1, sum = 1, n;

		Scanner input = new Scanner (System.in);
		System.out.print("Enter a number: ");
		n = Integer.parseInt(input.nextLine());
		if (n != 0)
		{
			for(int i = 2; i <= n; i++)
			{
				x += i;
				sum += x;
			}
		}
		else
			x = 0;
		System.out.println(sum);
	}
}
