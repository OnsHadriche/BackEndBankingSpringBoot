package tn.com.dto;

import java.util.Date;

import enums.AccountStatus;
import lombok.Data;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {

	   private String id;
	    private double balance;
	    private Date createdAt;
	    private AccountStatus status;
	    private CustomerDTO customerDTO;
	    private double overDraft;
	}