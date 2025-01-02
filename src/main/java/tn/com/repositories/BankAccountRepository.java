package tn.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.com.entities.BankAccount;
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
