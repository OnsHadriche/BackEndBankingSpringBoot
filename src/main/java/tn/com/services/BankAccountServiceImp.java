package tn.com.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import enums.OperationType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.com.dto.CustomerDTO;
import tn.com.entities.AccountOperation;
import tn.com.entities.BankAccount;
import tn.com.entities.CurrentAccount;
import tn.com.entities.Customer;
import tn.com.entities.SavingAccount;
import tn.com.exception.BalanceNotSufficentException;
import tn.com.exception.BankAccountNotFoundException;
import tn.com.exception.CustomerNotFoundException;
import tn.com.mappers.BankAccountMapperImpl;
import tn.com.repositories.AccountOperationRepository;
import tn.com.repositories.BankAccountRepository;
import tn.com.repositories.CustomerRepository;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImp implements BankAccountService {

	private final CustomerRepository customerRepo;
	private final BankAccountRepository bankRepo;
	private final AccountOperationRepository accountOpRepo;
	private final BankAccountMapperImpl dtoMapper;
	@Override
	public Customer saveCustomer(Customer customer) {
		log.info("Saving new customer");
		return customerRepo.save(customer);
	}

	@Override
	public List<CustomerDTO> listCustomer() {
		log.info("Fetching all customers");
		List<Customer> customers = customerRepo.findAll();
		List<CustomerDTO> customerDTOs = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
			customerDTOs.add(customerDTO);
		}

		
		return customerDTOs ;
	}

	@Override
	public BankAccount getAccount(String accountId) throws BankAccountNotFoundException {
		log.info("Fetching bank account with ID {}", accountId);
		return bankRepo.findById(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
	}

	@Override
	public void debit(String accountId, double amount, String description)
			throws BankAccountNotFoundException, BalanceNotSufficentException {
		log.info("Debiting account with ID {}", accountId);
		BankAccount account = getAccount(accountId);
		if (account.getBalance() < amount) {
			throw new BalanceNotSufficentException("Balance not sufficient");
		}
		// Ajoute operation
		AccountOperation accountOp = new AccountOperation();
		accountOp.setType(OperationType.DEBIT);
		accountOp.setAmount(amount);
		accountOp.setDescription(description);
		accountOp.setOperationDate(new Date());
		accountOp.setBankAccount(account);
		accountOpRepo.save(accountOp);
		// update account
		account.setBalance(account.getBalance() - amount);
		bankRepo.save(account);
	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		log.info("Crediting account with ID {}", accountId);
		BankAccount account = getAccount(accountId);

		// Ajoute operation
		AccountOperation accountOp = new AccountOperation();
		accountOp.setType(OperationType.CREDIT);
		accountOp.setAmount(amount);
		accountOp.setDescription(description);
		accountOp.setOperationDate(new Date());
		accountOp.setBankAccount(account);
		accountOpRepo.save(accountOp);
		// update account
		account.setBalance(account.getBalance() + amount);
		bankRepo.save(account);
	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amountOp)
			throws BankAccountNotFoundException, BalanceNotSufficentException {
		log.info("Transferring {} from {} to {}", amountOp, accountIdSource, accountIdDestination);
		debit(accountIdSource, amountOp, "Transfer to " + accountIdDestination);
		credit(accountIdDestination, amountOp, "Transfer from " + accountIdSource);
	}

//Creation des comptes bancaires
	@Override
	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		CurrentAccount currentBankAccount = new CurrentAccount();
		currentBankAccount.setId(UUID.randomUUID().toString());
		currentBankAccount.setCreated_date(new Date());
		currentBankAccount.setBalance(initialBalance);
		currentBankAccount.setCustomer(customer);
		currentBankAccount.setOverDraft(overDraft);
		return bankRepo.save(currentBankAccount);
	}

	@Override
	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		SavingAccount savingAccount = new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setCreated_date(new Date());
		savingAccount.setBalance(initialBalance);
		savingAccount.setCustomer(customer);
		savingAccount.setInteresRate(interestRate);
		return bankRepo.save(savingAccount);
	}

	@Override
	public List<BankAccount> listBankAccount() {
		log.info("Fetching all accounts");
		return bankRepo.findAll();
	}

	public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
		Customer customer;
		try {
			customer = customerRepo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
		}
		return dtoMapper.fromCustomer(customer);
	}
}
