package com.bankapp.main;


import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankapp.account.model.Account;
import com.bankapp.account.model.Customer;
import com.bankapp.account.service.AccountService;
import com.bankapp.account.service.CustomerService;
import com.bankapp.account.service.impl.AccountServiceImpl;
import com.bankapp.account.service.impl.CustomerServiceImpl;
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
		CustomerService custService = new CustomerServiceImpl();
		AccountService accService = new AccountServiceImpl();
		TransactionService tranService = new TransactionServiceImpl();
		
		do {
			log.info("\nWelcome to Marvel Bank!");             //MAIN MENU
			log.info("-------------------------");
			log.info("1) Customer Login");
			log.info("2) Employee Login");
			log.info("3) Exit");
			try {
				outerOption=Integer.parseInt(scan.nextLine());
			}catch(NumberFormatException e) {}
			
			switch (outerOption) {
			case 1:
				int option=0;
				
				do{
				log.info("\nWelcome to Customer Login");                        //CUSTOMER LOGIN SUB MENU
				log.info("Please choose one of the following options");
				log.info("1) Login");
				log.info("2) Create an Account");
				log.info("3) Exit");
				try {
					option=Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {}
				
				switch (option) {
				case 1:
					int check = 0;
					log.info("\nPlease enter your user email address");
					String email=scan.nextLine();
					log.info("Please enter your password");
					String password=scan.nextLine();
					try {
						while(check == 0) {                                      //CUSTOMER LOGIN 
							
							if (custService.custLogin(email, password)==false) {
								log.info("The email and password did not match please try again");
							}else {
								check=1;
							}
						}
					} catch (LoginException e) {
						log.info(e);
					}
	                 
					int accountId = accService.getIdByEmailPassword(email, password);
					                                                             
					int subOption=0;
					
					do{
					log.info("\nWelcome back HERO");                         //MAIN CUSTOMER MENU
					log.info("What would you like to do today?");
					log.info("1) View My Bank Accounts");
					log.info("2) Open a New Bank Account");
					log.info("3) Log Out");
					try {
						subOption=Integer.parseInt(scan.nextLine());
					}catch(NumberFormatException e) {}
					
					switch (subOption) {
					case 1:
						log.info("\nDisplaying your accounts:");
						List<Account> accList;
						try {
							accList = accService.listBankAccById(accountId);
							for(int i=0;i<accList.size();i++) {
								System.out.println(accList.get(i));
							}
						} catch (BankException e) {
							log.warn(e);
						}
						log.info("\nEnter the account number of the account you would like to work with today");  //MAKE THIS SO YOU CANT WORK WITH PENDING(VALIDATIONS)
						int accountNumber = Integer.parseInt(scan.nextLine());
						
						int subOption2=0;
						do{
						log.info("Displaying account details :");                        //MY BANK ACCOUNT SUB MENU
						try {
							log.info(accService.getBankAccByNum(accountNumber));
						} catch (BankException e) {
							log.warn(e);
						}
						log.info("\nWhat would you like to do with this account?");
						log.info("1) Withdrawal");
						log.info("2) Deposit");
						log.info("3) Transfer");
						log.info("4) Transaction History");
						log.info("5) Return");
						
						try {
							subOption2=Integer.parseInt(scan.nextLine());
						}catch(NumberFormatException e) {}
							
						switch (subOption2) {
						case 1:
							Transaction transactionW = new Transaction();
							log.info("\nEnter the amount you would like to withdraw");
							int amountW = Integer.parseInt(scan.nextLine());
							transactionW.setAccountnumber(accountNumber);
							transactionW.setAmount(amountW);
							try {
								tranService.performWithdrawal(transactionW);
								log.info("Withdrawal Successful");
							} catch (BankException e) {
								log.warn(e);;
							}
						

							break;
						case 2:
							Transaction transactionD = new Transaction();
							log.info("\nEnter the amount you would like to deposit");
							int amountD = Integer.parseInt(scan.nextLine());
							transactionD.setAccountnumber(accountNumber);
							transactionD.setAmount(amountD);
							try {
								tranService.performDeposit(transactionD);
								log.info("Deposit Successful");
							} catch (BankException e) {
								log.warn(e);;
							}
							

							break;
						case 3:
							Transaction transactionT = new Transaction();
							log.info("\nEnter the amount you would like to transfer");
							int amountT = Integer.parseInt(scan.nextLine());
							log.info("Enter the account number of the account you are transfering to");
							int accountNumber2 = Integer.parseInt(scan.nextLine());
							transactionT.setAccountnumber(accountNumber);
							transactionT.setAmount(amountT);
							try {
								tranService.performTransfer(transactionT, accountNumber2);
								log.info("Transfer Successful");
							} catch (BankException e) {
								log.warn(e);;
							}
							

							break;
						case 4:
							log.info("\nTransaction History");;
							List<Transaction> transList0;
							try {
								log.info("Displaying the transaction History of the current account:");
								transList0 = tranService.listTransactionByAccNum(accountNumber); 
								for (int i = 0; i < transList0.size(); i++) {
									log.info(transList0.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}

							break; 
						case 5: //EXIT

							break;

						default:
							log.info("Please enter only a number between 1-5");
							break;
						}
						}while(subOption2!=5);
						
						break;
					case 2:
						log.info("\nOpening a NEW BANK account:");
						Account account = new Account();
						log.info("Please choose the account type you would like to open :");
						log.info("1) Checkings");
						log.info("2) Savings");
						int choice=Integer.parseInt(scan.nextLine());
						if(choice==1) {
							account.setAccountType("Checkings");
							try {
								accService.openNewBankAcc(account, accountId);
							} catch (BankException e) {
								log.info(e);
							}
							log.info("Checkings account created successfully, please allow 2-3 business days for approval");
						}else if(choice==2){
							account.setAccountType("Savings");
							try {
								accService.openNewBankAcc(account, accountId);
							} catch (BankException e) {
								log.info(e);
							}
							log.info("Savings account created successfully, please allow 2-3 business days for approval");
						}else {
							log.info("Please only choose from the options provided");
						}

						break;
					case 3:
						log.info("\nThank you for banking with Marvel Bank! Continue being HEROIC!");

					default:
						break;
					}
					
					}while(subOption!= 3);
					
					break;
				case 2:
					log.info("\nCreating a new CUSTOMER ACCOUNT");  //CREATE NEW ACCOUNT
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
							log.info(customer);
							log.info("Thank you for creating an account with us!");
							log.info("Login through the customer login menu and follow the steps to open your first account!");
						}
					} catch (LoginException e) {
					log.warn(e);
					}
					break;
				case 3:
					log.info("Exiting to main menu");
					
					break;

				default:
					log.info("Please enter a number 1-3 ONLY");
					break;
				}
				}while(option!=3);

				break;
			case 2:
				log.info("\nWelcome to Employee Login");                         //EMPLOYEE LOGIN
				
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
				log.info("\nWelcome back! How will you be a HERO today?"); //EMPLOYEE MAIN MENU
				log.info("1) Manage Customer Accounts");
				log.info("2) Manage Bank Accounts");
				log.info("3) Manage Transactions");
				log.info("4) Log Out");
				try {
					subOption=Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e) {}
					 
				switch (subOption) {
				case 1:
					int subOption2 = 0;
					do{
						log.info("\nWelcome to the Customer Account Menu"); //MANAGE CUSTOMER ACCOUNTS SUB MENU
						log.info("1) Find Customer by ID");
						log.info("2) Find Customer by Lastname");
						log.info("3) List All Customer Accounts");
						log.info("4) Update Customer Account(by ID)");
						log.info("5) Create NEW Customer Account");
						log.info("6) Delete Customer Account");
						log.info("7) Return");
						try {
							subOption2=Integer.parseInt(scan.nextLine());
						}catch(NumberFormatException e) {}
					
						switch (subOption2) {
						case 1:
							Customer findCustomer = new Customer(); 
							log.info("Please enter the account ID of the customer you are looking for");
							int accountId=Integer.parseInt(scan.nextLine());
							
							try {
								findCustomer=custService.getCustomerAccByid(accountId);
							} catch (BankException e) {
								log.warn(e);
							}
							
							log.info("The account matching that ID is displayed below: ");
							log.info(findCustomer);
							
							
							break;
						case 2:
							log.info("\nEnter the last name of the customer you are looking for");  ///SORT THIS
							List<Customer> custList;
							String lastName = scan.nextLine();
							try {
								log.info("Listing all accounts with the last name "+lastName);
								custList = custService.listCustByLastName(lastName);
								for(int i=0;i<custList.size();i++) {
									System.out.println(custList.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}
							
							
							break;
						case 3:
							List<Customer> custList2;
							try {
								log.info("\nListing all customer accounts:");
								custList2 = custService.listAllCustAccounts();
								for(int i=0;i<custList2.size();i++) {
									log.info(custList2.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}

							break;
						case 4:
							Customer getCustomer = new Customer();
							log.info("Please provide the account ID of the customer account your would like to update");
							int accountId2=Integer.parseInt(scan.nextLine());
							
							try {
								getCustomer=custService.getCustomerAccByid(accountId2);
							} catch (BankException e) {
								log.warn(e);
							}
							
							log.info("\nThe account matching that ID is displayed below: ");
							log.info(getCustomer);
							log.info("What would you like to update");
							log.info("1) Firstname");
							log.info("2) Lastname");
							log.info("3) Address");
							log.info("4) Number");
							log.info("5) Email");
							log.info("6) Password");
							int choice=Integer.parseInt(scan.nextLine());
							
							log.info("please enter the updated information");
							String update = scan.nextLine();
							
							try {
								custService.updateCustomerAccByid(accountId2, choice, update);
							} catch (BankException e) {
								log.warn(e);
							}
							
							try {
								getCustomer=custService.getCustomerAccByid(accountId2);
							} catch (BankException e) {
								log.warn(e);
							}
							log.info("The updated customer account is displayed below: ");
							log.info(getCustomer);
							
							

							break;
						case 5:
							log.info("\nCreating a NEW customer account");                               //UNTESTED
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
									log.info("Player registered successfully with below details: ");
									log.info(customer);
								}
							} catch (LoginException e) {
								System.out.println(e.getMessage());
							}

							break;
						case 6:
							Customer deleteCustomer = new Customer();
							log.info("\nCustomer Account Deletion");
							log.info("Enter the account ID of the Customer");
							int accountId3 = Integer.parseInt(scan.nextLine());
							try {
								deleteCustomer=custService.getCustomerAccByid(accountId3);
							} catch (BankException e) {
								log.warn(e);
							}
							if (deleteCustomer.getAccountid() != 0) {
								log.info("The customer account is displayed below: ");
								log.info(deleteCustomer);
								log.info("Are you sure you want to delete this account?"); //////ADD SOME TYPE OF LIST TO CHECK IF CUST HAS ACCOUNTS
								log.info("1) Delete");
								log.info("2) Cancel");
								int confirm = Integer.parseInt(scan.nextLine());
								if (confirm == 1) {
									try {
										custService.deleteCustomerAccByid(accountId3);
									} catch (BankException e) {
										log.warn(e);
										;
									}
									log.warn("The account was deleted successfully");
								} else if (confirm == 2) {
									log.warn("Account deletion was canceled");
								} else {
									log.warn("Improper selection account deletion was canceled");
								}
								;
							}else {
								log.info("An account with that account ID does not exist"); //untested
							}
							break;
							//ADD FEATURE TO CHECK IF ACCOUNT HAS OPENED BANK ACCOUNTS
							
						case 7:// this is exit

							break;

						default:
							log.info("Only choose between options 1-7");
							break;
						}
					} while (subOption2 != 7);

					break;
				case 2:
					int subOption3 = 0;
					do{
						log.info("\nWelcome to the Customer BANK Account Menu?"); //MANAGE BANK ACCOUNTS SUB MENU
						log.info("1) Get Bank Accounts by Account Number");
						log.info("2) List Bank Accounts by ID");
						log.info("3) List All Bank Accounts");
						log.info("4) Manage Accounts by status");
						log.info("5) Delete a bank account");
						log.info("6) Return");
						try {
							subOption3 = Integer.parseInt(scan.nextLine());
						} catch (NumberFormatException e) {}
						
						switch (subOption3) {
						case 1:
							log.info("\nPlease enter the account number of the account you are looking for");
							int accountNumber = Integer.parseInt(scan.nextLine());
							try {
								log.info(accService.getBankAccByNum(accountNumber));
							} catch (BankException e) {
								log.warn(e);
							}
							break;
						case 2:
							log.info("\nPlease enter the account ID to list accounts with that ID");
							List<Account> accListid;
							int accountId = Integer.parseInt(scan.nextLine());
							try {
								accListid = accService.listBankAccById(accountId);
								for(int i=0;i<accListid.size();i++) {
									log.info(accListid.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}
							break;
						case 3:
							List<Account> accList;
							try {
								log.info("\nListing all accounts:");
								accList = accService.listAllAccounts();
								for(int i=0;i<accList.size();i++) {
									log.info(accList.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}

							break;
						case 4:
							List<Account> accList2;
							log.info("\nChoose the account status you are looking for");
							log.info("1) Open");
							log.info("2) Closed");
							log.info("3) Pending");
							int choice = Integer.parseInt(scan.nextLine());
							String status = null;
							if(choice==1) {
								status = "Open";
							}else if(choice==2) {
								status = "Closed";
							}else if(choice==3) {
								status = "Pending";
							}else {
								log.info("Please choose an appropriate number option from the choices");
							}
							if (status != null) {
								try {
									log.info("\nListing all accounts:");
									accList2 = accService.listAccountsByStatus(status);
									for (int i = 0; i < accList2.size(); i++) {
										log.info(accList2.get(i));
									}
								} catch (BankException e) {
									log.warn(e);
								}
							}
							
							log.info("\nEnter the account number of the account you would like to modify");
							accountNumber = Integer.parseInt(scan.nextLine());
							log.info("\nChoose an action below");
							log.info("1) Close the Account");
							log.info("2) Approve an Account and provide an Official Bank Number");
							choice = Integer.parseInt(scan.nextLine());
							if (choice == 1) {
								try {
									accService.updateBankAccountStatus(accountNumber, "Closed");
								} catch (BankException e) {
									log.warn(e);
								}
								log.info("The account was closed successfully");
							}else if(choice == 2) {
								log.info("Enter the official Account Number");
								int newAccountNumber = Integer.parseInt(scan.nextLine());
								try {
									accService.updateBankAccountStatus(accountNumber, "Open");
									accService.updateBankAccountNumber(accountNumber, newAccountNumber);
								} catch (BankException e) {
									log.warn(e);
								}
								log.info("The account was approved and opened successfully");
							}else {
								log.info("Please choose only option 1 or option 2");
							}
							
							break;
						case 5:
							log.info("\nAccount Deletion:");
							log.info("Enter the account ID of the account that will be DELETED");
							int accountIdDel = Integer.parseInt(scan.nextLine());
							
							int deletion;
							try {
								deletion = accService.deleteBankAccount(accountIdDel);
								if (deletion==1) {
									log.info("The account was deleted successfully");
								}else {
									log.info("The account was not deleted");
									log.info("Make sure the account number is correct and the account is CLOSED");
								}
							} catch (BankException e) {
								log.info(e);
							}
							
							break;
						case 6://exit

							break;

						default:
							log.info("Only choose between options 1-6");
							break;
						}
					}while(subOption3!=6);

					break;
				case 3:
					int subOption4 = 0;
					do{
						log.info("\nHow will you manage transactions today?"); //SUB MENU TRANSACTIONS SUB MENU
						log.info("1) List Transactions by Account Number");
						log.info("2) List Transactions by Account ID");
						log.info("3) List All Transactions");
						log.info("4) Return");
						try {
							subOption4 = Integer.parseInt(scan.nextLine());
						} catch (NumberFormatException e) {}
						
						switch (subOption4) {
						case 1:
							List<Transaction> transList0;
							log.info("Enter the account NUMBER of an account to view its transactions");
							int accountNumber = Integer.parseInt(scan.nextLine());
							try {
								log.info("\nListing transactions from account with account number: "+accountNumber);
								transList0 = tranService.listTransactionByAccNum(accountNumber); 
								for (int i = 0; i < transList0.size(); i++) {
									log.info(transList0.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}
							break;
						case 2:
							List<Transaction> transList1;
							log.info("Enter the account ID of the customer to view transactions across all their accounts");
							int accountId = Integer.parseInt(scan.nextLine());
							try {
								log.info("\nListing all transactions from customer with account ID: "+accountId);
								transList1 = tranService.listTransactionById(accountId);
								for (int i = 0; i < transList1.size(); i++) {
									log.info(transList1.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}
							break;
						case 3:
							List<Transaction> transList2;
							try {
								log.info("\nListing all transactions in the database");
								transList2 = tranService.listAllTransactions();
								for (int i = 0; i < transList2.size(); i++) {
									log.info(transList2.get(i));
								}
							} catch (BankException e) {
								log.warn(e);
							}
							break;
						case 4: //EXIT

							break;

						default:
							log.info("Only choose between options 1-4");
							break;
						}
					} while (subOption4 != 4);

					break;
				case 4:
					log.info("Thank you for being HEROIC today!");

					break;

				default:
					log.info("Please only enter a number 1-4");
					break;
				}
				}while(subOption!=4);
				
				break;
			case 3:
				log.info("Thank you for banking with Marvel Bank! \"A Super Bank for Super Heroes!\"");

				break;

			default:
				log.info("Please only enter a number 1-3");
				break;
			}

		} while (outerOption!= 3);

	}

}