package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping("/")
    public String index() {
        return "Hello from SpringBoot Example!!";
    }

    @GetMapping("/users")
    @ResponseBody
    public String getUsers() {
        String usersJson = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            usersJson = mapper.writeValueAsString(repository.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            usersJson = "Error";
        }

        return usersJson;
    }

    private String response;

    @GetMapping(value = {"/users/"})
    @ResponseBody
    private String getUserByLastName(@RequestParam("username") String username) {
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