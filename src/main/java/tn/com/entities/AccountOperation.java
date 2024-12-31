package tn.com.entities;

import java.util.Date;

import enums.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="account_operation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountOperation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date operationDate;
	private double amount;
	private OperationType type;
	@ManyToOne
	private BankAccount bankAccount;
	

}
