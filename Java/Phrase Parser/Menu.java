import javax.swing.JOptionPane;
public class Menu
{
	public static void main (String[] args)
	{
		int	choice;
		String menuOption = "";
		while ((menuOption !=null) && (!(menuOption.equals("0"))))
		{
			menuOption = getMenuOption();
			if (menuOption !=null)
			{
				choice = Integer.parseInt(menuOption);
				if (choice != 0)
				{
					switch(choice)
					{
						case 1: analyseVowelContent(); break;
						case 2: determineFrequencyOfConsonants(); break;
						case 3: determineKeysRequiredToTypeWordOrPhrase(); break;
						case 4: whichRowsOfKeysUsed(); break;
						case 5: determineIfVowelsAndConsonantsAreAlternating(); break;
						case 6: determineShortestAndLongest(); break;
						case 7: areWordsOrPhrasesAnagrams(); break;
						case 8: determineIfWordPhraseSentenceIsPalindrome(); break;
					}
				}
			}
		}
	}


	public static String getMenuOption()
	{
		String menuOptions = "1. Analyse the vowel content of a word/phrase." +
		"\n2. Analyse consonant frequency of a word/phrase." +
		"\n3. Analyse character content of a word/phrase. "	+
		"\n4. Determine key rows required to type a word or phrase. " +
		"\n5. Determine if a word/phrase has an alternating vowel/consonant pattern. " +
		"\n6. Analyse maximum and minimum word length in a phrase. " +
		"\n7. Determine if two words are anagrams of each other. " +
		"\n8. Analyse if a word/phrase is a palindrome. " +
		"\n0. Exit. ";
		boolean validInput = false;
		String selection = "", menuChoicePattern = "[0-8]{1}";
		String errorMessage = "Invalid menu selection. ";
			   errorMessage += "\n\nValid options are 0 to 8 inclusive.";
			   errorMessage += "\nSelect OK to retry. ";

		while (!(validInput))
		{
			selection = JOptionPane.showInputDialog(null, menuOptions, "Choose number of option you wish to have executed", 3);
			if (selection == null || selection.matches(menuChoicePattern))
				validInput = true;
			else
				JOptionPane.showMessageDialog(null, errorMessage, "Error in user input", 2);
		}
		return selection;
	}
	

	public static String getWordOrPhraseFromEndUser(String windowMessage, String windowTitle)
	{
		boolean validInput = false;
		String userInput = "";
		String errorMessage = "Invalid input.";
				errorMessage += "\nPlease enter a word or phrase at least 2 characters in length!";
				errorMessage += "\nSelect OK to retry";

		while (!(validInput))
		{
			userInput = JOptionPane.showInputDialog(null, windowMessage, windowTitle, 3);
			if (userInput == null || userInput.matches(".{2,}"))
				validInput = true;
			else
				JOptionPane.showMessageDialog(null, errorMessage, "Error in user input", 2);
		}
		return userInput;
	}


	/* 0877174 Patrick Foskin ; 14150611 Robert Benedict.McCahill.
	Input:
	A string.
	Process:
	This method counts the number of a character in a string by replacing every instance of a character in a string with nothing and then counting the difference between the new word and the old word.
	Output:
	A string of the results of this method.
	*/
	public static String acquireChars(String word)
	{
		int x ;
		String y = "";
		String wordCopy = word;
		String results = "";
		int z = wordCopy.length();
		while (z > 0)
		{   
			y =   wordCopy.substring(0, 1);
			wordCopy = wordCopy.replace(y, "");
			x =  word.length() - wordCopy.length();     
			word = wordCopy;
			results += y + " occurs " + x + " time(s). \n";
			z = wordCopy.length();
		}
		return results;
	}


