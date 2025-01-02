package tn.com.services;

import java.util.List;

import tn.com.entities.BankAccount;
import tn.com.entities.Customer;

public interface BankAccountService {

	Customer saveCustomer(Customer customer);

	BankAccount saveBankAccount(double initialBalane, String type, Long customerId);

	List<Customer> listCustomer();

	BankAccount getAccount(String accountId);

	void debit(String accountId, double amount, String description);

	void credit(String accountId, double amount, String description);

	void transfer(String accountIdSource, String accountIdDestination, double amountOp);

}
