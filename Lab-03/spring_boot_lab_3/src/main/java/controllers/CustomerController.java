package controllers;

import model.services.CustomerService;
import model.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
            System.out.println(aCustomer);
            return new ModelAndView("/displayOneCustomer", "aCustomer", aCustomer);
        } else {
            //Handle customer not found case (going to display an error page but we could redirect)
            return new ModelAndView("/customerNotFound", "customerId", customerId);
        }
    }
}