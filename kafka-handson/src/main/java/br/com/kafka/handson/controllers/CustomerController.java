package br.com.kafka.handson.controllers;

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
	public void produceBaseCustomer() {
		customerService.produceBaseCustomer();
	}

	@GetMapping("/vip-customer")
	public void produceVipCustomer() {
		customerService.produceVipCustomer();
	}

}
