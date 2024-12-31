package tn.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.com.entities.AccountOperation;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

}
