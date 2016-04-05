public class Exercise5_9
{
	public static void main(String [] args)
	{
		double foot = 1;
		double meter = 20;
		double m;
		double f;

		System.out.println("Feet\t\tMeters\t|\tMeters\t\tFeet");
		for (;foot <= 10;foot++, meter = meter + 5)
		{
			m = footToMeter(foot);
			f = meterToFoot(meter);
			System.out.printf(foot + "\t\t" + m + "\t|\t" + meter + "\t\t" + f + "\n");
		}
	}
	
	public static double footToMeter(double foot)
	{
		double m = 0.305 * foot;
		return m;
	}
	
	public static double meterToFoot(double meter)
	{
		double f = 3.279 * meter;
		return f;
	}
}
