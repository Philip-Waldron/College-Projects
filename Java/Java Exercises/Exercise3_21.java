import java.util.Scanner;

//The following assumes the user inputs are in the correct format
public class Exercise3_21
{
	public static void main(String [] args)
	{
		Scanner input = new Scanner(System.in);

        System.out.print("Enter year (e.g. 2012): ");
        int year = input.nextInt();

        System.out.print("Enter month (1-12): ");
        int month = input.nextInt();

        System.out.print("Enter the day of the month (1-31): ");
        int day = input.nextInt();

        if (month == 1 || month == 2) 
		{
            month += 12;
            year--;
        }

        int k = year % 100; // The year of the century
        int century = (int)(year / 100.0);
        int q = day;
        int m = month;
        int h = (q + (int)((26 * (m + 1)) / 10.0) + k + (int)(k / 4.0) + (int)(century / 4.0) + (5 * century)) % 7;

		String result = "Day of the week is ";
		switch (h)
		{
			case 0: System.out.print(result + "Saturday"); break;
			case 1: System.out.print(result + "Sunday"); break;
			case 2: System.out.print(result + "Monday"); break;
			case 3: System.out.print(result + "Tuesday"); break;
			case 4: System.out.print(result + "Wednesday"); break;
			case 5: System.out.print(result + "Thursday"); break;
			default: System.out.print(result + "Friday"); break;
		}
	}
}
