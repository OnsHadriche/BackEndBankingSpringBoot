package tn.com.entities;

import java.util.Date;
import java.util.List;

import enums.AccountStatus;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//on va utilisé une seule table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", length=4)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private double balance;
	private Date created_date;
	private AccountStatus status;
	@ManyToOne
	// plusieur comptes bancaires concerne un client
	private Customer customer;
	@OneToMany(mappedBy = "bankAccount")
	private List<AccountOperation> banckAccountoperations;

}
