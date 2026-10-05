package org.example.lab03.controllers;

import org.springframework.ui.Model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.ToString;
import org.example.lab03.model.entities.Customer;
import org.example.lab03.model.entities.Review;
import org.example.lab03.model.repositories.CustomerRepository;
import org.example.lab03.model.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Review> reviewList;


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

    /**
     * Deletes a customer from the database based on the provided customer ID.
     *
     * @param id The unique identifier of the customer to be deleted.
     * @param redirectAttributes Used to add attributes to the redirect response,
     *                           including flash attributes for success messages.
     * @return A String representing the name of the view to redirect to after deletion.
     */
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        // Your implementation goes here
        //you can use RedirectAttributes to pass data when redirecting from one controller method to another, particularly success/error messages.
        Optional<Customer> foundCustomer = customerService.getCustomerById(id.longValue());

        if (foundCustomer.isPresent())
        {
            try {
                Customer aCustomer = foundCustomer.get();
                customerService.deleteCustomer(aCustomer);

                redirectAttributes.addFlashAttribute("successMessage","Customer Deleted Successfully");
                return "redirect:/customers";

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("failMessage", "Customer Deletion Unsuccessful due to the reviews FK key");
                return "redirect:/customers";
            }
        }
        else {
            redirectAttributes.addFlashAttribute("failMessage", "Customer Deleted Unsuccessful");
            return "redirect:/customers";
        }
    }
    /**
     * Displays the update form for a specific customer identified by the given ID.
     * If the customer is found, their details are pre-populated in the form.
     * Otherwise, the user is redirected to the customer list with an error message.
     *
     * @param id The unique identifier of the customer to be updated.
     * @param model The Model object used to pass customer details to the view.
     * @return The name of the Thymeleaf template to render
     *         if the customer is found (the update form), or a redirect to the customer list if not.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes)
    {
        // Your implementation goes here
        Optional<Customer> foundCustomer = customerService.getCustomerById(id.longValue());

        if (foundCustomer.isPresent())
        {
            Customer aCustomer = foundCustomer.get();
            model.addAttribute("customer",aCustomer);

            return "editCustomerForm";
        }
        else{
            redirectAttributes.addFlashAttribute("failMessage","Customer ID could not be found");
            return "redirect:/customers";
        }
    }

    /**
     * Processes the submission of the customer update form.
     * This method updates the customer details in the database
     * based on the provided Customer object. After a successful update,
     * the user is redirected to the customer list with a success message.
     *
     * @paramcustomer The Customer object containing the updated details
     *                 of the customer, populated from the form submission.
     * @paramredirectAttributes The RedirectAttributes object used to
     *                           pass flash attributes to the redirected page,
     *                           allowing for messages to be displayed to the user.
     * @return A redirect string to the customer list page, indicating
     *         that the update was successful.
    */

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
        // Your implementation goes here
        try {
            customerService.updateCustomer(customer);

            redirectAttributes.addFlashAttribute("successMessage", "Customer has been updated successfully");
            return "redirect:/customers";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("failMessage", "Could not locate customer ID returning to customer list");
            return "redirect:/customers";
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
}