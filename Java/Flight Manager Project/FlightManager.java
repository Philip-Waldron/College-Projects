import javax.swing.JOptionPane;
import java.text.*;
import java.util.*;
import java.io.*;
public class FlightManager
{
	public static File airportsFile = new File("Airports.txt");
	public static File flightsFile = new File("Flights.txt");
	/*Philip Waldron 14173026
	The main method takes in the the command line arguments as input and validates it.
	Either a message will be output or the appropriate method will be executed.*/
	public static void main (String[] args) throws IOException
	{
		if (args.length >= 2 && args.length <= 5)
		{
			switch (args[0].toUpperCase())
			{
				case "AA": addAirport(args); break;
				case "EA": editAirport(args); break;
				case "DA": deleteAirport(args); break;
				case "EF": editFlight(args); break;
				case "DF": deleteFlight(args); break;
				case "SF": searchForFlight(args); break;
				case "SD": searchForFlightWithDepartureDate(args); break;
				default: JOptionPane.showMessageDialog(null, "Invalid command entered!", "Invalid Command", 2);
			}
		}
		else if (args.length == 1 && args[0].equalsIgnoreCase("help"))
		{
			String help = "\n1. Add airport:\t\t\tjava FlightManager AA AirportName AirportCode";
					help += "\n2. Edit airport:\t\tjava FlightManager EA AirportCode NewAirportName";
					help += "\n3. Delete airport:\t\tjava FlightManager DA AirportCode";
					help += "\n4. Edit flight:\t\t\tjava FlightManager EF FlightNumber Days(M-WT-SS) StartDate(1/5/2015)\n\t\t\t\tEndDate(31/10/2015)";
					help += "\n5. Delete flight:\t\tjava FlightManager DF FlightNumber";
					help += "\n6. Search for flights:\t\tjava FlightManager SF DepartureAirport ArrivalAirport";
					help += "\n7. Search for flights on date:\tjava FlightManager SD DepartureAirport ArrivalAirport Date(1/12/2015)";
			System.out.println(help);
		}
		else
			JOptionPane.showMessageDialog(null, "Invalid number of command line arguments entered!\n\nPlease enter between 2 and 5 command line arguments inclusive\nor enter help for a list of commands.", "Error", 2);
	}


	/*Philip Waldron 14173026
	Command line arguments are taken as input. If the airports text file does not already exist,
	it is created and the airport input is added. Otherwise the method checks if the airport already
	exists and if not, another method adds the new airport to the text file.*/
	public static void addAirport(String[] args) throws IOException
	{
		if (args.length != 3)
			JOptionPane.showMessageDialog(null, "3 command line arguments expected, " + args.length + " entered!", "Error", 2);
		else if (args[1].indexOf(",") != -1 || args[2].indexOf(",") != -1)
			JOptionPane.showMessageDialog(null, "Airport name or airport code cannot contain a comma!", "Error", 2);
		else if (!airportsFile.exists())
		{
			PrintWriter output = new PrintWriter(airportsFile);
			output.println(args[1] + "," + args[2].toUpperCase());
			output.close();
		}
		else
		{
			Scanner in = new Scanner(airportsFile);
			ArrayList<String> airportArrayList = new ArrayList<String>();
			String searchText = "," + args[2].toUpperCase();
			boolean airportFound = false;
			int count = 0;
			FileReader airportReader = new FileReader(airportsFile);
			while (in.hasNext() && !airportFound)
			{
				airportArrayList.add(in.nextLine());
				if (airportArrayList.get(count).toUpperCase().indexOf(searchText) != -1)
					airportFound = true;
				count++;
			}
			airportReader.close();
			if (airportFound)
				JOptionPane.showMessageDialog(null, "Airport code " + args[2].toUpperCase() + " already exists!", "Error", 2);
			else
			{
				airportArrayList.add(args[1] + "," + args[2].toUpperCase());
				writeToAirportsFile(airportArrayList);
			}
		}
	}


