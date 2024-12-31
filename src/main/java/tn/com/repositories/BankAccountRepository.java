package tn.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.com.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
