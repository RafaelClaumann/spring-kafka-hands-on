package br.com.kafka.handson.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kafka.handson.services.CustomerService;

@RestController
@RequestMapping("/producer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/base-customer")
	public ResponseEntity<String> produceBaseCustomer() {
		return customerService.produceBaseCustomer();
	}

	@GetMapping("/vip-customer")
	public ResponseEntity<String> produceVipCustomer() {
		return customerService.produceVipCustomer();
	}

}