	/*
	0877174 Patrick Foskin 
	Input:
	This method when called does not accept any input from the calling method.
	Process:
	This method calls getWordOrPhraseFromEndUser() on execution.
	getWordOrPhraseFromEndUser() passes back a word of over two charters in length or a value null.
	If null is returned the method ends.
	If null is not returned from getWordOrPhraseFromEndUser() the input received form getWordOrPhraseFromEndUser() is converted to lower case and stored in a second variable named word.
	A variable called results is initiated. This will eventually be displayed back to the end user stating the vowel analysis of the input.
	Every character in the word variable that is not a vowel is removed.
	Our word variable is checked against a regex pattern to see if it contains Vowels.
	If no vowels are contained in the input this is stated to the end user and the method ends. 
	If word contains vowels the frequency of the vowels are counted.
	The word variable is passed to this acquireChars() method. It returns the frequency of the charters occurring in the word variable.
	All double instances of charters are removed from word and word is checked for the occurrence of aeiou and uoiea using the .matches method and two patterns.
	If the word contains instances of the vowels in alphabetic or reverse alphabetic order this is added to results. If not this is added to results.
	A dialog box is displayed to the end user stating the results of the Word analysis.
	Output:
	These method doses not return a value to the calling method
	*/		
	public static void analyseVowelContent()
	{
		String input = getWordOrPhraseFromEndUser("Enter a word or phrase to be analysed by the program", "Vowel analyze");
		if (input != null)
		{
			String word = input.toLowerCase().replaceAll("[^aeiou]","");
			String results = "Your input, " + input + ", ";
			if (word.matches("(.)*([aeiou])(.)*"))
			{
				results += "contains vowels.\n";
				word = word.replaceAll("(.)\\1+","$1");
				results += acquireChars(word);

				if ((word.indexOf("a") > -1) && (word.indexOf("e") > -1) && (word.indexOf("i") > -1) && (word.indexOf("o") > -1) && (word.indexOf("u") > -1))   //if this one fails as it is and the whole condition will fail. quicker then a loop
				{
					results += "It contained all the vowels\n";
					if (word.contains("aeiou") ) results += "All the vowels are in alphabetical order\n";
					else results += "All the vowels are not in alphabetic order\n";
					if (word.contains("uoiea") ) results += "All the vowels are in reverse alphabetical order\n";
					else results += "All the vowels are not in reverse alphabetic order\n";
				}
				else results += "It did not contain all the vowels. \n";  
			}
			else results += "did not contain any vowels. \n";
			JOptionPane.showMessageDialog(null, results);  
		}
	}


	/*Gets user input using common method, then removes all characters that aren't consonants.
	If the string is empty, returns a message saying no consonants are present. Otherwise it is passed to another method that returns
	a string detailing every consonant present and how many times it occurs in the input.*/
	public static void determineFrequencyOfConsonants()
	{
		String phrase1 = getWordOrPhraseFromEndUser("Enter a word or phrase to be analysed by the program", "Consonants Present");
		if (phrase1 != null)
		{
			phrase1 = phrase1.replaceAll("[^bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]", "");
			if (phrase1.length() < 1)
				JOptionPane.showMessageDialog(null, "No consonants present", "result", 1);
			else
			{
				String results = acquireChars(phrase1);
				JOptionPane.showMessageDialog(null, results, "results", 1);
			}
		}
	}


	/*Passes the phrase into the acquireChars array which returns a string detailing each character present and how many times it occurs.
	Then creates 2 patterns, one for alphabetic characters (upper and lower case), and another for numbers.
	A temp string is created  and set to be equal to the input with all non alphabetic characters removed.
	This temp string is compared with the original input string and the length difference indicates how many alphabetic characters are present in the input.
	The same process is done for numbers, and any remaining characters will go towards the 'other characters' counter.
	These are then merged with the results string and outputted to the user*/
	public static void determineKeysRequiredToTypeWordOrPhrase()
	{
		String patternAZ = "[a-zA-Z]";
		String pattern01 = "[0-9]";
		String phrase1 = getWordOrPhraseFromEndUser("Enter a word or phrase to be analysed by the program", "Characters Present");
		String phraseCopy = "";
		int letterCount, numCount , charCount;
		if (phrase1 != null)
		{
			String results = acquireChars(phrase1);
			phraseCopy = phrase1.replaceAll(patternAZ, "");
			letterCount = phrase1.length() - phraseCopy.length();
			phrase1 = phraseCopy.replaceAll(pattern01, "");
			numCount = phraseCopy.length() - phrase1.length();
			charCount = phrase1.length();
			results += letterCount + " alphabetic characters present" + "\n";
			results += numCount + " numeric characters present" + "\n";
			results += charCount + " other characters present" + "\n";
			JOptionPane.showMessageDialog(null, results, "Output", 1);
		}
	}


