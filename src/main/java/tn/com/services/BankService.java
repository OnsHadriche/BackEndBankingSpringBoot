package tn.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tn.com.entities.BankAccount;
import tn.com.entities.CurrentAccount;
import tn.com.entities.SavingAccount;
import tn.com.repositories.BankAccountRepository;

@Service
@Transactional
public class BankService {
	@Autowired
	private BankAccountRepository bankAccountRepo;
	public void consulter() {
		BankAccount bankAccount = bankAccountRepo.findById("").orElse(null);
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
		bankAccount.getBanckAccountoperations().forEach(op->{
			System.out.println("operation type: "+op.getType());
			
		});
	}

}
