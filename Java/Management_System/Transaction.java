import java.util.*;
import java.io.*;

/**
	System to handle transaction requests.
*/
public class Transaction
{
	private String dateTime;
	private double cost;
	private String type;

	/**
		Constructs a Transaction object.
		@param dateTime 	the date and time of transaction
		@param cost 		the total cost of the transaction
		@param type 		the the type of transaction (sale/hire)
	*/
	public Transaction(String dateTime, double cost, String type)
	{
		this.dateTime = dateTime;
		this.cost = cost;
		this.type = type;
	}

	/**
		Saves the current transaction details.
		@param file the file name and location for transaction records
	*/
	public void save(File file) throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
	    out.println(dateTime + "," + cost + "," + type);
	    out.close();
	}

	/**
		Get current transaction date and time.
		@return the date and time
	*/
	public String getDateTime()
	{
		return dateTime;
	}

	/**
		Get current transaction total cost.
		@return the total cost
	*/
	public double getCost()
	{
		return cost;
	}

	/**
		Get current transaction type.
		@return the type of transaction
	*/
	public String getType()
	{
		return type;
	}
}
