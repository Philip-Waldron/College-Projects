import java.io.IOException;
import java.io.File;

/**
	This program emulates a shop management system.
*/
public class ManagementSystem
{
	public static void main(String[] args) throws IOException
	{
		Employee user;
		File[] listOfFiles = new File("CSVFiles").listFiles();
		
		System.out.println("List of CSV files:");
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				System.out.println(listOfFiles[i].getName());
			}
		}
		while (true)
		{
			user = LogInMenu.run();
			user.menu();
		}
	}
}
