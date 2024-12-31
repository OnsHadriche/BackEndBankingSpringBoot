package tn.com.entities;

import java.util.Date;
import java.lang.*;
import enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BankAccountOperation {
	private Long id;
	private Date operationDate;
	private double amount;
	private OperationType type;
	private BankAccount bankAccount;
	

}
