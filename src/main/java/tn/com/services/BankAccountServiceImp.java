package tn.com.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import enums.OperationType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.com.dto.BankAccountDTO;
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
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		log.info("Saving new customer");
		Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
		Customer savedCustomer = customerRepo.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}

	@Override
	public List<CustomerDTO> listCustomer() {
		log.info("Fetching all customers");
		List<Customer> customers = customerRepo.findAll();
		/*
		 * List<CustomerDTO> customerDTOs = new ArrayList<>(); for (Customer customer :
		 * customers) { CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
		 * customerDTOs.add(customerDTO); }
		 * 
		 */
		List<CustomerDTO> customerDTOs = customers.stream().map(cust -> dtoMapper.fromCustomer(cust))
				.collect(Collectors.toList());
		return customerDTOs;
	}

	@Override
	public BankAccountDTO getAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankRepo.findById(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
		if (bankAccount instanceof SavingAccount) {
			SavingAccount savingAccount = (SavingAccount) bankAccount;
			return dtoMapper.fromSavingBankAccount(savingAccount);
		} else {
			CurrentAccount currentAccount = (CurrentAccount) bankAccount;
			return dtoMapper.fromCurrentBankAccount(currentAccount);
		}
	}

	@Override
	  public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount=bankRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
       accountOpRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankRepo.save(bankAccount);
    }

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		   BankAccount bankAccount=bankRepo.findById(accountId)
	                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
	        AccountOperation accountOperation=new AccountOperation();
	        accountOperation.setType(OperationType.CREDIT);
	        accountOperation.setAmount(amount);
	        accountOperation.setDescription(description);
	        accountOperation.setOperationDate(new Date());
	        accountOperation.setBankAccount(bankAccount);
	        accountOpRepo.save(accountOperation);
	        bankAccount.setBalance(bankAccount.getBalance()+amount);
	        bankRepo.save(bankAccount);
	}

	@Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
        debit(accountIdSource,amount,"Transfer to "+ accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
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
	public List<BankAccountDTO> listBankAccount() {
		log.info("Fetching all accounts");
		List<BankAccount> bankAccounts = bankRepo.findAll();
		List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
			if (bankAccount instanceof SavingAccount) {
				SavingAccount savingAccount = (SavingAccount) bankAccount;
				return dtoMapper.fromSavingBankAccount(savingAccount);
			} else {
				CurrentAccount currentAccount = (CurrentAccount) bankAccount;
				return dtoMapper.fromCurrentBankAccount(currentAccount);
			}
		}).collect(Collectors.toList());
		return bankAccountDTOS;
	}

	@Override
	public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
		Customer customer;

		customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);

		return customerDTO;
	}

	@Override
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
		log.info("Saving new Customer");
		Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
		Customer savedCustomer = customerRepo.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		customerRepo.deleteById(customerId);
	}

}
