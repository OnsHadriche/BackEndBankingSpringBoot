package tn.com.demo;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import tn.com.dto.BankAccountDTO;
import tn.com.dto.CurrentBankAccountDTO;
import tn.com.dto.CustomerDTO;
import tn.com.dto.SavingBankAccountDTO;
import tn.com.exception.BalanceNotSufficentException;
import tn.com.exception.BankAccountNotFoundException;
import tn.com.exception.CustomerNotFoundException;
import tn.com.services.BankAccountService;

@SpringBootApplication(scanBasePackages = { "tn.com.services", "tn.com.repositories", "tn.com.entities","tn.com.controllers","tn.com.mappers" })
@EnableJpaRepositories(basePackages = { "tn.com.repositories", "tn.com.services" })
@EntityScan("tn.com.entities")
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {

			Stream.of("Ons", "Khalil", "Emna").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer); // Save customer to the repository
			});
			bankAccountService.listCustomer().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 100000000, 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 1000, 2.2, customer.getId());
					List<BankAccountDTO> bankAccounts = bankAccountService.listBankAccount();
					for (BankAccountDTO bankAccount : bankAccounts) {
						for (int i = 0; i < 5; i++) {

							  String accountId;
			                    if(bankAccount instanceof SavingBankAccountDTO){
			                        accountId=((SavingBankAccountDTO) bankAccount).getId();
			                    } else{
			                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
			                    }
			                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
			                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
						}
					}

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				} catch (BankAccountNotFoundException  e) {
					e.printStackTrace();
				}catch (BalanceNotSufficentException  e) {
					e.printStackTrace();
				}
			});
		;

		};
	}

	/*
	 * CommandLineRunner start(CustomerRepository customRep, BankAccountRepository
	 * bankAccountRepo, AccountOperationRepository accountOpRepo) { return args -> {
	 * Stream.of("Ons", "Khalil", "Emna").forEach(name -> { Customer customer = new
	 * Customer(); customer.setName(name); customer.setEmail(name + "@gmail.com");
	 * customRep.save(customer); // Save customer to the repository });
	 * customRep.findAll().forEach(customer -> { CurrentAccount currentAccount = new
	 * CurrentAccount(); currentAccount.setId(UUID.randomUUID().toString());
	 * currentAccount.setBalance(Math.random() * 9000);
	 * currentAccount.setCreated_date(new Date());
	 * currentAccount.setStatus(AccountStatus.CREATED);
	 * currentAccount.setCustomer(customer); currentAccount.setOverDraft(9000);
	 * bankAccountRepo.save(currentAccount);
	 * 
	 * }); bankAccountRepo.findAll().forEach(acc -> { for (int i = 0; i < 5; i++) {
	 * AccountOperation accountOp = new AccountOperation();
	 * accountOp.setOperationDate(new Date()); accountOp.setAmount(Math.random() *
	 * 12000); accountOp.setType(Math.random() > 0.5 ? OperationType.DEBIT :
	 * OperationType.CREDIT); accountOp.setBankAccount(acc);
	 * accountOpRepo.save(accountOp);
	 * 
	 * }
	 * 
	 * }); BankAccount bankAccount = bankAccountRepo.findById("jj").orElse(null);
	 * System.out.println("**************************");
	 * System.out.println(bankAccount.getId());
	 * System.out.println(bankAccount.getBalance());
	 * System.out.println(bankAccount.getCustomer().getName());
	 * System.out.println(bankAccount.getCreated_date()); if(bankAccount instanceof
	 * CurrentAccount) { System.out.println(((CurrentAccount)
	 * bankAccount).getOverDraft()); }else { System.out.println(((SavingAccount)
	 * bankAccount).getInteresRate()); }
	 * 
	 * 
	 * }; }
	 */
}
