import java.util.Scanner;

public class Exercise6_21
{
	//The following assumes the user inputs are in the correct format
	public static int largest = 0;

	public static void main(String []args)
	{
		String pathResults = "";
		String slotResults = "";
		int numOfSlots;
		int numOfBalls;

		Scanner input = new Scanner(System.in);
		System.out.print("Enter the number of balls to drop: ");
		numOfBalls = input.nextInt();
		System.out.print("Enter the number of slots in the bean machine: ");
		numOfSlots = input.nextInt();
		int [] slots = new int[numOfSlots];

		pathResults = calcPaths(pathResults, numOfSlots, numOfBalls, slots);
		calcLargest(slots);
		slotResults = calcSlots(slotResults, slots);
		System.out.println(pathResults);
		System.out.println(slotResults);
	}
	
	public static String calcPaths(String pathResults, int numOfSlots, int numOfBalls, int[] slots)
	{
		int slotCount;
		int leftRight;

		for (int i = numOfBalls; i > 0; i--)
		{
			slotCount = 1;
			for(int j = (numOfSlots -1); j > 0; j --)
			{
				leftRight = (int)(Math.random()*2);
				if (leftRight == 0)
					pathResults += "L";
				else if (leftRight == 1)
				{
					pathResults += "R";
					slotCount++;
				}
			} 
			if(i > 1)
				pathResults += "\n";
			slots[slotCount - 1]++;
		}
		return pathResults;
	}
	
	public static void calcLargest(int[] slots)
	{
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] > largest)
				largest = slots[i];
		}
	}
	
	public static String calcSlots(String slotResults, int[] slots)
	{
		for (int i = largest + 1; i > 0; i--) 
		{
			for (int j = 0; j < slots.length ; j ++) 
			{
				if (slots[j] >= i)			
					slotResults += "0";					
				else 
					slotResults += " ";
			} 
			slotResults += "\n";
		}
		return slotResults;
	}
}
