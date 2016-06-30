import java.util.Scanner;

//The following assumes the user inputs are in the correct format
public class Exercise5_5
{
	public static void main(String [] args)
	{
		double num1, num2, num3;
		Scanner input = new Scanner(System.in);

		System.out.println("Please enter the first number: ");
		num1 = input.nextDouble();
		System.out.println("Please Enter the second number: ");
		num2 = input.nextDouble();
		System.out.println("Please Enter the third number: ");
		num3 = input.nextDouble();
		displayLargestNumber(num1, num2, num3);
	}

	public static void displayLargestNumber(double num1, double num2, double num3)
	{
		double largest = num1;
		if (num2 > largest)
			largest = num2;
		if (num3 > largest)
			largest = num3;
		System.out.println("The largest number is " + largest);
	}
}