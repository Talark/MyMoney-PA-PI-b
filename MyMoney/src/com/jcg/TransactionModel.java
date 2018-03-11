package com.jcg;

public class TransactionModel {
private int transactionId;
private double amount;
private String transactionDate;
private int transactionTypeId;
private int transactionCategoryId;

public TransactionModel(int transactionId, double amount, String transactionDate, int transactionTypeId, int transactionCategoryId) {
	super();
	this.transactionId = transactionId;
	this.amount = amount;
	this.transactionDate = transactionDate;
	this.transactionTypeId = transactionTypeId;
	this.setTransactionCategoryId(transactionCategoryId);
}
public int getTransactionId() {
	return transactionId;
}
public double getAmount() {
	return amount;
}
public String getTransactionDate() {
	return transactionDate;
}
public int getTransactionTypeId() {
	return transactionTypeId;
}
public void setTransactionId(int transactionId) {
	this.transactionId = transactionId;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public void setTransactionDate(String transactionDate) {
	this.transactionDate = transactionDate;
}
public void setTransactionTypeId(int transactionTypeId) {
	this.transactionTypeId = transactionTypeId;
}
public int getTransactionCategoryId() {
	return transactionCategoryId;
}
public void setTransactionCategoryId(int transactionCategoryId) {
	this.transactionCategoryId = transactionCategoryId;
}
}
