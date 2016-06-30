/**
	Item object.
*/
public class Item
{
	private String itemCode;
	private String name;
	private int quantity;
	private double unitCost;

	/**
		Constructs an item object.
		@param itemCode 	the item code of the item
		@param name 		the name of the item
		@param quantity 	the quantity of the item in stock
		@param unitCost 	the cost of each unit of the item
	*/
	public Item(String itemCode, String name, int quantity, double unitCost)
	{
		this.itemCode = itemCode;
		this.name = name;
		this.quantity = quantity;
		this.unitCost = unitCost;
	}

	/**
		Adjust quantity of item stock.
		@param quantity the quantity of the item in the transaction
	*/
	public void changeQuantity(int quantity)
	{
		this.quantity = this.quantity - quantity;
	}

	/**
		Get the current item code.
		@return the item code
	*/
	public String getItemCode()
	{
		return itemCode;
	}

	/**
		Get the current item name.
		@return the item name
	*/
	public String getName()
	{
		return name;
	}

	/**
		Get the current item quantity.
		@return the item quantity
	*/
	public int getQuantity()
	{
		return quantity;
	}

	/**
		Get the current item unitCost.
		@return the item unitCost
	*/
	public double getUnitCost()
	{
		return unitCost;
	}
}
