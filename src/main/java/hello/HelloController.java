package hello;

import hello.model.Customer;
import hello.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private CustomerRepository repository;

    @RequestMapping("/")
    public String index() {
        return "Hello from SpringBoot Example!!";
    }

    @GetMapping("/users")
    public String getUsers() {
        String response = "";
        // fetch all customers
        response = response.concat("Customers found with findAll():");
        response = response.concat("-------------------------------");
        response = response.concat("\n");
        for (Customer customer : repository.findAll()) {
            response = response.concat(customer.toString());
            response = response.concat("\n");
        }
        response = response.concat("");

        return response;
    }

    private String response;

    @RequestMapping("/users/{username}")
    private String getUserByLastName(@PathVariable String username) {
        response = "";
        repository.findByLastName(username).forEach(user -> {
            response = response.concat(user.toString());
        });

        if (response.isEmpty()) {
            response = "No user!";
        }
        return response;
    }

}