package tn.com.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.com.entities.BankAccount;
import tn.com.entities.CurrentAccount;
import tn.com.entities.Customer;
import tn.com.entities.SavingAccount;
import tn.com.exception.CustomerNotFoundException;
import tn.com.exception.RunTimeException;
import tn.com.repositories.AccountOperationRepository;
import tn.com.repositories.BankAccountRepository;
import tn.com.repositories.CustomerRepository;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImp implements BankAccountService {
	
	private CustomerRepository customerRepo;
	private BankAccountRepository bankRepo;
	private AccountOperationRepository accountOpRepo;
	//la journalisation  
	//Logger log =LoggerFactory.getLogger((this.getClass().getName()));


	@Override
	public Customer saveCustomer(Customer customer) {
		log.info("Saving new customer");
		Customer savedCustomer=customerRepo.save(customer);
		return savedCustomer;
	}

	@Override
	public BankAccount saveBankAccount(double initialBalane, String type, Long customerId) throws CustomerNotFoundException  {
		//generer une exception 
		Customer customer = customerRepo.findById(customerId).orElseThrow(null);
		if(customer == null) {
			throw new CustomerNotFoundException("customer not found");
		}
		
		BankAccount bankAccount;
		if(type.equals("current")) {
			bankAccount= new CurrentAccount();
		}else {
			bankAccount=new SavingAccount();
		}
		bankAccount.setId(UUID.randomUUID().toString());
		bankAccount.setCreated_date(new Date());
		bankAccount.setBalance(initialBalane);
		
		bankAccount.setCustomer(null);
		return null;
	}

	@Override
	public List<Customer> listCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankAccount getAccount(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void debit(String accountId, double amount, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void credit(String accountId, double amount, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amountOp) {
		// TODO Auto-generated method stub
		
	}

}
