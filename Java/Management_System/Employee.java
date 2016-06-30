import java.util.*;
import java.io.*;

/**
	Employee object.
*/
public class Employee
{
	protected Scanner in = new Scanner(System.in);

	/**
		Menu to be overridden by ShopAssistant or Manager.
	*/
	public void menu() throws IOException
	{
	}

	/**
		Checks database against employee login.
		@param type the number representing user login choice
		@param name the name of the employee
		@param pass the password for the employee
		@return 	the result of if the employee is in the database of employees
	*/
	public static boolean findEmployee(int type, String name, String pass) throws IOException
	{
		String[] employee = new String[3];
		File file = new File("CSVFiles/Employees.csv");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		boolean found = false;

		while(!found && (line = reader.readLine()) != null)
		{
			employee = line.split(",");
			if(employee[1].equals(name) && employee[2].equals(pass))
				if(type <= Integer.parseInt(employee[0]))
					found = true;
				else
				{
					System.out.println("You Don't have manager permissions!");
					reader.close();
					return false;
				}
		}

		reader.close();

		if(found)
			return true;
		else
		{
			System.out.println("Invalid username or password!");
			return false;
		}
	}

	/**
		Creates an array to hold stock for current transaction quantity values.
		@return 	the stock array holding all items in stock.csv
	*/
	protected ArrayList<Item> getStock() throws IOException
	{
		ArrayList<Item> stock = new ArrayList<Item>();
		String line;
		String temp[];
		Item item;

		File stockList = new File("CSVFiles/Stock.csv");
		BufferedReader reader = new BufferedReader(new FileReader(stockList));

		while((line = reader.readLine()) != null)
		{
			temp = line.split(",");
			item = new Item(temp[0], temp[1], Integer.parseInt(temp[2]), Double.parseDouble(temp[3]));
			stock.add(item);
		}

		reader.close();
		return stock;
	}

	/**
		Prints item details.
		@param item the object of item details
	*/
	protected void printItemDetails(Item item)
	{
		System.out.printf("%9s", item.getItemCode());
		System.out.print(" | ");
		System.out.printf("%-20s", item.getName());
		System.out.print(" | ");
		System.out.printf("%8d", item.getQuantity());
		System.out.print(" | ");
		System.out.printf("%10.2f", item.getUnitCost());
		System.out.println();
	}
}
