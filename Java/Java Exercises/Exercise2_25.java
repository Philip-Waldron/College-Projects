import java.util.Scanner;

public class Exercise2_25
{
	//The following assumes the user inputs are in the correct format
	public static String name;
	public static double hoursWorked;
	public static double hourlyPayRate;
	public static double fedTaxRate;
	public static double stateTaxRate;
	public static double fedTax;
	public static double stateTax;
	
	public static void main(String [] args)
	{
		userInputs();
		printPayroll();
	}
	
	public static void userInputs()
	{
		Scanner input = new Scanner (System.in);
		System.out.print("Enter employee's name: ");
		name = input.nextLine();
		System.out.print("Enter number of hours worked in a week: ");
		hoursWorked = Double.parseDouble(input.nextLine());
		System.out.print("Enter hourly pay rate: ");
		hourlyPayRate = Double.parseDouble(input.nextLine());
		System.out.print("Enter federal tax withholding rate: ");
		fedTaxRate = Double.parseDouble(input.nextLine());
		System.out.print("Enter state tax withholding rate: ");
		stateTaxRate = Double.parseDouble(input.nextLine());
	}
	
	public static void printPayroll()
	{
		fedTax = (hoursWorked * hourlyPayRate) * fedTaxRate;
		stateTax = (hoursWorked * hourlyPayRate) * stateTaxRate;
		System.out.println("\nEmployee Name: " + name);
		System.out.println("Hours Worked: " + hoursWorked);
		System.out.println("Pay Rate: $" + hourlyPayRate);
		System.out.printf("Gross Pay: $%.2f", + hoursWorked * hourlyPayRate);
		System.out.println("\nDeductions:");
		System.out.printf("\tFederal Withholding (" + (fedTaxRate * 100) + "%%): $%.2f", + fedTax);
		System.out.printf("\n\tState Withholding (" + (stateTaxRate * 100) + "%%): $%.2f", + stateTax);
		System.out.printf("\nTotal Deduction: $%.2f", + (fedTax + stateTax));
		System.out.printf("\nNet Pay: $%.2f", + ((hoursWorked *hourlyPayRate) - (fedTax + stateTax)));
	}
}
