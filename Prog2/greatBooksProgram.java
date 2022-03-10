import java.util.*;
import java.io.*;


public class greatBooksProgram
{
	public static void main(String[] args)
	{
		//clear the screen first and declare the array-list
		clearScreen();
		String fileName = startMenu();
		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>();

		//input book info
		input(fileName, books);
		
		//after inputting the book information, run the main search menu
		menu(books);
	}

	//Methods
	//prints info each library book contains
	private static void printBooks(ArrayList<LibraryBook> books, int element)
	{
		clearScreen();
		System.out.println("Book # " + (element + 1));
		System.out.println("\n" + "Title:" + "\t" + "\t" + "\t" + books.get(element).getTitle());
		System.out.println("Author's Name:" + "\t" + "\t" + books.get(element).getAuthor());
		System.out.println("Copyright:" + "\t" + "\t" + books.get(element).getCopyright());
		System.out.println("Price:" + "\t" + "\t" + "\t" + books.get(element).getPrice());
		System.out.println("Genre:" + "\t" + "\t" + "\t" + books.get(element).getGenre());
		enterToContinue();

		
	}

	//runs through a loop to display all the books in the file
	public static void displayRecords(ArrayList<LibraryBook> books)
	{
		for(int i = 0; i < books.size(); i++)
		{
			printBooks(books, i);
		}
	}

	//this is the display menu to view or search for books that are recorded
	private static void printDisplayMenu()
	{
	  	 System.out.println("\n" + "THE GREAT BOOKS SEARCH PROGRAM");
       		 System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       		 System.out.println("\n" + "1) Display all books recorded");
       		 System.out.println("\n" + "2) Search for a book by title");
       		 System.out.println("\n" + "3) Exit");
       		 System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        	 System.out.print("Please Enter Your Choice >  ");
	}

	//menu loop for if the user pickes between 1-3
	private static void menu(ArrayList<LibraryBook> books)
	{
		printDisplayMenu();
		Scanner in = new Scanner(System.in);
		int option = 0;
		while(option!= 3)
		{
			option = in.nextInt();
			int pick;
			switch(option)
			{
				case 1: displayRecords(books);
					printDisplayMenu();
					break;
				
				case 2: pick = findBook(books);
					if(pick == -1)
					{
						System.out.println("Sorry, the book you entered was not found");
						System.out.print("Please enter a valid option:  ");
					}
					else
					{
						printBooks(books, pick);
						clearScreen();
						printDisplayMenu();
					}
					break;
				
				case 3: clearScreen();
					System.out.println("Exit chosen, goodbye!");
					break;
				
				default: clearScreen();	
					printDisplayMenu();
					System.out.println("That is not an option. Please enter a valid option.");
					break;
			}
		}
	}

	//this method finds the files in the directory with the .dat format, and prints them
	public static String bookFiles()
	{
		
	       	File curDir = new File(".");
		String[] fileNames = curDir.list();
       		ArrayList<String> data = new ArrayList<String>();
	
		for(String s:fileNames)
		{
        		if(s.endsWith(".dat"))
			{	
               			data.add(s);
			}

		}

		String files = "";
		for(String element: data)
		{
			files += (element + "  ");
		}
		return files;

	}

	//The selection sort method
	public static void sortBooks(ArrayList<LibraryBook> books)
	{
		int minIndex, index, j;
		LibraryBook temp;

		for(index = 0; index < books.size() - 1; index++)
		{
			minIndex = index;
			for(j = minIndex + 1; j < books.size(); j++)
			{
				if(books.get(j).getTitle().compareTo(books.get(minIndex).getTitle()) < 0)
				{
					minIndex = j;
				}
			}

			if(minIndex != index)
			{
				temp = books.get(index);
				books.set(index, books.get(minIndex));
				books.set(minIndex, temp);
			}
		}

	}

	//this method will find the books
	private static int findBook(ArrayList<LibraryBook> books)
	{
		int element = 0; 
		String title = "";
		System.out.print("Enter book title: ");
		Scanner in = new Scanner(System.in);
		title = in.nextLine();

		int first = 0; 
		int last = books.size() - 1;
		int  middle;
		boolean found = false;

		do
		{
			middle = (first + last) / 2;
			if((books.get(middle)).getTitle().compareTo(title) == 0)
			{
				found = true;
			}
		
			else if(books.get(middle).getTitle().compareTo(title) > 0)
			{
				last = middle - 1;
			}
			else
			{
				first = middle + 1;
			}

		} 
		while((!found) && (first <= last));
		
		element = middle;
		
		return(found ? element : -1);

	}

	//clear screen method
	private static void clearScreen()
	{
       		System.out.println("\u001b[H\u001b[2J");
   	}

	//inputs title, author name,copyright, price, genre, also has try catch statement
	public static int input(String fileName, ArrayList<LibraryBook> books)
	{
		int numbooks = 0;
		try
		{
			Scanner scan = new Scanner(new File(fileName));
			while(scan.hasNext())
			{
				Scanner in = new Scanner(scan.nextLine()).useDelimiter(";");
				String title = in.next();
				String name = in.next();
				int copyright = in.nextInt();
				double price = in.nextDouble();
				String genre = in.next();
				books.add(new LibraryBook(title, name, copyright, price, genre));
				numbooks++;
			}
			
		}

		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}

		sortBooks(books);
		System.out.println("\n" + "There are a total of " + numbooks + " books in your file that you entered.");
		enterToContinue();
		clearScreen();
		return numbooks;	
	}
	
	//the enter to continue method
	private static void enterToContinue()
	{
		System.out.print("\n" + "Please press ENTER to continue in the program when you are ready");
		Scanner scann = new Scanner(System.in);
		scann.nextLine();
		clearScreen();
	}
	
	//menu that runs at startup,	
	public static String startMenu()
	{
		clearScreen();
		System.out.println("\n" + "\tWELCOME TO THE GREAT BOOKS PROGRAM");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("\n" + "Here are the current files in your directory, please select which one you would like to choose");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("\n" + bookFiles());
		Scanner scan = new Scanner(System.in);
		String result = scan.nextLine();
		return result;
	}

}
