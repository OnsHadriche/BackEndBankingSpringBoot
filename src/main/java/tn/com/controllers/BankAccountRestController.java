package tn.com.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.com.dto.AccountHistoryDTO;
import tn.com.dto.AccountOperationDTO;
import tn.com.dto.BankAccountDTO;
import tn.com.exception.BankAccountNotFoundException;
import tn.com.services.BankAccountService;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
	private BankAccountService bankAccountService;

	@GetMapping("/accounts/{accountId}")
	public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {

		return bankAccountService.getAccount(accountId);
	}

	@GetMapping("/accounts")
	public List<BankAccountDTO> listAccounts() {
		return bankAccountService.listBankAccount();
	}
	
	@GetMapping("/accounts/{accountId}/operations")
	public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
		return bankAccountService.accountHistory(accountId);
	}


	@GetMapping("/accounts/{accountId}/pageOperations")
	public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
		return bankAccountService.getAccountHistory(accountId, page, size);
	}
/*
s	@PostMapping("/accounts/debit")
	public DebitDTO debit(@RequestBody DebitDTO debitDTO)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
		return debitDTO;
	}

	@PostMapping("/accounts/credit")
	public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
		this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
		return creditDTO;
	}

	@PostMapping("/accounts/transfer")
	public void transfer(@RequestBody TransferRequestDTO transferRequestDTO)
			throws BankAccountNotFoundException, BalanceNotSufficientException {
		this.bankAccountService.transfer(transferRequestDTO.getAccountSource(),
				transferRequestDTO.getAccountDestination(), transferRequestDTO.getAmount());
	}*/

}
