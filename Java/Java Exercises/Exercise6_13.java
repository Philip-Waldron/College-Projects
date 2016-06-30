public class Exercise6_13
{
	public static void main(String[] args)
	{
		int[] excludedNumbers = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54}; // test data

		System.out.println(getRandom(excludedNumbers));
	}

	public static int getRandom(int[] excludedNumbers)
	{
		int random = 0;
		boolean excludedNumber = true;

		while (excludedNumber)
		{
			excludedNumber = false;
			random = (int) (Math.random() * 54) + 1;
			for (int i = 0; i < excludedNumbers.length; i++)
				if (excludedNumbers[i] == random)
				{
					excludedNumber = true;
					break;
				}
		}
		return random;
	}
}
