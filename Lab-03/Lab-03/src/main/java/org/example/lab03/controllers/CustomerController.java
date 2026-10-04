package org.example.lab03.controllers;

import org.example.lab03.model.entities.Customer;
import org.example.lab03.model.entities.Review;
import org.example.lab03.model.repositories.CustomerRepository;
import org.example.lab03.model.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    //This method retrieves a list of all customers from the services.
    //This method is triggered when someone accesses the URL (customers/) and displays all customers in the view.
    @GetMapping({"/", ""})
    public ModelAndView getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        allCustomers.forEach(System.out::println);
        return new ModelAndView("/displayAllCustomers", "customerList", allCustomers);
    }

    //This method retrieves a specific customer by their customerId.
    //This method is triggered when someone accesses a URL like customers/123, displaying a specific customer's details in the view.
    @GetMapping("/{customerId}")
    public ModelAndView getOneCustomer(@PathVariable Long customerId) {
        Optional<Customer> foundCustomer = customerService.getCustomerById(customerId);

        if (foundCustomer.isPresent()) {
            Customer aCustomer = foundCustomer.get();
            List<Review> customersReviews = foundCustomer.get().getReviewList();

            System.out.println(customersReviews);
            System.out.println(aCustomer);

            return new ModelAndView("/displayOneCustomer", "aCustomer", aCustomer);
        } else {
            //Handle customer not found case (going to display an error page, but we could redirect)
            return new ModelAndView("/customerNotFound", "customerId", customerId);
        }
    }

    @GetMapping("/delete/{customerId}")
    public ModelAndView deleteCustomerById(@PathVariable long customerId)
    {
        Optional<Customer> foundCustomer = customerService.getCustomerById(customerId);

        if (foundCustomer.isPresent()) {

            Customer aCustomer = foundCustomer.get();
            customerService.deleteCustomer(aCustomer);

            System.out.println("Customer has been deleted: " + aCustomer);

            return new ModelAndView("/deleteCustomerById", "aCustomer",aCustomer);
        } else {
            return new ModelAndView("/customerNotFound", "customerId", customerId);
        }
    }

    @GetMapping("/add")
    public ModelAndView createCustomer()
    {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Oisin");
        newCustomer.setLastName("Webb");
        newCustomer.setEmail("Owebb@gmail.com");
        newCustomer.setPassword("password123");
        newCustomer.setAddress("123 Street");
        newCustomer.setCity("Tipperary");

        Customer aCustomer = customerService.createCustomer(newCustomer);

        ModelAndView mav = new ModelAndView("/addCustomer", "aCustomer", aCustomer);

        System.out.println(mav);
        return mav;
    }

    @GetMapping("/update/{customerId}")
    public ModelAndView updateCustomer(@PathVariable long customerId)
    {
        Optional<Customer> foundCustomer = customerService.getCustomerById(customerId);

        if (foundCustomer.isPresent())
        {
            Customer aCustomer = foundCustomer.get();
            aCustomer.setFirstName("Hello");
            aCustomer.setLastName("World");

            customerService.updateCustomer(aCustomer);

            System.out.println("Customer has been updated" + aCustomer);
            return new ModelAndView("updateCustomer", "aCustomer", aCustomer);

        }else{
            return new ModelAndView("/customerNotFound","aCustomer",customerId);
        }
    }
}