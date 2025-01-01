package tn.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.com.entities.AccountOperation;
@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

}
