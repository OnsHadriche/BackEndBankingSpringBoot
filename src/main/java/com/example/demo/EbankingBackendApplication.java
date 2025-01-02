package com.example.demo;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import enums.AccountStatus;
import enums.OperationType;
import tn.com.entities.AccountOperation;
import tn.com.entities.BankAccount;
import tn.com.entities.CurrentAccount;
import tn.com.entities.Customer;
import tn.com.entities.SavingAccount;
import tn.com.repositories.AccountOperationRepository;
import tn.com.repositories.BankAccountRepository;
import tn.com.repositories.CustomerRepository;
import tn.com.services.BankService;

@SpringBootApplication(scanBasePackages = {"tn.com.services", "tn.com.repositories", "tn.com.entities"})
@EnableJpaRepositories(basePackages = {"tn.com.repositories","tn.com.services"})
@EntityScan("tn.com.entities")
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BankService bankService) {
		return args -> {
			bankService.consulter();
			
			
		};
	}

	//@Bean
	CommandLineRunner start(CustomerRepository customRep, BankAccountRepository bankAccountRepo,
			AccountOperationRepository accountOpRepo) {
		return args -> {
			Stream.of("Ons", "Khalil", "Emna").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customRep.save(customer); // Save customer to the repository
			});
			customRep.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId((long) (Math.random() * 100));
				currentAccount.setBalance(Math.random() * 9000);
				currentAccount.setCreated_date(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(9000);
				bankAccountRepo.save(currentAccount);

			});
			bankAccountRepo.findAll().forEach(acc -> {
				for (int i = 0; i < 5; i++) {
					AccountOperation accountOp = new AccountOperation();
					accountOp.setOperationDate(new Date());
					accountOp.setAmount(Math.random() * 12000);
					accountOp.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOp.setBankAccount(acc);
					accountOpRepo.save(accountOp);

				}
				
			});
			BankAccount bankAccount = bankAccountRepo.findById((long) 3).orElse(null);
			System.out.println("**************************");
			System.out.println(bankAccount.getId());
			System.out.println(bankAccount.getBalance());
			System.out.println(bankAccount.getCustomer().getName());
			System.out.println(bankAccount.getCreated_date());
			if(bankAccount instanceof CurrentAccount) {
				System.out.println(((CurrentAccount) bankAccount).getOverDraft());
			}else {
				System.out.println(((SavingAccount) bankAccount).getInteresRate());
			}
			
			
		};
	}
}
