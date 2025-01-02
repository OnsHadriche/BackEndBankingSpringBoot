package tn.com.entities;

import java.util.Date;

import enums.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private OperationType type;
	@ManyToOne
	private BankAccount bankAccount;
	private String description;
	

}