	/*
	08577174 Patrick Foskin
	This method analyses the content of a word and detects which rows of keys were used to type it. Then telling the end user which rows of keys were used.
	Input:
	This method when called does not accept any input from the calling method.
	Process:
	This method calls getWordOrPhraseFromEndUser() on execution.
	getWordOrPhraseFromEndUser() passes back a word of over two charters in length or a value null.
	If null is returned the method ends.
	If null is not returned from getWordOrPhraseFromEndUser() the input received form getWordOrPhraseFromEndUser() is converted to lower case and stored in a second variable named word.
	A variable called results is initiated. This will eventually be displayed back to the end user stating the analysis of the input.
	Everything that is not an alphabetic charter is removed from the word variable.
	Our word variable is checked against a regex pattern to see if it contains charters from the top row of a keyboard. If so this stated to results.
	Our word variable is checked against a regex pattern to see if it contains charters from the middle row of a keyboard. If so this stated to results.
	Our word variable is checked against a regex pattern to see if it contains charters from the bottom row of a keyboard. If so this stated to results.
	A message is displayed to the end user stating which rows of keys were required to type this phrase.
	Output:
	This method doses not return a value to the calling method
	*/
	public static void whichRowsOfKeysUsed()
	{
		String result = "";
		String word = getWordOrPhraseFromEndUser("enter a word or phrase to be analyzed by the computer", "The Rows Of Keys Required to Type this Phrase");
		if (word != null)
		{
			word = word.toLowerCase();
            word = word.replaceAll("[^a-z]", "");
			if (word.length() == 0) result += "No alphabetic characters were required to type this phrase";			
			else
			{
				result = "To type this phrase you need to use the following rows:\n";
				boolean condition1 = false;
				boolean condition2 = false;
				boolean condition3 = false;
				boolean masterCondition = false;
				String top = "[qwertyuiop]";
				String mid = "[asdfghjkl]";
				String bot = "[zxcvbnm]";
				int i = 0;
				while (i < word.length() && masterCondition == false)
				{
					if (word.substring(i, i +1).matches(top) && condition1 == false)
					{
						result += "\tThe top row.\n";
						condition1 = true;
					}
					if (word.substring(i, i +1).matches(mid) && condition2 == false)
					{
						result += "\tThe middle row.\n";
						condition2 = true;			
					}
					if (word.substring(i, i +1).matches(bot) && condition3 == false)
					{
						result += "\tThe bottom row.\n";
						condition3 = true;
					}
					if (condition1 == true && condition2 == true && condition3 == true)
					{
						masterCondition = true;
					}
					i++;
				}
			}
			JOptionPane.showMessageDialog(null, result);
		}
	}


	/*The input is taken from the method getWordOrPhraseFromEndUser, and converted to lower case and alphabetic characters.
	An if statement checks if the input has 2 or more alphabetic characters.
	Vowels are taken as equivalent to odd numbers and consonants are equivalent to even numbers.
	A loop steps through each letter of the input and ends if the pattern of 'odd even odd' or 'even odd even' is not followed or until every letter is checked.
	The loop checks if the previous letter was odd or even, and if the next letter alternates.
	Using a boolean condition, the method outputs whether or not the vowels and consonants alternated.*/
	public static void determineIfVowelsAndConsonantsAreAlternating()
	{
		String userInput = getWordOrPhraseFromEndUser("Enter a word or phrase", "Are Vowels and Consonants Alternating");
		if (userInput != null)
		{
			String message1 = "The word or phrase comprises of alternating vowels and consonants in it's entirety.";
			String message2 = "The word or phrase does NOT comprise of alternating vowels and consonants in it's entirety.";
			String message3 = "The word or phrase did not contain 2 or more alphabetic characters!";
			String vowels = "aeiou", consonants = "bcdfghjklmnpqrstvwxyz", currentLetter;
			int oddEven, check;
			boolean alternating = true;
			
			userInput = userInput.toLowerCase().replaceAll("[^a-z]", "");
			if (userInput.length() < 2)
				JOptionPane.showMessageDialog(null, message3);
			else
			{
				currentLetter = userInput.substring(0, 1);
				if (vowels.indexOf(currentLetter) != -1)
					oddEven = 1;
				else
					oddEven = 2;
				for (int count = 1;count < userInput.length() && alternating == true;count++, oddEven++)
				{
					currentLetter = userInput.substring(count, count + 1);
					if (oddEven % 2 == 0)
						check = vowels.indexOf(currentLetter);
					else
						check = consonants.indexOf(currentLetter);
					if (check == -1)
						alternating = false;
				}
				if (alternating == true)
					JOptionPane.showMessageDialog(null,message1);
				else
					JOptionPane.showMessageDialog(null, message2);
			}
		}
	}


	/* Method starts declaring variables after taking a phrase from the end user.
	It converts the 'words' input, into the appropriate form the code needs to check the length of each word*/
	public static void determineShortestAndLongest()
	{
		String words = getWordOrPhraseFromEndUser("Enter a phrase or sentence: ", "Word Length");
		if (words != null )
		{
			String[] wordsarrays;
			int max = 0;
			int min = 0;
			String longest = " ";
			String shortest = " ";
			words = words.toLowerCase().replaceAll("[^a-z0-9\\s]","").trim();
			words = words.replaceAll("\\s+", " ");
			wordsarrays = words.split(" ");
			if (wordsarrays.length == 1)
				JOptionPane.showMessageDialog(null,"You are required to enter a phrase or sentence, not a word!");
			else if (words.length() == 0 || words.length() == 1)
				JOptionPane.showMessageDialog(null,"A phrase or sentence must contain alphabetic characters or numbers to count in each word.");
			else
			{
				max = wordsarrays[0].length();
				min = wordsarrays[0].length();
				
				for (int c = 0; c < wordsarrays.length; c++)
				{
					if (max < wordsarrays[c].length())
						max = wordsarrays[c].length();
					if (min > wordsarrays[c].length())
						min = wordsarrays[c].length();
				}
				for (int i = 0; i < wordsarrays.length; i++)
				{
					if (wordsarrays[i].length() == max && !longest.contains(wordsarrays[i]))
						longest += wordsarrays[i] + " ";
					if (wordsarrays[i].length() == min && !shortest.contains(wordsarrays[i]))
						shortest += wordsarrays[i] + " ";
				}
				JOptionPane.showMessageDialog(null,"Length of longest word is "+max+
													"\nLength of shortest word is "+min+
													"\nWords of longest length are: "+longest+
													"\nWords of shortest length are: "+shortest);
			}
		}
	/* Displays results from the code. Will show the lengths of the shortest and longest words,
	and display the words matching that length, without producing duplicates of these words.
	Outputs appropriate error messages if user enters an incorrect input. */
	}