	/*Philip Waldron 14173026
	Command line arguments are taken as input. If the airport file exists the method searches through the
	airports text file. If the entered airport does not exist an error message is output. Otherwise
	the appropriate airport is edited and another method writes the airports to the text file.*/
	public static void editAirport(String[] args) throws IOException
	{
		if (args.length != 3)
			JOptionPane.showMessageDialog(null, "3 command line arguments expected, " + args.length + " entered!", "Error", 2);
		else if (!airportsFile.exists())
			JOptionPane.showMessageDialog(null, "Airports file does not exist!", "Error", 2);
		else if (args[1].indexOf(",") != -1 || args[2].indexOf(",") != -1)
			JOptionPane.showMessageDialog(null, "Airport name or airport code cannot contain a comma!", "Error", 2);
		else
		{
			Scanner in = new Scanner(airportsFile);
			ArrayList<String> airportArrayList = new ArrayList<String>();
			String searchText = "," + args[1].toUpperCase();
			boolean airportFound = false;
			int count = 0;
			FileReader airportReader = new FileReader(airportsFile);
			while (in.hasNext())
			{
				airportArrayList.add(in.nextLine());
				if (airportArrayList.get(count).toUpperCase().indexOf(searchText) != -1)
				{
					airportArrayList.set(count, args[2] + "," + args[1].toUpperCase());
					airportFound = true;
				}
				count++;
			}
			airportReader.close();
			if (!airportFound)
				JOptionPane.showMessageDialog(null, "Airport code " + args[2].toUpperCase() + " does not exist!", "Error", 2);
			else
			{
				writeToAirportsFile(airportArrayList);
			}
		}
	}


