package myMoney;

import java.io.IOException;
import java.util.Date;
//import java.util.List;
import java.util.Scanner;

public class Account {
	
	private static double balance=0.0;
	private Scanner scanner = new Scanner(System.in);
	private char choice = '\0';
	
	Repository repo = Repository.getinstance(); //instantiating Repository class with variable repo
	
//	================================================================

	public void showMenu() {
		
		while (true) {
			printMenu();
			switch (choice) {
			case 'A':
				System.out.println("----------");
				System.out.println("Please enter amount to deposit");
				System.out.println("----------");
				float addamount = scanner.nextFloat();
				balance = balance + addamount;
				pressAnyKeyToContinue();
				break;
				
			case 'B':
				System.out.println("----------");
				System.out.println("Please enter amount to withdraw");
				System.out.println("----------");
				float depoamount = scanner.nextFloat();
				balance = balance - depoamount;
				pressAnyKeyToContinue();
				break;
				
			case 'C':
				System.out.println("----------");
				addCategory();
				System.out.println("----------");
				System.out.println("\n");
				pressAnyKeyToContinue();
				break;

			case 'D':
				System.out.println("----------");
				categoryList();
				System.out.println("----------");
				System.out.println("\n");
				pressAnyKeyToContinue();
				break;

			case 'E':
				System.out.println("----------");
				expenseEntry();
				System.out.println("----------");
				System.out.println("\n");
				pressAnyKeyToContinue();
				break;

			case 'F':
				System.out.println("----------");
				expenseList();
				System.out.println("----------");
				System.out.println("\n");
				pressAnyKeyToContinue();
				break;
				
			case 'G':
				System.out.println("----------");
				System.out.println("the balance is: "+ balance);
				System.out.println("----------");
				pressAnyKeyToContinue();
				break;

			case 'X':
				System.out.println("Bye!");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid Option!!. Please try again");
				System.out.println("\n");
				break;
			}
		}
	}
//	================================================================

	public void printMenu() {
		System.out.println("A. Deposit Cash");
		System.out.println("B. Withdraw Cash");
		System.out.println("C. Add Expence Category");
		System.out.println("D. Show Expence Category List");
		System.out.println("E. Make an Expense Entry");
		System.out.println("F. Show Expense List");
		System.out.println("G. Show Balance");
		System.out.println("X. Exit");
		System.out.println("-----------------------");
		System.out.println("Enter Your Choice: ");
		
		choice = Character.toUpperCase(scanner.next().charAt(0)); 
		// this will change LowerCase inputs to UpperCase
	}
//	================================================================

	public void addCategory() {
		
		scanner.nextLine();
		System.out.println("Enter category Name: ");
		String catName = scanner.nextLine();
		Category cat = new Category(catName); 
		// Create an instance of the Category class using the user input.
		repo.catList.add(cat);
		// Adds the user value into the ArrayList catList
		System.out.println("Category Added. ");
	}
//	================================================================
	
	public void categoryList() {
		System.out.println("category list");
		for (int i = 0; i < repo.catList.size(); i++) {
			Category c = repo.catList.get(i);
			System.out.println((i + 1) + ". " + c.getcatName());
		}// this is to retrieve the values in the ArrayList catList and displaying it. 
	}
//	================================================================
	
	public void expenseEntry() {
		System.out.println("expense entry");
		categoryList();
		System.out.println("choose a category: ");
		
		int catChoice = scanner.nextInt();
		Category selectedCat = repo.catList.get(catChoice - 1); 
		// this is to retrieve the value in the ArrayList catList from the position (catChoice - 1).

		System.out.println("Enter amount ");
		float amount = scanner.nextFloat();
		
		balance = balance - amount;


		System.out.println("Enter date ");
		Date date = new Date();

		Expense exp = new Expense(); // Create an instance of the Expense class.
		exp.setcatName(selectedCat.getcatName()); 
		exp.setAmount(amount);
		exp.setDate(date);
		// this is to set the values in the fields in the Expense class.
		
		
		repo.expList.add(exp); // this is to set the values from the Expense class into the ArrayList expList
		System.out.println("success : exp added");
	}
//	================================================================
	
	public void expenseList() {
		System.out.println("expense list");
		for (int i = 0; i < repo.expList.size(); i++) {
			Expense exp = repo.expList.get(i);
			System.out.println((i + 1) + ". " + exp.getcatName() + ". " + exp.getAmount() + ". " + exp.getDate() + ". ");
		}// this is to retrieve the values in the ArrayList expList and displaying it. 
	}
//	================================================================
	
	public void pressAnyKeyToContinue() {
		System.out.println("press any key to go back to Main Menue: ");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	================================================================

}
