import java.util.*;
import java.io.*;
import java.text.*;

public class Manager extends Employee
{
	/**
		Menu for manager, overrides the employee menu.
	*/
	public void menu() throws IOException
	{
		while (true)
		{
			System.out.println("Manager: S)tock Summary I)tem details T)ransactions L)og Out");
			String command = in.nextLine().toUpperCase();

			if (command.equals("S"))
				stockSummary();
			else if (command.equals("I"))
				itemDetails();
			else if (command.equals("T"))
				summaryOfTransactions();
			else if (command.equals("L"))
				break;
			else
				System.out.println("Invalid Input!");

			System.out.println();
		}
	}

	/**
		Prints out a summary of current stock.
	*/
	private void stockSummary() throws IOException
	{
		ArrayList<Item> stock = getStock();
		Item item;

		System.out.println("Summary of stock");
		System.out.println("Item Code | Name                 | Quantity | Unit Price");

		for(int i = 0; i < stock.size(); i++)
		{
			item = stock.get(i);
			printItemDetails(item);
		}
	}

	/**
		Prints out details of a specified item.
	*/
	private void itemDetails() throws IOException
	{
		ArrayList<Item> stock = getStock();
		System.out.print("Enter an item code: ");
		String input = in.nextLine();
		Item item;
		boolean itemFound = false;

		for(int i = 0; !itemFound && i < stock.size(); i++)
		{
			item = stock.get(i);
			if(item.getItemCode().equals(input))
			{
				itemFound = true;
				System.out.println("Item Code | Name                 | Quantity | Unit Price");
				printItemDetails(item);
			}
		}

		if(!itemFound)
			System.out.println("No item with item code " + input + " found.");
	}

	/**
		Handles requests for summary of transactions.
	*/
	private void summaryOfTransactions() throws IOException
	{
		String type;
		int time;
		int quantity;
		boolean naturalNumber;

		while(true)
		{
			System.out.println("Choose unit of time: D)ays, W)eeks, M)onths, Y)ears, C)ancel");
			type = in.nextLine().toUpperCase();
			if(type.equals("D") || type.equals("W") || type.equals("M") || type.equals("Y"))
			{
				naturalNumber = false;
				while (!naturalNumber)
				{
					try
					{
						System.out.print("Enter units of time: ");
						quantity = Integer.parseInt(in.nextLine());
						naturalNumber = true;
					}
					catch (NumberFormatException nfe)
					{
						System.out.println("Not a natural number!");
					}
				}

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String dateTime = dateFormat.format(Calendar.getInstance().getTime());

				//I know how to compare times, using calendar, but ran out of time.
				printTransactions();
				return;
			}
			else if(type.equals("C"))
				break;
			else
				System.out.println("Invalid Input!");
		}
	}

	/**
		Prints out a summary of specified transactions.
	*/
	private void printTransactions() throws IOException
	{
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		String line;
		String temp[];
		Transaction transaction;

		File transactionList = new File("CSVFiles/Transactions.csv");
		BufferedReader reader = new BufferedReader(new FileReader(transactionList));

		while((line = reader.readLine()) != null)
		{
			temp = line.split(",");
			transaction = new Transaction(temp[0], Double.parseDouble(temp[1]), temp[2]);
			transactions.add(transaction);
		}

		reader.close();

		System.out.println("Summary of transactions");
		System.out.println("Date-Time           | Total Cost | Transaction Type");

		for(int i = 0; i < transactions.size(); i++)
		{
			transaction = transactions.get(i);
			System.out.print(transaction.getDateTime() + " | ");
			System.out.printf("%10.2f", transaction.getCost());
			System.out.println(" | " + transaction.getType());
		}
	}
}
