
/*****************************************************************
    CS4001301 Programming Languages                   
    
    Programming Assignment #1
    
    Java programming using subtype, subclass, and exception handling
    
    To compile: %> javac Application.java
    
    To execute: %> java Application

******************************************************************/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
class BankingException extends Exception {
    BankingException () { super(); }
    BankingException (String s) { super(s); }
} 

interface BasicAccount {
    String name();
    double balance();	
}

interface WithdrawableAccount extends BasicAccount {
    double withdraw(double amount) throws BankingException;	
}

interface DepositableAccount extends BasicAccount {
    double deposit(double amount) throws BankingException;	
}

interface InterestableAccount extends BasicAccount {
    double computeInterest() throws BankingException;	
}

interface FullFunctionalAccount extends WithdrawableAccount,
                                        DepositableAccount,
                                        InterestableAccount {
}

public abstract class Account {
	
    // protected variables to store commom attributes for every bank accounts	
    protected String accountName;
    protected double accountBalance;
    protected double accountInterestRate;
    protected Date openDate;
    protected Date lastInterestDate;
    
    // public methods for every bank accounts
    public String name() {
    	return(accountName);	
    }	
    
    public double balance() {
        return(accountBalance);
    }
    
    public double deposit(double amount) throws BankingException{
        accountBalance += amount;
        return(accountBalance);
    } 
    
    abstract double withdraw(double amount, Date withdrawDate) throws BankingException;
    
    public double withdraw(double amount) throws BankingException {
        Date withdrawDate = new Date();
        return(withdraw(amount, withdrawDate));
    }
    
    abstract double computeInterest(Date interestDate) throws BankingException;
    
    public double computeInterest() throws BankingException {
        Date interestDate = new Date();
        return(computeInterest(interestDate));
    }
    protected int calNumOfDays(Date Start,Date End){
    	int numOfDays=0;
    	LocalDate loc_start = Start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate loc_end = End.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	numOfDays=(int) ChronoUnit.DAYS.between(loc_start,loc_end);
    	return numOfDays;
    }
    protected int calNumOfMonths(Date Start,Date End){
    	int numOfMonths=0;
    	LocalDate loc_start = Start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate loc_end = End.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	numOfMonths=(int) ChronoUnit.MONTHS.between(loc_start,loc_end);
    	return numOfMonths;
    }
}

/*
 *  Derived class: CheckingAccount
 *
 *  Description:
 *      Interest is computed daily; there's no fee for
 *      withdraw; there is a minimum balance of $1000.
 */
                          
class CheckingAccount extends Account implements FullFunctionalAccount{

    CheckingAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;	
    }
    
    CheckingAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;	
    }	
    
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
    // minimum balance is 1000, raise exception if violated
        if ((accountBalance  - amount) < 1000) {
            throw new BankingException ("Underdraft from checking account name:" +
                                         accountName);
        } else {
            accountBalance -= amount;	
            return(accountBalance); 	
        }                                        	
    }
    
	public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfDays= calNumOfDays(lastInterestDate,interestDate);
        System.out.println("Number of days since last interest is " + numberOfDays);
        
        
        double interestEarned = (double) numberOfDays / 365.0 *
                                      accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned); 
        lastInterestDate = interestDate;
        accountBalance += interestEarned;
        return(accountBalance);                            
    }  	
}
/*
 *  Derived class: SavingAccount
 *
 *  Description:
 *      Monthly interest; fee of $1 for every transaction, except
 *		the first three per month are free; no minimum balance.
 */
                          
class SavingAccount extends Account implements FullFunctionalAccount{

	SavingAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;	
    }
	private Vector accessMonth=new Vector();
	SavingAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;	
    }	
	
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
    //checking if the account is still in the first 3-month
    	int numberOfMonths = calNumOfMonths(openDate,withdrawDate);
    	
    // free of 1$ transaction fee
    	if(!isMoreThan3SameMonthsIntheVec(numberOfMonths)){
    		if ((accountBalance  - amount) < 0) {
            throw new BankingException ("Underdraft from saving account name:" +
                                         accountName);
	        } else {
	            accountBalance -= amount;	
	            accessMonth.add(numberOfMonths);
	            return(accountBalance); 	
	        }        
    	}
    	else{
    	//charging 1$ transaction fee
    		if ((accountBalance  - amount) < 1) {
                throw new BankingException ("Underdraft from saving account name:" +
                                             accountName);
                } 
    		else {
    	            accountBalance -= (amount+1);	
    	            return(accountBalance); 	
    	        } 
    	}
                                       	
    }
    double deposit(double amount, Date depositDate) throws BankingException{
    	//checking if the account is still in the first 3-month
    	int numberOfMonths = calNumOfMonths(openDate,depositDate);
    	
    	// free of 1$ transaction fee
    	if(!isMoreThan3SameMonthsIntheVec(numberOfMonths)){
    		accountBalance += amount;
    		accessMonth.add(numberOfMonths);    
    	}
    	else{
    	//charging 1$ transaction fee
			accountBalance += (amount-1);
    	}
    	return (accountBalance); 
    }
    
    @Override
    public double deposit(double amount) throws BankingException{
    	Date depositDate = new Date();
        return(deposit(amount, depositDate));
    } 
    public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfMonths =calNumOfMonths(lastInterestDate,interestDate);
        System.out.println("Number of months since last interest is " + numberOfMonths);
        double interestEarned = (double) numberOfMonths *
                                      accountInterestRate / 12.0 * accountBalance;
        System.out.println("Interest earned is " + interestEarned); 
      //if numberOfMonths is less than a month, the interestDate should not be stored into  lastInterestDate
      //to prevent people who call computeInterest frequently earning no interest.
        if(numberOfMonths!=0){
        	lastInterestDate = interestDate;	
        	accountBalance += interestEarned;
        }
        return(accountBalance);                            
     }  	
    private boolean isMoreThan3SameMonthsIntheVec(int month){
    	int count=0;
    	for(int i=0;i<accessMonth.size()&&count<3;i++){
    		if((int)accessMonth.get(i)==month) count++;
    	}
    	if(count>=3)return true;
    	else return(false);
    }
}
/*
 *  Derived class: CDAccount
 *
 *  Description:
 *      monthly interest; fixed amount and duration (e.g., you can open
 * 		1 12-month CD for $5000; for the next 12 months you can't deposit
 * 		anything and withdrawals cost a  $250 fee); at the end of the 
 * 		duration the interest payments stop and you can withdraw w/o fee.
 */
                          
