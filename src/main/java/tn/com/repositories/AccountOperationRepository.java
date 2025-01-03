package tn.com.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.com.entities.AccountOperation;
@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
	public List<AccountOperation> findByBankAccountId(String accountId);

	public Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, PageRequest of);
}
