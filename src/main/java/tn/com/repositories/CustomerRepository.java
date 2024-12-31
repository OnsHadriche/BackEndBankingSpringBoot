package tn.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.com.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
