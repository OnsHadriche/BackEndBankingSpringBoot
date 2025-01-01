package tn.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.com.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