	/*Two inputs are taken from from the method getWordOrPhraseFromEndUser.
	An if statement checks if there are any alphabetic or numeric characters in the input. If there are none, message3 is output.
	An if statement checks if the two words or phrases are the same length. If not, the entire process is skipped and message2 is output.
	A for loop steps through each character in the first input, and removes that character in the second input if it exists.
	If at any point the character does not exist in the second input, a boolean condition terminates the loop and message2 is output.
	If every character in the first input exists in the second input, message1 is output.*/
	public static void areWordsOrPhrasesAnagrams()
	{
		String userInput1 = getWordOrPhraseFromEndUser("Enter first word or phrase", "Are Words or Phrases Anagrams");
		if (userInput1 != null)
		{
			String userInput2 = getWordOrPhraseFromEndUser("Enter second word or phrase", "Are Words or Phrases Anagrams");
			if (userInput2 != null)
			{
				String message1 = "Without punctuation, The two words or phrases are anagrams of each other.";
				String message2 = "The two words or phrases are NOT anagrams of each other.";
				String message3 = "One or both of the words or phrases did not contain any alphabetic or numeric characters!";
				String input1Letter;
				boolean anagram = true;

				userInput1 = userInput1.toLowerCase().replaceAll("[^a-z0-9]", "");
				userInput2 = userInput2.toLowerCase().replaceAll("[^a-z0-9]", "");
				if (userInput1.length() == 0 || userInput2.length() == 0)
					JOptionPane.showMessageDialog(null, message3);
				else
				{
					if (userInput1.length() == userInput2.length())
					{
						for (int count = 0;count < userInput1.length() && anagram == true;count++)
						{
							input1Letter = userInput1.substring(count, count + 1);
							if (userInput2.indexOf(input1Letter) != -1)
								userInput2 = userInput2.replaceFirst(input1Letter, "");
							else
								anagram = false;
						}
						if (anagram == true)
							JOptionPane.showMessageDialog(null,message1);
						else
							JOptionPane.showMessageDialog(null, message2);
					}
					else
						JOptionPane.showMessageDialog(null, message2);
				}
			}
		}
	}


	/* Takes in word or phrase from and user. Will convert text to appropriate form to
	allow the code to check for palindromes. Checks if sentence or word is a palindrome at letter
	or word level. If the created array matched a reverse of it, it was a palindrome.
	It would also check code to see if it was a palindrome on word level.*/
	public static void determineIfWordPhraseSentenceIsPalindrome()
	{
		String words = getWordOrPhraseFromEndUser("Enter word or phrase: ", "Palindromes");
		if (words != null)
		{
			String words1array[];
			String wordsreverse = "";
			String result = "";
			words = words.toLowerCase().replaceAll("[^a-z\\s]","");
			if (words.length() == 0 || words.length() == 1)
				JOptionPane.showMessageDialog(null,"There were no valid characters to test. At least two alphabetic characters are required for a palindrome.");
			else
			{
				for (int i = words.replaceAll("\\s", "").length()-1; i>=0; i--)
					wordsreverse += words.replaceAll("\\s", "").substring(i,i+1);

				if (words.replaceAll("\\s", "").equals(wordsreverse))
					result = "Word/phrase is a palindrome.";
				else
				{
					result = "Word/phrase is not a palindrome at letter level\n";
					words1array = words.split(" ");

					int a = words1array.length-1;                 
					int c = 0;
					while ((words1array[c].equals(words1array[a])) && (c<a))
					{
						c++;
						a--;
					}
					if (c<a || words1array.length == 1)
						result += "and the word/phrase is not a palindrome at word level";
					else
						result += "but word/phrase is a palindrome at word level";
				}
				JOptionPane.showMessageDialog(null, result);
			}
		}
	/* Would display appropriate results depending on whether the word or sentence
	was a palindrome or not. Outputs appropriate error messages if user enters an incorrect input. */
	}


}