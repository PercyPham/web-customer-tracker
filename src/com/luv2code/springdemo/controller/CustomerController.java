package com.luv2code.springdemo.controller;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// inject customer dao
    @Autowired
    private CustomerService customerService;

    @GetMapping("/processForm")
    public String someMethod() {
    	return null;
	}

	@RequestMapping("/list")
	public String listCustomers(Model model) {
		// get customers from service
        List<Customer> customers = customerService.getCustomers();

        // add customers to spring mvc model
	    model.addAttribute("customers", customers);

	    return "list-customers";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

    	Customer customer = new Customer();

    	model.addAttribute("customer", customer);

    	return "customer-form";
	}

	@RequestMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
    	customerService.saveCustomer(customer);

    	return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id,
									Model model) {
    	// get the customer from database
		Customer customer = customerService.getCustomer(id);

		// set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);

		// send over to our form
		return "customer-form";
	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam("customerId") int id) {

        customerService.deleteCustomer(id);

		return "redirect:/customer/list";
	}


}