class CDAccount extends Account implements FullFunctionalAccount{
	protected int DurationInMonth;
	CDAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;
        DurationInMonth=12;
    }
    
	CDAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;
        DurationInMonth=12;
    }	
	CDAccount(String s, double firstDeposit,int Duration) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;
        DurationInMonth=Duration;
    }	
	CDAccount(String s, double firstDeposit, Date firstDate,int Duration) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;
        DurationInMonth=Duration;
    }	
	@Override
    public double deposit(double amount) throws BankingException{
		throw new BankingException ("Deposit is forbidden in CD account:" +
                accountName);
    } 
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
    // checking if the action is taking place in the CD duration;
    //if it is, charge 250$
    	int numberOfMonths =calNumOfMonths(openDate,withdrawDate);
    	if(numberOfMonths<DurationInMonth){
    		if ((accountBalance  - amount) < 250) {
            throw new BankingException ("Underdraft from CD account name:" +
                                         accountName);
    		} else {
            accountBalance -= (amount+250);	
            return(accountBalance); 	
    		}    
    	}
    	else {
    		if ((accountBalance  - amount) <0) {
                throw new BankingException ("Underdraft from CD account name:" +
                                             accountName);
        	} 
    		else {
                accountBalance -= amount;	
                return(accountBalance); 	
        	}    
    	}
    	                                    	
    }
    
    public double computeInterest (Date interestDate) throws BankingException {
    	if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfMonths =calNumOfMonths(lastInterestDate,interestDate);
        int numOfMonSinceTheAccountStarts=calNumOfMonths(openDate,interestDate);
        int numOfMonSinceTheAccountStartsTolastInterestDate=calNumOfMonths(openDate,lastInterestDate);
      //check if last Interest date is in the duration and if this request is made after the end of
      //of the duration. Either one of the both is true will lead to re-count the valid interest months.
        if(numOfMonSinceTheAccountStartsTolastInterestDate>DurationInMonth) {
        	System.out.println ("Due to the fulfillment of the contract, " +accountName+
        			" account is no longer receiving interest!");
        	return accountBalance;
        }
        else if(numOfMonSinceTheAccountStarts>DurationInMonth) 
        	numberOfMonths-=(numOfMonSinceTheAccountStarts-DurationInMonth);
        System.out.println("Number of months since last interest is " + numberOfMonths);

        double interestEarned = (double) numberOfMonths *
                                      accountInterestRate / 12.0 * accountBalance;
        System.out.println("Interest earned is " + interestEarned); 
      //if numberOfMonths is less than a month, the interestDate should not be stored into lastInterestDate
      //to prevent people who call computeInterest frequently earning no interest.
        if(numberOfMonths!=0){
        	lastInterestDate = interestDate;	
        	accountBalance += interestEarned;
        }
        return(accountBalance); 
    }
}

/*
 *  Derived class: LoanAccount
 *
 *  Description:
 *      like a saving account, but the balance is "negative" (you owe
 * 		the bank money, so a deposit will reduce the amount of the loan);
 * 		you can't withdraw (i.e., loan more money) but of course you can 
 * 		deposit (i.e., pay off part of the loan).
 */
                          
class LoanAccount extends Account implements FullFunctionalAccount{

	LoanAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;	
    }
    
	LoanAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;	
    }	
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
    	throw new BankingException ("Withdraw is forbidden in loan account:" +
                accountName);                             	
    }
    @Override
    public double deposit(double amount) throws BankingException{
    	if(accountBalance>=0) System.out.println("This account is clean and closed. No need to deposit anymore!");  
    	else accountBalance+=amount;
    	return(accountBalance);
    } 
    public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfMonths = calNumOfMonths(lastInterestDate,interestDate);
        System.out.println("Number of months since last interest is " + numberOfMonths);
        double interestEarned = (double) numberOfMonths / 12.0 *
                                      accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned); 
        //if numberOfMonths is less than a month, the interestDate should not be stored into lastInterestDate
        //to prevent people who call computeInterest frequently earning no interest.
          if(numberOfMonths!=0){
          	lastInterestDate = interestDate;	
          	accountBalance += interestEarned;
          }
        return(accountBalance);                            
    }  	
}           