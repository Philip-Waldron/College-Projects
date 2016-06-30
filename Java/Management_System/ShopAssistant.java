import java.util.*;
import java.io.*;
import java.text.*;

public class ShopAssistant extends Employee
{
	/**
		Menu for shop assistant, overrides the employee menu.
	*/
	public void menu() throws IOException
	{
		while (true)
		{
			System.out.println("Shop Assistant: T)ransaction L)og Out");
			String command = in.nextLine().toUpperCase();

			if (command.equals("T"))
				createTransaction();
			else if (command.equals("L"))
				break;
			else
				System.out.println("Invalid Input!");
		}
	}

	/**
		Runs the transaction system.
	*/
	private void createTransaction() throws IOException
	{
		ArrayList<Item> stock = getStock();
		ArrayList<Item> transactionItems = new ArrayList<Item>();
		Transaction transaction;
		Item item;
		boolean naturalNumber;
		String itemCode;
		int itemQuantity = 0;
		double totalPrice = 0;

		System.out.println("Enter a blank line to finish adding items");

		while (true)
		{
		    System.out.print("Enter item code: ");
			itemCode = in.nextLine().toUpperCase();

			if (itemCode.trim().isEmpty())
				break;

			naturalNumber = false;
			while (!naturalNumber)
			{
				try
				{
					System.out.print("Enter item quantity: ");
					itemQuantity = Integer.parseInt(in.nextLine());
					naturalNumber = true;
				}
				catch (NumberFormatException nfe)
				{
					System.out.println("Not a natural number!");
				}
			}

			stock = itemInStock(stock, transactionItems, itemCode, itemQuantity);
		}

		if(transactionItems.size() == 0)
			return;

		System.out.println("Item Code | Name                 | Quantity |      Price");
		for(int i = 0; i < transactionItems.size(); i++)
		{
			item = transactionItems.get(i);
			printItemDetails(item);
			totalPrice += (item.getUnitCost() * item.getQuantity());
		}
		System.out.println("Total Price: " + totalPrice);

		System.out.println("Transaction Type: P)urchase, H)ire, C)ancel");
		while(true)
		{
			String command = in.nextLine().toUpperCase();

			if (command.equals("P") || command.equals("H"))
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String dateTime = dateFormat.format(Calendar.getInstance().getTime());
				String type = "sale";

				if(command.equals("H"))
				{
					type = "hire";
					System.out.println("Additional charges will apply if not returned in 30 days");
				}

				transaction = new Transaction(dateTime, totalPrice, type);
				transaction.save(new File("CSVFiles/Transactions.csv"));
				printReceipt(transactionItems, totalPrice, dateTime);
				saveStock(stock);
				break;
			}
			else if (command.equals("C"))
				break;
			else
				System.out.println("Invalid Input!");
		}
	}

	/**
		Checks if item is in stock.
		@param stock			the array of items in stock.csv (with amended item quantities)
		@param transactionItems the array of items in current transaction
		@param itemCode 		the item reference ID
		@param itemQuantity 	the number of item requested
		@return 				the new array of items in stock with amended item quantity
	*/
	private ArrayList<Item> itemInStock(ArrayList<Item> stock, ArrayList<Item> transactionItems, String itemCode, int itemQuantity) throws IOException
	{
		Item item;
		boolean found = false;

		for(int i = 0; !found && i < stock.size(); i++)
		{
			item = stock.get(i);
			if(item.getItemCode().equals(itemCode) && item.getQuantity() >= itemQuantity)
			{
				transactionItems.add(new Item(item.getItemCode(), item.getName(), itemQuantity, item.getUnitCost()));
				item.changeQuantity(itemQuantity);
				stock.set(i, item);
				found = true;
			}
		}

		if(!found)
			System.out.println("Sufficient stock not found!");

		return stock;
	}

	/**
		Creates a new file as receipt for completed transaction.
		@param transactionItems		the array of items in current transaction
		@param total 				the total price of transaction
		@param dateTime 			the date and time of transaction
	*/
	private void printReceipt(ArrayList<Item> transactionItems, double total, String dateTime) throws IOException
	{
		Item item;

		File receipt = new File("Receipts/" + dateTime + ".txt");
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(receipt)));
		out.println("**********************************************************");
		out.println("*Date: " + dateTime.substring(0, 10) + " Time: " + dateTime.substring(11, 18) + "                          *");
		out.println("*Item Code | Name                 | Quantity | Unit Price*");
		for(int i = 0; i < transactionItems.size(); i++)
		{
			item = transactionItems.get(i);
			out.print("*");
			out.printf("%9s", item.getItemCode());
			out.print(" | ");
			out.printf("%-20s", item.getName());
			out.print(" | ");
			out.printf("%8d", item.getQuantity());
			out.print(" | ");
			out.printf("%10.2f", item.getUnitCost());
			out.println("*");
		}
		out.println("*Total Price: " + total + "                                       *");
		out.println("**********************************************************");

		out.close();
		System.out.println("Created receipt: " + dateTime + ".txt");
	}

	/**
		Saves amended stock quantities to the stock file.
		@param stock the array of items from stock.csv with amended quantities
	*/
	private void saveStock(ArrayList<Item> stock) throws IOException
	{
		Item item;
		File stockFile = new File("CSVFiles/Stock.csv");
		stockFile.createNewFile();

		PrintWriter out= new PrintWriter(new BufferedWriter(new FileWriter(new File("CSVFiles/Stock.csv"))));
		for(int i = 0; i < stock.size(); i++)
		{
			item = stock.get(i);
			out.println(item.getItemCode() + "," + item.getName() + "," + item.getQuantity() + "," + item.getUnitCost());
		}

		out.close();
	}
}
