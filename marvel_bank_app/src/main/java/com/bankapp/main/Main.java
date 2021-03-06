package com.bankapp.main;


import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankapp.account.dao.AccountDAO;
import com.bankapp.account.dao.impl.AccountDAOImpl;
import com.bankapp.account.model.Account;
import com.bankapp.account.service.AccountService;
import com.bankapp.account.service.impl.AccountServiceImpl;
import com.bankapp.customer.model.Customer;
import com.bankapp.customer.service.CustomerService;
import com.bankapp.customer.service.impl.CustomerServiceImpl;
import com.bankapp.employee.dao.EmployeeDAO;
import com.bankapp.employee.dao.impl.EmployeeDAOImpl;
import com.bankapp.employee.model.Employee;
import com.bankapp.exception.BankException;
import com.bankapp.exception.LoginException;
import com.bankapp.transaction.model.Transaction;
import com.bankapp.transaction.service.TransactionService;
import com.bankapp.transaction.service.impl.TransactionServiceImpl;

public class Main {
	
	private static Logger log=Logger.getLogger(Main.class);

	public static void main(String[] args) {
		
		int outerOption = 0;
		Scanner scan=new Scanner(System.in);
		EmployeeDAO empService = new EmployeeDAOImpl();
		AccountDAO accDAO = new AccountDAOImpl();
		CustomerService custService = new CustomerServiceImpl();
		AccountService accService = new AccountServiceImpl();
		TransactionService tranService = new TransactionServiceImpl();
		
		do {
			log.info("\n=============================================");
			log.info("Welcome to the Marvel Bank App V1.0! \nMarvel Bank: \"A Super Bank for Super Heroes!\"");  //MAIN MENU
			log.info("=============================================");
			log.info("1) Customer Login");
			log.info("2) Employee Login");
			log.info("3) Exit");
			log.info("=============================================");
			try {
				outerOption=Integer.parseInt(scan.nextLine());
			}catch(NumberFormatException e) {}
			
			switch (outerOption) {
			case 1:
				int option=0;
				
				do{
				log.info("\n===========================================");
				log.info("Welcome to Customer Login");                        //CUSTOMER LOGIN SUB MENU
				log.info("Please choose one of the following options");
				log.info("===========================================");
				log.info("1) Login");
				log.info("2) Create an Account");
				log.info("3) Exit");
				log.info("===========================================");
				try {
					option=Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {}
				
				switch (option) {
				case 1:
					log.info("\nPlease enter your user email address");        //CUSTOMER LOGIN
					String email=scan.nextLine();
					log.info("Please enter your password");
					String password=scan.nextLine();
					try {                                    
							if (custService.custLogin(email, password)==false) {
								log.warn("The email and password did not match please try again");
								break;
						}
					} catch (LoginException e) {
						log.error(e);
					}
	                 
					int accountId = accDAO.getIdByEmailPassword(email, password); //Gets ID for other processes
					                                                             
					int subOption=0;
					do{
					log.info("\n==================================");
					log.info("Welcome back HERO!");                         //MAIN CUSTOMER MENU
					log.info("What would you like to do today?");
					log.info("==================================");
					log.info("1) View My Bank Accounts");
					log.info("2) Open a New Bank Account");
					log.info("3) Log Out");
					log.info("==================================");
					try {
						subOption=Integer.parseInt(scan.nextLine());
					}catch(NumberFormatException e) {}
					
					switch (subOption) {
					case 1:
						List<Account> accList;
						try {
							log.info("\nDisplaying your accounts:");    //VIEW BANK ACCOUNTS
							log.info("==============================================================================================================================");
							accList = accService.listBankAccById(accountId);
							for(int i=0;i<accList.size();i++) {
								System.out.println(accList.get(i));
							}
						} catch (BankException e) {
							log.error(e);
							break;
						}
						log.info("==============================================================================================================================");
						
						log.info("\nEnter the account number of the account you would like to work with today");  
						int accountNumber = Integer.parseInt(scan.nextLine());
						int subOption2=0;
						do{
						log.info("\nDisplaying account details :");          
						log.info("==============================================================================================================================");
						try {
							log.info(accService.getBankAccByNum(accountNumber));
						} catch (BankException e) {
							log.error(e);
						}
						if(accountNumber%10==0) { //To not allow pending accounts to be used
							break;
						}
						log.info("==============================================================================================================================");
						log.info("\n==============================================");
						log.info("What would you like to do with this account?"); //MY BANK ACCOUNT CUSTOMER SUB MENU
						log.info("==============================================");
						log.info("1) Withdrawal");
						log.info("2) Deposit");
						log.info("3) Transfer");
						log.info("4) Transaction History");
						log.info("5) Return");
						log.info("==============================================");
						
						try {
							subOption2=Integer.parseInt(scan.nextLine());
						}catch(NumberFormatException e) {}
							
						switch (subOption2) {
						case 1:
							Transaction transactionW = new Transaction();
							log.info("\nEnter the amount you would like to withdraw");  //WITHDRAWAL
							int amountW = Integer.parseInt(scan.nextLine());
							transactionW.setAccountnumber(accountNumber);
							transactionW.setAmount(amountW);
							try {
								tranService.performWithdrawal(transactionW);
								log.info("Withdrawal Successful");
							} catch (BankException e) {
								log.error(e);
							}
						

							break;
						case 2:
							Transaction transactionD = new Transaction();
							log.info("\nEnter the amount you would like to deposit"); //DEPOSIT
							int amountD = Integer.parseInt(scan.nextLine());
							transactionD.setAccountnumber(accountNumber);
							transactionD.setAmount(amountD);
							try {
								tranService.performDeposit(transactionD);
								log.info("Deposit Successful");
							} catch (BankException e) {
								log.error(e);
							}
							

							break;
						case 3:
							Transaction transactionT = new Transaction(); //TRANSFER  
							log.info("\nEnter the amount you would like to transfer");
							int amountT = Integer.parseInt(scan.nextLine());
							log.info("Enter the account number of the account you are transfering to");
							int accountNumber2 = Integer.parseInt(scan.nextLine());
							if(accountNumber2%10==0) {
								log.warn("You CANNOT transfer to a PENDING acount");     //Cannot transfer to PENDING account
							}
							transactionT.setAccountnumber(accountNumber);
							transactionT.setAmount(amountT);
							try {
								tranService.performTransfer(transactionT, accountNumber2);
								log.info("Transfer Successful");
							} catch (BankException e) {
								log.error(e);
							}
							

							break;
						case 4:
							log.info("\nTransaction History");;               //TRANSACTION HISTORY
							List<Transaction> transList0;
							try {
								log.info("Displaying the transaction History of the current account:");
								log.info("==============================================================================================================================");
								transList0 = tranService.listTransactionByAccNum(accountNumber); 
								for (int i = 0; i < transList0.size(); i++) {
									log.info(transList0.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}

							break; 
						case 5: //EXIT

							break;

						default:
							log.warn("Please enter only a number between 1-5");
							break;
						}
						}while(subOption2!=5);
						
						break;
					case 2:
						log.info("\nOpening a NEW BANK account:");                       //CUSTOMER OPEN NEW BANK ACCOUNT
						Account account = new Account();
						log.info("=======================================================");
						log.info("Please select the account type you would like to open");
						log.info("=======================================================");
						log.info("1) Checkings");
						log.info("2) Savings");
						log.info("=======================================================");
						int choice=Integer.parseInt(scan.nextLine());
						if(choice==1) {
							account.setAccountType("Checkings");
							try {
								accService.openNewBankAcc(account, accountId);
							} catch (BankException e) {
								log.error(e);
							}
							log.info("Checkings account created successfully, please allow 2-3 business days for approval");
						}else if(choice==2){
							account.setAccountType("Savings");
							try {
								accService.openNewBankAcc(account, accountId);
							} catch (BankException e) {
								log.error(e);
							}
							log.info("Savings account created successfully, please allow 2-3 business days for approval");
						}else {
							log.warn("Please only choose from the options provided");
						}

						break;
					case 3:
						log.info("\nThank you for banking with Marvel Bank! Continue being HEROIC!"); //CUSTOMER LOGOUT
						break;

					default:
						log.warn("Please choose only an option between 1-3");
						break;
					}
					
					}while(subOption!= 3);
					
					break;
				case 2:
					log.info("\nCreating a new CUSTOMER ACCOUNT");  //CREATE NEW CUSTOMER ACCOUNT
					Customer customer=new Customer();
					log.info("Enter your first name");
					customer.setFirstName(scan.nextLine());
					log.info("Enter your last name");
					customer.setLastName(scan.nextLine());
					log.info("Enter your address");
					customer.setAddress(scan.nextLine());
					log.info("Enter your phone number (XXX-XXX-XXXX)");
					customer.setNumber(scan.nextLine());
					log.info("Enter your email address");
					customer.setEmail(scan.nextLine());
					log.info("Provide a password to complete account");
					customer.setPassword(scan.nextLine());
					
					
					try {
						if(custService.createNewCustAcc(customer)==1) {
							log.info("\nRegistered successfully with below details: ");
							log.info("==============================================================================================================================");
							log.info(customer);
							log.info("==============================================================================================================================");
							log.info("Thank you for creating an account with us!");
							log.info("Login through the customer login menu and follow the steps to open your first account!");
						}
					} catch (LoginException e) {
					log.error(e);
					}
					break;
				case 3:
					log.info("Exiting to Main Menu");   //EXIT
					
					break;

				default:
					log.warn("Please enter a number 1-3 ONLY");
					break;
				}
				}while(option!=3);

				break;
			case 2:
				log.info("\n===========================");
				log.info("Welcome to Employee Login");                         //EMPLOYEE LOGIN
				log.info("===========================");
				int check = 0;
				Employee employee = new Employee();

				while (check == 0) {
					log.info("Please enter your user ID");
					int empId = Integer.parseInt(scan.nextLine());
					employee.setEmpId(empId);
					log.info("Please enter your password");
					String password = scan.nextLine();
					employee.setPassword(password);

					if (empService.empLogin(employee) == false) {
						log.warn("The ID and password did not match please try again");
					} else {
						check = 1;
					}
				}

				int subOption=0;
				
				do{
				log.info("\n=============================================");
				log.info("Welcome back! How will you be a HERO today?"); //EMPLOYEE MAIN MENU
				log.info("=============================================");
				log.info("1) Manage Customer Accounts");
				log.info("2) Manage Bank Accounts");
				log.info("3) Manage Transactions");
				log.info("4) Log Out");
				log.info("=============================================");
				try {
					subOption=Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {}
					 
				switch (subOption) {
				case 1:
					int subOption2 = 0;
					do{
						log.info("\n======================================");
						log.info("Welcome to the Customer Account Menu");  //EMPLOYEE MANAGE CUSTOMER ACCOUNTS SUB MENU
						log.info("======================================");
						log.info("1) Find Customer by ID");
						log.info("2) Find Customer by Lastname");
						log.info("3) List All Customer Accounts");
						log.info("4) Update Customer Account(by ID)");
						log.info("5) Create NEW Customer Account");
						log.info("6) Delete Customer Account");
						log.info("7) Return");
						log.info("======================================");
						try {
							subOption2=Integer.parseInt(scan.nextLine());
						}catch(NumberFormatException e) {}
					
						switch (subOption2) {
						case 1:
							Customer findCustomer = new Customer(); 
							log.info("Please enter the account ID of the customer you are looking for"); //FIND CUST BY ID
							int accountId=Integer.parseInt(scan.nextLine());
							
							try {
								findCustomer=custService.getCustomerAccByid(accountId);
								log.info("The account matching that ID is displayed below: ");
								log.info("==============================================================================================================================");
								log.info(findCustomer);
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}
						
							
							break;
						case 2:
							log.info("\nEnter the last name of the customer you are looking for");  //FIND CUST BY LAST NAME
							List<Customer> custList;
							String lastName = scan.nextLine();
							try {
								log.info("Listing all Customer Accounts with the last name "+lastName);
								log.info("==============================================================================================================================");
								custList = custService.listCustByLastName(lastName);
								for(int i=0;i<custList.size();i++) {
									System.out.println(custList.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}
							
							
							break;
						case 3:                                                   //LIST ALL CUSTOMERS 
							List<Customer> custList2;                            
							try {
								log.info("\nListing all Customer Accounts:");
								log.info("==============================================================================================================================");
								custList2 = custService.listAllCustAccounts();
								for(int i=0;i<custList2.size();i++) {
									log.info(custList2.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}

							break;
						case 4:
							Customer getCustomer = new Customer();                   //UPDATE CUSTOMER ACCOUNT BY ID
							log.info("Please provide the account ID of the customer account your would like to update");
							int accountId2=Integer.parseInt(scan.nextLine());
							
							try {
								getCustomer=custService.getCustomerAccByid(accountId2);
							} catch (BankException e) {
								log.error(e);
							}
							
							log.info("\nThe account matching that ID is displayed below: ");   //TO CHOOSE WHAT TO UPDATE
							log.info("==============================================================================================================================");
							log.info(getCustomer);
							log.info("==============================================================================================================================");
							log.info("\n================================");
							log.info("What would you like to update");
							log.info("================================");
							log.info("1) Firstname");
							log.info("2) Lastname");
							log.info("3) Address");
							log.info("4) Number");
							log.info("5) Email");
							log.info("6) Password");
							log.info("================================");
							int choice=Integer.parseInt(scan.nextLine());
							
							log.info("Please enter the updated information");
							String update = scan.nextLine();
							
							try {
								custService.updateCustomerAccByid(accountId2, choice, update);
							} catch (BankException e) {
								log.error(e);
							}
							
							try {
								getCustomer=custService.getCustomerAccByid(accountId2);
								log.info("The updated Customer Account is displayed below: ");
								log.info("==============================================================================================================================");
								log.info(getCustomer);
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}

							break;
						case 5:                                                             //EMPLOYEE CREATE NEW CUSTOMER ACCOUNT
							log.info("\nCreating a NEW customer account");                              
							Customer customer=new Customer();
							log.info("Enter customer first name");
							customer.setFirstName(scan.nextLine());
							log.info("Enter customer last name");
							customer.setLastName(scan.nextLine());
							log.info("Enter customer address");
							customer.setAddress(scan.nextLine());
							log.info("Enter customer phone number (XXX-XXX-XXXX)");
							customer.setNumber(scan.nextLine());
							log.info("Enter customer email address");
							customer.setEmail(scan.nextLine());
							log.info("Provide a password for the customer");
							customer.setPassword(scan.nextLine());
							
							
							try {
								if(custService.createNewCustAcc(customer)==1) {
									log.info("Customer Account created successfully with below details: ");
									log.info("==============================================================================================================================");
									log.info(customer);
									log.info("==============================================================================================================================");
								}
							} catch (LoginException e) {
								log.error(e);;
							}

							break;
						case 6:
							log.info("\nCustomer Account Deletion");                     //DELETE CUSTOMER BY ID
							log.info("Enter the account ID of the Customer");
							int accountId3 = Integer.parseInt(scan.nextLine());
							List<Account> accCheck;
							try {
								accCheck = accService.listBankAccById(accountId3);
								if(accCheck.size()!=0) {                                 //CHECK IF ACCOUNT HAS BANK ACCOUNTS 
									log.warn("Unable to delete Customer Accounts that still have Bank Accounts!"); 
									break;
								}
							} catch (BankException e) {
								log.error(e);
							}
		
							Customer deleteCustomer = new Customer();
							try {
								deleteCustomer=custService.getCustomerAccByid(accountId3);
							} catch (BankException e) {
								log.error(e);
							}
							if (deleteCustomer.getAccountid() != 0) {
								log.info("\nThe customer account is displayed below: ");
								log.info("==============================================================================================================================");
								log.info(deleteCustomer);
								log.info("==============================================================================================================================");
								log.info("\n=============================================");
								log.info("Are you sure you want to delete this account?"); //DELETION CONFIRMATION
								log.info("=============================================");
								log.info("1) Delete");
								log.info("2) Cancel");
								log.info("=============================================");
								int confirm = Integer.parseInt(scan.nextLine());
								if (confirm == 1) {
									try {
										custService.deleteCustomerAccByid(accountId3);
									} catch (BankException e) {
										log.error(e);
										;
									}
									log.info("The account was deleted successfully");
								} else if (confirm == 2) {
									log.info("Account deletion was canceled");
								} else {
									log.warn("Improper selection account deletion was canceled");
								}
								;
							}else {
								log.warn("An account with that account ID does not exist"); 
							}
							break;
							
						case 7://EXIT

							break;

						default:
							log.warn("Only choose between options 1-7");
							break;
						}
					} while (subOption2 != 7);

					break;
				case 2:
					int subOption3 = 0;
					do{
						log.info("\n===========================================");
						log.info("Welcome to the Customer BANK Account Menu"); //EMPLOYEE MANAGE BANK ACCOUNTS SUB MENU
						log.info("===========================================");
						log.info("1) Get Bank Accounts by Account Number");
						log.info("2) List Bank Accounts by ID");
						log.info("3) List All Bank Accounts");
						log.info("4) Manage Accounts by status");
						log.info("5) Delete a bank account");
						log.info("6) Return");
						log.info("===========================================");
						try {
							subOption3 = Integer.parseInt(scan.nextLine());
						} catch (NumberFormatException e) {}
						
						switch (subOption3) {
						case 1:                                                          //GET BANK ACC BY ACC NUMBER       
							log.info("\nPlease enter the account number of the account you are looking for"); 
							int accountNumber = Integer.parseInt(scan.nextLine());
							try {
								log.info(accService.getBankAccByNum(accountNumber));
							} catch (BankException e) {
								log.error(e);
							}
							break;
						case 2:                                                          //LIST BANK ACC BY ID
							log.info("\nPlease enter the account ID to list accounts with that ID");
							List<Account> accListid;
							int accountId = Integer.parseInt(scan.nextLine());
							try {
								log.info("\nListing all accounts:");
								log.info("==============================================================================================================================");
								accListid = accService.listBankAccById(accountId);
								for(int i=0;i<accListid.size();i++) {
									log.info(accListid.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}
							break;
						case 3:                                                          //LIST ALL ACCOUNTS
							List<Account> accList;
							try {
								log.info("\nListing all accounts:");
								log.info("==============================================================================================================================");
								accList = accService.listAllAccounts();
								for(int i=0;i<accList.size();i++) {
									log.info(accList.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}

							break;
						case 4:                                                           //MANAGE ACCOUNT BY STATUS
							List<Account> accList2;
							log.info("\n==============================================");
							log.info("Choose the account status you are looking for");
							log.info("==============================================");
							log.info("1) Open");
							log.info("2) Closed");
							log.info("3) Pending");
							log.info("==============================================");
							int choice = Integer.parseInt(scan.nextLine());
							String status = null;
							if(choice==1) {
								status = "Open";
							}else if(choice==2) {
								status = "Closed";
							}else if(choice==3) {
								status = "Pending";
							}else {
								log.warn("Please choose an appropriate number option from the choices");
							}
							if (status != null) {
								try {
									log.info("\nListing all accounts:");
									log.info("==============================================================================================================================");
									accList2 = accService.listAccountsByStatus(status);
									for (int i = 0; i < accList2.size(); i++) {
										log.info(accList2.get(i));
									}
									log.info("==============================================================================================================================");
								} catch (BankException e) {
									log.error(e);
								}
							}
							
							log.info("\nEnter the account number of the account you would like to modify"); //TO CLOSE OR APPROVE ACCOUNTS
							accountNumber = Integer.parseInt(scan.nextLine());
							log.info("\n==========================================================");
							log.info("Choose an action from the options below");
							log.info("==========================================================");
							log.info("1) Close the Account");
							log.info("2) Approve an Account and provide an Official Bank Number");
							log.info("3) Cancel");
							log.info("==========================================================");
							choice = Integer.parseInt(scan.nextLine());
							if (choice == 1) {
								try {
									accService.updateBankAccountStatus(accountNumber, "Closed");
									log.info("The account was closed successfully");
								} catch (BankException e) {
									log.error(e);
								}
							}else if(choice == 2) {
								log.info("Enter the official Account Number");
								int newAccountNumber = Integer.parseInt(scan.nextLine());
								try {
									accService.updateBankAccountNumber(accountNumber, newAccountNumber);
									accService.updateBankAccountStatus(newAccountNumber, "Open");
								} catch (BankException e) {
									log.error(e);
								}
								log.info("The account was approved and opened successfully");
							}else if(choice==3){
								break;
							}else {
								log.warn("Please choose only options 1-3");
							}
							
							break;
						case 5:                                                                  //TO DELETE AN ACCOUNT
							log.info("\nAccount Deletion:");
							log.info("Enter the Account Number of the account that will be DELETED");
							int accountNumDel = Integer.parseInt(scan.nextLine());
							
							int deletion;
							try {
								deletion = accService.deleteBankAccount(accountNumDel);
								if (deletion==1) {
									log.info("The account was deleted successfully");
								}else {
									log.warn("The account was not deleted");
									log.warn("Make sure the account number is correct and the account is CLOSED"); //MUST BE CLOSED
								}
							} catch (BankException e) {
								log.error(e);
							}
							
							break;
						case 6://EXIT

							break;

						default:
							log.warn("Only choose between options 1-6");
							break;
						}
					}while(subOption3!=6);

					break;
				case 3:
					int subOption4 = 0;
					do{
						log.info("\n==========================================");
						log.info("How will you manage transactions today?");   //EMPLOYEE TRANSACTIONS SUB MENU
						log.info("==========================================");
						log.info("1) List Transactions by Account Number");
						log.info("2) List Transactions by Account ID");
						log.info("3) List All Transactions");
						log.info("4) Return");
						log.info("==========================================");
						try {
							subOption4 = Integer.parseInt(scan.nextLine());
						} catch (NumberFormatException e) {}
						
						switch (subOption4) {
						case 1:                                                        //LIST TRANS BY ACC NUMBER
							List<Transaction> transList0;
							log.info("Enter the account NUMBER of an account to view its transactions");
							int accountNumber = Integer.parseInt(scan.nextLine());
							try {
								log.info("\nListing transactions from account with account number: "+accountNumber);
								log.info("==============================================================================================================================");
								transList0 = tranService.listTransactionByAccNum(accountNumber); 
								for (int i = 0; i < transList0.size(); i++) {
									log.info(transList0.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}
							break;
						case 2:                                                     //LIST TRANS BY CUSTOMER ACC ID
							List<Transaction> transList1;
							log.info("Enter the account ID of the customer to view transactions across all their accounts");
							int accountId = Integer.parseInt(scan.nextLine());
							try {
								log.info("\nListing all transactions from customer with account ID: "+accountId);
								log.info("==============================================================================================================================");
								transList1 = tranService.listTransactionById(accountId);
								for (int i = 0; i < transList1.size(); i++) {
									log.info(transList1.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}
							break;
						case 3:
							List<Transaction> transList2;                           //LIST ALL TRANSACTIONS 
							try {
								log.info("\nListing all transactions in the database");
								log.info("==============================================================================================================================");
								transList2 = tranService.listAllTransactions();
								for (int i = 0; i < transList2.size(); i++) {
									log.info(transList2.get(i));
								}
								log.info("==============================================================================================================================");
							} catch (BankException e) {
								log.error(e);
							}
							break;
						case 4: //EXIT

							break;

						default:
							log.warn("Only choose between options 1-4");
							break;
						}
					} while (subOption4 != 4);

					break;
				case 4:
					log.info("Thank you for being HEROIC today!");

					break;

				default:
					log.warn("Please only enter a number 1-4");
					break;
				}
				}while(subOption!=4);
				
				break;
			case 3:
				log.info("Thank you for banking with Marvel Bank! \"A Super Bank for Super Heroes!\"");

				break;

			default:
				log.warn("Please only enter a number 1-3");
				break;
			}

		} while (outerOption!= 3);

	}

}
