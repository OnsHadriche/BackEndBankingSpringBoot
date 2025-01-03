package tn.com.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.com.dto.CustomerDTO;
import tn.com.exception.CustomerNotFoundException;
import tn.com.services.BankAccountService;

@RestController
@AllArgsConstructor
@Slf4j

public class CustomerRestController {
	private BankAccountService bankAccountService;
	
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankAccountService.listCustomer();
	}
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
		return bankAccountService.getCustomer(customerId);
		
	}
	@PostMapping("/add-customers")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
		return bankAccountService.saveCustomer(customerDTO);	
	}
	@PutMapping("/update-customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO) {
		customerDTO.setId(customerId);
		return bankAccountService.updateCustomer(customerDTO);
	}
	@DeleteMapping("/delete-customers/{customerId}")
	public void deleteCustomer(@PathVariable Long customerId) {
		bankAccountService.deleteCustomer(customerId);
		
	}
	

}
