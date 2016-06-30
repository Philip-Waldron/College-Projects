import java.util.Scanner;

public class Exercise7_1
{
	//The following assumes the user inputs are in the correct format
	public static void main(String[] args)
	{
		double sum = 0.0;
		double[][] m = createArray();
		for(int column = 0; column < m[0].length; column++)
		{
			sum = sumColumn(m, column);
			System.out.println("Sum of the elements at column " + (column+1) + " is " + sum);
		}
	}

	public static double[][] createArray()
	{
		Scanner input = new Scanner(System.in);
		double[][] matrixArray = new double[3][4];

		System.out.println("Enter a " + matrixArray.length + "-by-" + matrixArray[0].length + " matrix row by row: ");
		for (int i = 0; i < matrixArray.length; i++)
			for (int j = 0; j < matrixArray[0].length; j++)
				matrixArray[i][j] = input.nextDouble();
		input.close();
		return matrixArray;
	}
	
	public static double sumColumn(double[][] m, int columnIndex)
	{
		double total = 0.0;
		for (int row = 0; row < m.length; row++)
		{
			total += m[row][columnIndex];
		}
		return total;
	}
}