	/*Input: Command line arguments, specifically the airport to be deleted
	Process: Checks Airports.txt to see if the airport exists and deletes it if it does, then scans Flights.txt and deletes all flights belonging to that airport 
	Output: New Airports.txt and Flights.txt files with the airport in question removed if it does exist or an error message if it doesn't*/
	public static void deleteAirport(String[] args) throws IOException
	{
		int airportOrFlightTest;
		String airport = args[1].toUpperCase();
		airportOrFlightTest = 3;
        if(airportsFile.exists())
        {
            boolean airportExists = deleteEntriesFromFile(airport, "Airports.txt", airportOrFlightTest);
            airportOrFlightTest = 1;
            if(airportExists && flightsFile.exists())
            {
                deleteEntriesFromFile(airport, "Flights.txt", airportOrFlightTest);
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Airports file does not exist!", "Error", 2);
	}


	/*Input:   User input at run time.
	Process: Allows the end user to edit flight details. 
	Output:  Writes the edited flights details to file.*/
    public static void editFlight(String[] args) throws IOException
    {
    	File file = new File("Flights.txt");
        boolean flightExists = false;
        boolean firstDateBool = false;
        boolean secondDateBool = false;
        ArrayList<String> fileString = new ArrayList<String>();
        String datePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
        String dayPattern = "[M,-]{1}[T,-]{1}[W,-]{1}[T,-]{1}[F,-]{1}[S,-]{1}[S,-]{1}";
        String invalidDayPattern = "-------";
    	String flightNoPattern = "[A-Z]{2}[0-9]+";
    	String newLine = "";
        String [] temp; 
        Date todaysDate;
        Date firstDateIn;
        Date secondDateIn;
        if (args.length == 5)
        {
            if(file.exists() && args[1].matches(flightNoPattern) && args[2].matches(dayPattern) && args[2] != invalidDayPattern && args[3].matches(datePattern)&& args[4].matches(datePattern) )
        	{
                if (dateChecker(args[3]) && dateChecker(args[4]))
                {
                    todaysDate = todaysDate();
                    firstDateIn = dateConverter(args[3]);
                    secondDateIn = dateConverter(args[4]);
                    if(firstDateIn.compareTo(todaysDate) >= 0)
                    {
                        firstDateBool =true;
                    } 
                    if(secondDateIn.after(firstDateIn))
                    {
                        secondDateBool = true;
                    }
                }
                if (dateChecker(args[3]) && dateChecker(args[4]) && firstDateBool && secondDateBool)
                {   
                    args[3] = zeroNator2000(args[3]);
                    args[4] = zeroNator2000(args[4]);
                    Scanner in = new Scanner(file);
        	    	while (in.hasNext())
        	    	{
        	    		fileString.add(in.nextLine());
        	    	}
        	    	in.close();   
                    for (int i = 0 ; i < fileString.size() -1 && flightExists == false; i ++)
                    {
                        temp = fileString.get(i).split(",");
                        if (temp[0].compareTo(args[1]) == 0)
                        {
                            temp[5] = args[2];
                            temp[6] = args[3];
                            temp[7] = args[4];
                            for (int j = 0; j < temp.length; j ++)
                            {
                                newLine += temp[j];
                                if (j < 7)
                                    newLine += ",";
                            }
                            fileString.set(i, newLine);
                            FileWriter fileOut = new FileWriter("Flights.txt");
                            PrintWriter out = new PrintWriter(fileOut);         
                            for (int k = 0; k < fileString.size(); k ++)
                            {
                              out.println(fileString.get(k));
                            }
                            out.close();
                            fileOut.close();
                            flightExists = true;
                        }
                    }
                    if (flightExists == false)
                		JOptionPane.showMessageDialog(null, "No such flight number exists. Please check the flight number and try again.");
                    else
                        JOptionPane.showMessageDialog(null, "Your flight has been edited");
                }
                else 
                {
                    if (!(dateChecker(args[3]) && dateChecker(args[4])))
                        JOptionPane.showMessageDialog(null, "Your input contained an invalid date.");
                    else 
                    {
                        if (!(firstDateBool))
                            JOptionPane.showMessageDialog(null, "The first date you entered can not occour before today's date");
                        if (!(secondDateBool))
                            JOptionPane.showMessageDialog(null, "The first date you enter needs to come before second date you enter");
                    }
                }
            }
    		else
    		{
				String error = "";
    			if (!(args[1].matches(flightNoPattern)))								
        			error += "Please enter a valid Flight Number.";
        		if ((!(args[2].matches(dayPattern))) || args[2].matches(invalidDayPattern)) 									
        			error += "\nPlease enter the days in the valid format: \"MTWTFSS\", With a \"-\" replacing up to six of the letters\"";
        		if (!(args[3].matches(datePattern) && args[4].matches(datePattern))) 	
        			error += "\nPlease enter the dates in the valid format\t\"DD/MM/YYYY\"";
        		if (!(file.exists())) 													
        			error += "\nNo file called \"Flights.txt\" exists yet. Please add flights before editing them.";
				JOptionPane.showMessageDialog(null, error, "Error", 2);
    		}
        }
        else
            JOptionPane.showMessageDialog(null, "Please enter the appropriate number of arguments");
    }


	/*Input: Command line arguments, specifically the flight to be deleted
	Process: Checks Flights.txt to see if the flight exists and deletes it if it does, or gives an error message if it doesn't
	Output: Produces a Flights.txt file with the flight in question deleted if it exists or outputs an error message if it doesn't*/
	public static void deleteFlight(String[] args) throws IOException
	{
		int airportOrFlightTest;
		String flight = args[1].toUpperCase();
		airportOrFlightTest = 2;
		if (flightsFile.exists())
            deleteEntriesFromFile(flight, "flights.txt", airportOrFlightTest);
        else
            JOptionPane.showMessageDialog(null, "Flights file does not exist!", "Error", 2);
	}


	/*Input: Takes in command SF from user, and an arrival and departure airport.
	Process: Checks if user has entered correct information, and then gets codes for the airports given.
	Outputs: Appropriate error messages, or, if correct input is given, it will display the flights that
	are available between the two given airports.*/
	public static void searchForFlight(String[] args) throws IOException
	{
		if (args.length != 3)
			JOptionPane.showMessageDialog(null, "3 command line arguments expected, " + args.length + " entered!", "Error", 2);
		else if (!flightsFile.exists() || !airportsFile.exists())
			JOptionPane.showMessageDialog(null, "Flights file and/or Airports File does not exist!", "Error", 2);
		else
		{
			String results = "";
			String[] fileItem;
			String depcode = "";
			String arrcode = "";
			List<String> flights = new ArrayList<String>();
			boolean depfound = false;
			boolean arrfound = false;
			Scanner airportScanner = new Scanner(airportsFile);

			while (airportScanner.hasNext() && (!depfound || !arrfound))
			{
				fileItem = (airportScanner.nextLine()).split(",");
				if (fileItem[0].equalsIgnoreCase(args[1]))
				{
					depcode = fileItem[1];
					depfound = true;
				}
				if (fileItem[0].equalsIgnoreCase(args[2]))
				{
					arrcode = fileItem[1];
					arrfound = true;
				}
			}
			airportScanner.close();
			if (!depfound || !arrfound)
				JOptionPane.showMessageDialog(null, "Departure airport and/or arrival airport doesn't exist.", "Error", 2);
			else
			{
				Scanner flightsScanner = new Scanner(flightsFile);
				while (flightsScanner.hasNext())
				{
					fileItem = (flightsScanner.nextLine()).split(",");
					if (fileItem[1].equalsIgnoreCase(depcode) && fileItem[2].equalsIgnoreCase(arrcode))
					{
						flights.add(fileItem[0]);
					}
				}
				flightsScanner.close();
				
				if (flights.size() >=1)
				{
				JOptionPane.showMessageDialog(null, "Flights departing from "+args[1]+" and arriving in "+args[2]+" are: ", "Flights",1);
				for (int i = 0; i<flights.size();i++)
					results += flights.get(i) +"\n";
				JOptionPane.showMessageDialog(null, results, "Flights", 1);
				}
				else
					JOptionPane.showMessageDialog(null, "There are no flights departing from "+args[1]+" and arriving in "+args[2], "Flights", 1);
			}
		}
	}


	/*Input: Takes in command SF from user along with a Departure airport, Arrival airport, and a date.
	Process: Checks if user has given a valid input. Gets code for airport names given. 
	Checks if date given is a valid date, and then checks whether or not there are flights on
	the date given from the given airports.
	Outputs: Displays appropriate error messages if needed, otherwise, it will display the flights that are on
	that date from the given airports.*/
	public static void searchForFlightWithDepartureDate(String[] args) throws IOException
	{
		if (args.length != 4)
			JOptionPane.showMessageDialog(null, "4 command line arguments expected, " + args.length + " entered!", "Error", 2);
		else if (!flightsFile.exists() || !airportsFile.exists())
			JOptionPane.showMessageDialog(null, "Flights file and/or Airports File does not exist!", "Error", 2);
		else
		{
			String results = "";
			int day, month, year, a, b, dayOfWeek;
			String[] fileItem;
			String date = args[3];
			Date actualDate;
			String startingDate = "";
			Date actualStartingDate;
			String endingDate = "";
			Date actualEndingDate;
			String dayOfDate = "";
			int posOfDay = 7;
			String depcode = "";
			String arrcode = "";
			File airportsFile = new File("Airports.txt");
			File flightFile = new File("Flights.txt");
			List<String> flights = new ArrayList<String>();
			boolean depfound = false;
			boolean arrfound = false;
			Scanner airportScanner = new Scanner(airportsFile);

			while (airportScanner.hasNext() && (!depfound || !arrfound))
			{
				fileItem = (airportScanner.nextLine()).split(",");
				if (fileItem[0].equalsIgnoreCase(args[1]))
				{
					depcode = fileItem[1];
					depfound = true;
				}
				if (fileItem[0].equalsIgnoreCase(args[2]))
				{
					arrcode = fileItem[1];
					arrfound = true;
				}
			}
			airportScanner.close();
			if (!depfound || !arrfound)
				JOptionPane.showMessageDialog(null, "Departure airport and/or arrival airport doesn't exist.", "Error", 2);
			else
			{
				String pattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
				if (args[3].matches(pattern))
				{
					int positionFirstSlash, positionLastSlash, ddInt, mmInt, yyInt;
					int[] daysArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
					boolean dateIsValid = true;
					positionFirstSlash = date.indexOf("/");
					positionLastSlash = date.lastIndexOf("/");
					ddInt = Integer.parseInt(date.substring(0, positionFirstSlash));
					mmInt = Integer.parseInt(date.substring(positionFirstSlash + 1, positionLastSlash));
					yyInt = Integer.parseInt(date.substring(positionLastSlash + 1));
					if ((ddInt == 0) || (mmInt == 0) || (yyInt == 0))
						dateIsValid = false;
						else if (mmInt >12) dateIsValid = false;
						else if ((ddInt == 29) && (mmInt == 2) && ((((yyInt % 4 == 0) && (yyInt % 100 != 0)) || (yyInt % 400 == 0))))
							dateIsValid = true;
						else if (ddInt > daysArray[mmInt - 1]) dateIsValid = false;
										
					actualDate = getDate(date);
						
					Scanner flightsScanner = new Scanner(flightFile);
					while (flightsScanner.hasNext() && dateIsValid)
					{
						fileItem = (flightsScanner.nextLine()).split(",");
						if (fileItem[1].equalsIgnoreCase(depcode) && fileItem[2].equalsIgnoreCase(arrcode))
						{
							startingDate = fileItem[6];
							actualStartingDate = getDate(startingDate);
							endingDate = fileItem[7];
							actualEndingDate = getDate(endingDate);
							if (actualDate.after(actualStartingDate) || actualDate.equals(actualStartingDate) && actualDate.before(actualEndingDate) || actualDate.equals(actualEndingDate))
							{
								dayOfDate = dayOnDate(date);
								if (dayOfDate.equals("Monday"))		posOfDay = 0;
								else if (dayOfDate.equals("Tuesday"))	posOfDay = 1;
								else if (dayOfDate.equals("Wednesday"))	posOfDay = 2;
								else if (dayOfDate.equals("Thursday"))	posOfDay = 3;
								else if (dayOfDate.equals("Friday"))	posOfDay = 4;
								else if (dayOfDate.equals("Saturday"))	posOfDay = 5;
								else if (dayOfDate.equals("Sunday"))	posOfDay = 6;

							String days = fileItem[5];
							if (posOfDay < 7)
							{
								if (!(days.substring(posOfDay, posOfDay + 1).contains("-")))
								flights.add(fileItem[0]);
							}
							}
						}
					}
					flightsScanner.close();
					
					if (flights.size() >=1)
					{
					JOptionPane.showMessageDialog(null, "Flights departing from "+args[1]+" and arriving in "+args[2]+" on "+ args[3] + " are: ", "Flights", 1);
					for (int i = 0; i<flights.size();i++)
						results += flights.get(i) + "\n";
					JOptionPane.showMessageDialog(null, results, "Flights", 1);
					}
					else
						JOptionPane.showMessageDialog(null, "There are no flights departing from "+args[1]+" and arriving in "+args[2]+" on "+ args[3], "Flights", 2);
				}
				else
					JOptionPane.showMessageDialog(null, "Date is invalid", "Error", 2);
			}
		}
	}


	/*Philip Waldron 14173026
	This method takes in an array of airports from another method and sorts the airports alphabetically.
	The airports are then written out to the airports text file.*/
	public static void writeToAirportsFile(ArrayList<String> airportArrayList) throws IOException
	{
		Collections.sort(airportArrayList);
		PrintWriter output = new PrintWriter(airportsFile);
		for(int count = 0;count < airportArrayList.size();count++)
			output.println(airportArrayList.get(count));
		output.close();
		JOptionPane.showMessageDialog(null, "Airport successfully appended/edited!", "Success!", 1);
	}


	/*Input: takes in the object to be checked for, the file to be scanned and a variable to determine which functions of the code need to be carried out
	Process: scans a given file for a flight or airport and if it exists removes it from the file. If the flight/airport does not exist an error message is displayed.
	Output: Gives a revised Airports/Flights.txt file without the given airport/flight in it. If the flight/airport doesn't exist an error message is displayed.*/
	public static boolean deleteEntriesFromFile(String objectName, String fileName, int airportOrFlight) throws IOException
	{
		List<String> objectList = new ArrayList<String>();
		boolean objectFound = false; 
		boolean airportExists = true;
		int checkPosition = 0;
		FileReader aFileReader = new FileReader(fileName);
		Scanner input = new Scanner(aFileReader);
			while(input.hasNext())
			{
				String temp = input.nextLine();
				String[] details = temp.split(",");
				if(airportOrFlight > 1)
				{
					if(airportOrFlight == 2)
						checkPosition = 0;
					else if(airportOrFlight == 3)
						checkPosition = 1;

					if(details[checkPosition].matches(objectName))
					{
						objectFound = true;
					}
					else if(details[checkPosition].matches(objectName)) 
						objectList.add(temp);
					else
						objectList.add(temp);
				}
				else if(airportOrFlight == 1)
                {
                    if(details[1].matches(objectName))
                        objectFound = true;
                    else if(details[2].matches(objectName))
                        objectFound = true;
                    else objectList.add(temp);
				}
			}
		aFileReader.close();
		input.close();
		String airportFlightMessage;
        if (fileName == "flights.txt")
            airportFlightMessage = "flight";
        else
            airportFlightMessage = "airport";
        if (!objectFound)
        {
            JOptionPane.showMessageDialog(null, "The given " + airportFlightMessage + " does not exist in " + fileName + "\nPlease ensure the " + airportFlightMessage + " entered is correct", "Error", 2);
            airportExists = false;
        }
		else 
		{
			FileWriter aFileWriter = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(aFileWriter);
			for(int i = 0; i < objectList.size(); i++)
				out.println(objectList.get(i));
			out.close();
			aFileWriter.close();
			JOptionPane.showMessageDialog(null, objectName + " entries have been removed from " + fileName);
		}
		return airportExists;
	}


	/*Input: A date sent down from another method.
	  Process: Converts date string to a date format.
	  Output: Sends result back to method that sent it down.*/
	public static Date getDate(String startDate)
	{
		startDate = startDate.trim();
		GregorianCalendar aCalendar = new GregorianCalendar();
		String dateParts = aCalendar.get(Calendar.DATE) +
			"/" + ((aCalendar.get(Calendar.MONTH)) + 1) +
			"/" + aCalendar.get(Calendar.YEAR);

		Date actualStartDate = new Date();
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			actualStartDate = (Date) dateFormatter.parse(startDate);
		}
		catch (ParseException pe)
		{
			System.out.println("Unable to format one or more dates");
		}
			return actualStartDate;
	}


	/*Input: A date sent down from another method.
	  Process: Checks the date to find out what day on  the year it falls on.
	  Output: Sends the day that the date falls on back to the method that sent down the date.*/
	public static String dayOnDate(String dateInput)
	{
		int ddInt, mmInt, yyInt, a, b, dayOfWeek, positionFirstSlash, positionLastSlash;
		int[] daysArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		positionFirstSlash = dateInput.indexOf("/");
		positionLastSlash = dateInput.lastIndexOf("/");
		ddInt = Integer.parseInt(dateInput.substring(0, positionFirstSlash));
		mmInt = Integer.parseInt(dateInput.substring(positionFirstSlash + 1, positionLastSlash));
		yyInt = Integer.parseInt(dateInput.substring(positionLastSlash + 1));
		String result = "";
			if (mmInt == 1 || mmInt == 2)
			{
			  mmInt += 12;
			  yyInt  -=   1;
			}
			a = yyInt % 100;
			b = yyInt / 100;
			dayOfWeek = ((ddInt + (((mmInt + 1) * 26) / 10) + a + (a / 4) + (b / 4)) + (5 * b)) % 7;
			if      (dayOfWeek == 0) result += "Saturday";
			else if (dayOfWeek == 1) result += "Sunday";
			else if (dayOfWeek == 2) result += "Monday";
			else if (dayOfWeek == 3) result += "Tuesday";
			else if (dayOfWeek == 4) result += "Wednesday";
			else if (dayOfWeek == 5) result += "Thursday";
			else if (dayOfWeek == 6) result += "Friday";
			
		  return result;
	}


	// 0877174
    // Input: A Date.
    // Process: Checks if a date is a valid date.
    // Output: Returns a true or false.
    public static boolean dateChecker(String date)
    {
        int firstSlash, lastSlash, dd, mm, yyyy;
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        firstSlash = date.indexOf("/");
        lastSlash = date.lastIndexOf("/");
        dd = Integer.parseInt(date.substring(0, firstSlash));
        mm = Integer.parseInt(date.substring(firstSlash + 1, lastSlash));
        yyyy = Integer.parseInt(date.substring(lastSlash + 1));
        if ((dd == 0) || (mm == 0) || (yyyy == 0))
            return false;
        else if (mm > 12) 
            return false;
        else if ((dd <= monthDays[mm-1]) || ((dd == 29) && (mm == 2) && ((((yyyy % 4 == 0) && (yyyy % 100 != 0)) || (yyyy % 400 == 0))))) 
            return true;
        return false;
    }


    // 0877174
    // Input: None.
    // Process: Gets the date from the computer.
    // Output: Returns todays Date.
    public static Date todaysDate()
    {
        Date retrunDate;
        GregorianCalendar aCalendar = new GregorianCalendar();
        String dateParts = aCalendar.get(Calendar.DATE) + "/" + ((aCalendar.get(Calendar.MONTH)) + 1) + "/" + aCalendar.get(Calendar.YEAR);
        retrunDate = dateConverter(dateParts);
        return retrunDate;
    }


    // 0877174
    // Input: A string of charters.
    // Process: Converts a string of charters into a date object.
    // Output: Returns a date object.
    public static Date dateConverter(String inDate)
    {
        Date outDate = new Date();
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            outDate = (Date) (dateFormatter.parse(inDate));
        }
        catch (ParseException pe)
        {
            JOptionPane.showMessageDialog(null, "Unable to format your date");
        }
        return outDate;
    }


    // 0877174
    // Input: A string of charters representing a date.
    // Process: Adds a zero where appropriate to a date.
    // Output: Returns the resulting date.
    public static String zeroNator2000(String date)
    {
        int firstSlash, secondSlash;
        firstSlash = date.indexOf("/");
        secondSlash = date.indexOf("/", firstSlash);
        String subString1 = date.substring(0, firstSlash);
        String subString2 = date.substring(firstSlash, secondSlash);
        String pattern = ("\\d{1}/\\d{1,2}/\\d{4}");
        String pattern2 = ("\\d{2}/\\d{1}/\\d{4}");
        String dateTemp = "";
        if (date.matches(pattern))
        {
                dateTemp = "0" + date;
                date = dateTemp;
        }
        if (date.matches(pattern2))
            date = date.substring(0, (date.indexOf("/") + 1)) + "0" + date.substring((date.indexOf("/") + 1));
        return date;
    }
}