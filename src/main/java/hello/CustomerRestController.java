package hello;

import hello.model.Customer;
import hello.model.customerDTOs.CustomerDTO;
import hello.model.customerDTOs.NewCustomerDTO;
import hello.model.customerDTOs.UpdateCustomerDTO;
import hello.repository.CustomerRepository;
import hello.utils.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/")
    public String index() {
        return "Hello from SpringBoot Example!!";
    }

    @GetMapping("/users")
    public @ResponseBody
    List<CustomerDTO> getUsers() {
        List<CustomerDTO> listOfUsers = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (Customer customer : repository.findAll()) {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            listOfUsers.add(customerDTO);
        }

        return listOfUsers;
    }

    @GetMapping(value = {"/users/"})
    private @ResponseBody
    List<CustomerDTO> getUser(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "userId", required = false) Long userId) {
        ModelMapper modelMapper = new ModelMapper();
        List<CustomerDTO> listOfUsers = new ArrayList<>();

        if (userId != null) {
            Optional<Customer> customerOptional = repository.findById(userId);
            if (customerOptional.isPresent()) {
                CustomerDTO customerDTO = modelMapper.map(customerOptional.get(), CustomerDTO.class);
                listOfUsers.add(customerDTO);
            }
        } else if (username != null && !username.isEmpty()) {
            for (Customer item : repository.findByLastName(username)) {
                CustomerDTO customerDTO = modelMapper.map(item, CustomerDTO.class);
                listOfUsers.add(customerDTO);
            }
        }

        return listOfUsers;
    }

    @PostMapping("/users/")
    private @ResponseBody
    CustomerDTO newUser(@DTO(NewCustomerDTO.class) Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(customer), CustomerDTO.class);
    }

    @PutMapping("/users/")
    @ResponseStatus(HttpStatus.OK)
    private @ResponseBody
    CustomerDTO updateUser(@DTO(UpdateCustomerDTO.class) Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(customer), CustomerDTO.class);
    }

}