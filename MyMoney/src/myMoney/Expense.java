package myMoney;

import java.util.Date;

public class Expense {

	private Float amount;
	private Date date;
	private String catName;

//	================================================================
	// This is a Accessor method
	public Float getAmount() {
		return amount;
	}
//	================================================================
	// This is a Mutator method
	public void setAmount(Float amount) { 
		this.amount = amount;
	}
//	================================================================
	// This is a Accessor method
	public Date getDate() {
		return date;
	}
//	================================================================
	// This is a Mutator method
	public void setDate(Date date) {
		this.date = date;
	}
//	================================================================
	// This is a Accessor method
	public String getcatName() {
	return catName;
	}
//	================================================================
	// This is a Mutator method
	public void setcatName(String catName) {
		this.catName = catName;
	}
//	================================================================
	// The creation of these methods is part of encapsulation.
	// Member variables of a class are made private to hide and protect them from other code, 
	// and can only be modified by a public member function (the mutator and accessor methods), 
	// which takes the desired new value as a parameter, optionally validates it, 
	// and modifies the private member variable.
}
