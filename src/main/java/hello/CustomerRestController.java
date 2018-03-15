package hello;

import hello.model.Customer;
import hello.model.SuccessResponse;
import hello.model.customerDTOs.CustomerDTO;
import hello.model.customerDTOs.DeleteCustomerDTO;
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

import static hello.repository.RepositoryConstants.PARAM_USER_ID;
import static hello.repository.RepositoryConstants.USER_ENDPOINT;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/")
    public String index() {
        return "Hello from SpringBoot Example!!";
    }

    List<CustomerDTO> getUsers() {
        List<CustomerDTO> listOfUsers = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (Customer customer : repository.findAll()) {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            listOfUsers.add(customerDTO);
        }

        return listOfUsers;
    }

    @GetMapping(value = USER_ENDPOINT)
    private @ResponseBody
    List<CustomerDTO> getUser(@RequestParam(value = PARAM_USER_ID, required = false) Long userId) {
        if (userId != null) {
            ModelMapper modelMapper = new ModelMapper();
            List<CustomerDTO> listOfUsers = new ArrayList<>();

            Optional<Customer> customerOptional = repository.findById(userId);
            if (customerOptional.isPresent()) {
                CustomerDTO customerDTO = modelMapper.map(customerOptional.get(), CustomerDTO.class);
                listOfUsers.add(customerDTO);
            }
            return listOfUsers;
        } else {
            return getUsers();
        }
    }

    @PostMapping(value = USER_ENDPOINT)
    private @ResponseBody
    CustomerDTO newUser(@DTO(NewCustomerDTO.class) Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(customer), CustomerDTO.class);
    }

    @PutMapping(value = USER_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    private @ResponseBody
    CustomerDTO updateUser(@DTO(UpdateCustomerDTO.class) Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(customer), CustomerDTO.class);
    }

    @DeleteMapping(value = USER_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    private @ResponseBody
    SuccessResponse deleteUser(@DTO(DeleteCustomerDTO.class) Customer customer) {
        repository.delete(customer);
        return new SuccessResponse(true);
    }
}