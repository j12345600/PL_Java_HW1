
/*****************************************************************
    CS4001301 Programming Languages                   
    
    Programming Assignment #1
    
    Java programming using subtype, subclass, and exception handling
    
    To compile: %> javac Application.java
    
    To execute: %> java Application

******************************************************************/

import java.text.SimpleDateFormat;
import java.util.*;



public class Application {
	
public static void main( String args []) {
    Account a;
    Date d;
    double ret;
    String datT = "2015-4-24";
    String datT2="2016-3-23";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date t = null;
    Date t2=null;
    Date t3=null;
    try{
      t = formatter.parse(datT);
      t2 = formatter.parse(datT2);
      t3=formatter.parse("2017-12-23");
    }catch(Exception as){;}  
     
    /* put your own tests here ....... */
	/* if your implementaion is correct, you can do the following with polymorphic array accountList*/
		Account[] accountList;
		
		accountList = new Account[4];
		
		// buid 4 different accounts in the same array
		accountList[0] = new CheckingAccount("John Smith(Checking)", 2200.0,t);
		accountList[1] = new SavingAccount("William Hurt(Saving)", 2200.0,t);
		accountList[2] = new CDAccount("Woody Allison(CD)", 2000.0,t);
		accountList[3] = new LoanAccount("Judi Foster(Loan)", -1500.0,t);
		for(int i=0;i<accountList.length;i++){
			System.out.println(accountList[i].name()+" Balance: "+
		accountList[i].balance()+" "+formatter.format(accountList[i].openDate));
		}
		
		// compute interest for all accounts
		test_interest(accountList,t2,t3);
		test_withdraw(accountList,t2,t3);
		test_deposit(accountList,t2,t3);
}

static void stdExceptionPrinting(Exception e, double balance) {
	    System.out.println("EXCEPTION: Banking system throws a " + e.getClass() +
	                       " with message: \n\t" +
	                       "MESSAGE: " + e.getMessage());
	    System.out.println("\tAccount balance remains $" + balance + "\n");
    }
static void test_interest(Account[] accountList,Date t2,Date t3){
	double newBalance=0.0;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	System.out.println ("------------------------------------------------------------------\n");
	System.out.println(formatter.format(new Date())+" computeInterest");

		for (int count = 0; count < accountList.length; count++) {
			newBalance=accountList[count].balance();
			System.out.println ("Account <" + accountList[count].name() + "> has $" +
					newBalance +" balance before operating");
			try{
			newBalance = accountList[count].computeInterest();
			System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			} catch (Exception e) {stdExceptionPrinting(e, newBalance);}
			//System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t2)+" computeInterest");
		for (int count = 0; count < accountList.length; count++) {
			newBalance=accountList[count].balance();
			System.out.println ("Account <" + accountList[count].name() + "> has $" +
					newBalance +" balance before operating");
			try{
			newBalance = accountList[count].computeInterest(t2);
			System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			} catch (Exception e) {stdExceptionPrinting(e, newBalance);}
			//System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t3)+" computeInterest");
		for (int count = 0; count < accountList.length; count++) {
			newBalance=accountList[count].balance();
			System.out.println ("Account <" + accountList[count].name() + "> has $" +
					newBalance +" balance before operating");
			try{
			newBalance = accountList[count].computeInterest(t3);
			System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			} catch (Exception e) {stdExceptionPrinting(e, newBalance);}
			//System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t3)+" computeInterest");
		for (int count = 0; count < accountList.length; count++) {
			newBalance=accountList[count].balance();
			System.out.println ("Account <" + accountList[count].name() + "> has $" +
					newBalance +" balance before operating");
			try{
			newBalance = accountList[count].computeInterest(t3);
			System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			} catch (Exception e) {stdExceptionPrinting(e, newBalance);}
			//System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" computeInterest");
		for (int count = 0; count < accountList.length; count++) {
			newBalance=accountList[count].balance();
			System.out.println ("Account <" + accountList[count].name() + "> has $" +
					newBalance +" balance before operating");
		try{
			newBalance = accountList[count].computeInterest(new Date());
			System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

		} catch (Exception e) {stdExceptionPrinting(e, newBalance);}
	}
	
}	
static void test_withdraw(Account[] accountList,Date t2,Date t3){
	double newBalance=0.0;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" withdraw 500");
		for (int count = 0; count < accountList.length; count++) {
			try {
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(500,new Date());
				System.out.println ("Account <" + accountList[count].name() + "> now has $" +
				newBalance +"balance\n");
			 } catch (Exception e) {
			       stdExceptionPrinting(e, newBalance);	
			 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t2)+" withdraw 250");
		for (int count = 0; count < accountList.length; count++) {
			try {
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(250,t2);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			} catch (Exception e) {
			       stdExceptionPrinting(e, newBalance);	
			 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t3)+" withdraw 250");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(250,t3);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" withdraw 100");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(100);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" withdraw 100");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(100);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" withdraw 100");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(100);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t3)+" withdraw 100");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].withdraw(100,t3);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
}

static void test_deposit(Account[] accountList,Date t2,Date t3){
	double newBalance=0.0;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" deposit 500");
		for (int count = 0; count < accountList.length; count++) {
			try {
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(500);
				System.out.println ("Account <" + accountList[count].name() + "> now has $" +
				newBalance +"balance\n");
			 } catch (Exception e) {
			       stdExceptionPrinting(e, newBalance);	
			 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t2)+" deposit 250");
		for (int count = 0; count < accountList.length; count++) {
			try {
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(250);
				System.out.println ("Account <" + accountList[count].name() 
						+ "> now has $" + newBalance + " balance\n");

			} catch (Exception e) {
			       stdExceptionPrinting(e, newBalance);	
			 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t3)+" deposit 250");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(250);
				System.out.println ("Account <" + accountList[count].name() 
						+ "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" deposit 100");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(100);
				System.out.println ("Account <" + accountList[count].name() 
						+ "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" deposit 100");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(100);
				System.out.println ("Account <" + accountList[count].name()
						+ "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(new Date())+" deposit 1000");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(1000);
				System.out.println ("Account <" + accountList[count].name()
						+ "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
		System.out.println ("------------------------------------------------------------------\n");
		System.out.println(formatter.format(t3)+" deposit 1000");
		for (int count = 0; count < accountList.length; count++) {
			try{
				newBalance=accountList[count].balance();
				System.out.println ("Account <" + accountList[count].name() + "> has $" +
						newBalance +" balance before operating");
				newBalance = accountList[count].deposit(1000);
				System.out.println ("Account <" + accountList[count].name()
						+ "> now has $" + newBalance + " balance\n");

			}
		 catch (Exception e) {
		       stdExceptionPrinting(e, newBalance);	
		 }
		}
}


}    