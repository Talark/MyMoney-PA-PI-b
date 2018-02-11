package myMoney;

import java.util.ArrayList;

public class Repository {
	
//	The Repository class maintains a static reference to the lone singleton instance 
//	and returns that reference from the static getInstance() method.
//	Here, Repository class employs a technique known as lazy instantiation to create the singleton; 
//	as a result, the singleton instance is not created until the getInstance() method is called for 
//	the first time. This technique ensures that singleton instances are created only when needed.

	public ArrayList<Expense> expList = new ArrayList<Expense>(); // creating an arrayList expList
	public ArrayList<Category> catList = new ArrayList<Category>(); // creating an arrayList catList
	// the reason behind using an arrayList is because it is a resizable array. 
	// it handles the resizing automatically

	
	private static Repository instance = null; // static variable instance of type Repository

//	================================================================
	//Create a static method to get instance.
	public static Repository getinstance() {
		if (instance == null) {
			instance = new Repository();
		}
		return instance;
	}
//	================================================================

}
