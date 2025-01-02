package tn.com.entities;

import java.util.Date;
import java.util.List;

import enums.AccountStatus;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bank_account")

//on va utilisé une seule table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE", length=4)
@Data
@NoArgsConstructor
@AllArgsConstructor
//la classe doit etre abstract
public class BankAccount {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private double balance;
	private Date created_date;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	@ManyToOne
	// plusieur comptes bancaires concerne un client
	private Customer customer;
	@OneToMany(mappedBy = "bankAccount", fetch=FetchType.EAGER)
	private List<AccountOperation> banckAccountoperations;

}
