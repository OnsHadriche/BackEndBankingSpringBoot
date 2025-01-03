package tn.com.services;

import java.util.List;

import tn.com.dto.AccountHistoryDTO;
import tn.com.dto.AccountOperationDTO;
import tn.com.dto.BankAccountDTO;
import tn.com.dto.CustomerDTO;
import tn.com.entities.BankAccount;
import tn.com.entities.CurrentAccount;
import tn.com.entities.SavingAccount;
import tn.com.exception.BalanceNotSufficentException;
import tn.com.exception.BankAccountNotFoundException;
import tn.com.exception.CustomerNotFoundException;

public interface BankAccountService {

	CustomerDTO saveCustomer(CustomerDTO customer);

	CurrentAccount saveCurrentBankAccount(double initialBalane,double overDraft, Long customerId) throws CustomerNotFoundException;
	SavingAccount saveSavingBankAccount(double initialBalane,double interestRate, Long customerId) throws CustomerNotFoundException;

	List<CustomerDTO> listCustomer();
	List<BankAccountDTO> listBankAccount();

	BankAccountDTO getAccount(String accountId) throws BankAccountNotFoundException;

	void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;

	void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

	void transfer(String accountIdSource, String accountIdDestination, double amountOp) throws BankAccountNotFoundException, BalanceNotSufficentException;

	CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;


    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

	List<AccountOperationDTO> accountHistory(String accountId);

	AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
	

}
