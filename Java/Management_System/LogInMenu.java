import java.util.*;
import java.io.*;

/**
	A menu for the Log In system.
*/
public class LogInMenu
{
	private static Scanner in = new Scanner(System.in);

	/**
		Runs the login menu.
		@return  the current user type
	*/
	public static Employee run() throws IOException
	{
		while (true)
		{
			System.out.println("Log in: S)hop Assistant M)anager Q)uit");
			String command = in.nextLine().toUpperCase();

			if (command.equals("S"))
			{
				if(login(1))
					return new ShopAssistant();
			}
			else if (command.equals("M"))
			{
				if(login(2))
					return new Manager();
			}
			else if (command.equals("Q"))
				System.exit(0);
			else
				System.out.println("Invalid Input!");

			System.out.println();
		}
	}

	/**
		Gets username and password as input.
		@param type the number representing user login choice
		@return		the result of if the employee is in the database of employees
	*/
	private static boolean login(int type) throws IOException
	{
		String name = "", pass = "";

		System.out.print("Username: ");
		name = in.nextLine().toLowerCase();
		System.out.print("Password: ");
		pass = in.nextLine();

		return Employee.findEmployee(type, name, pass);
	}
}
