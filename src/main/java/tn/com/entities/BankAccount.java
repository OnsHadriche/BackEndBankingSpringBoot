package tn.com.entities;

import java.util.Date;
import java.util.List;

import enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
	private String id;
	private double balance;
	private Date created_date;
	private AccountStatus status;
	private Customer customer;
	private List<BankAccountOperation> banckAccountoperations;
	
}
