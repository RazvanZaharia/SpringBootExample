package hello.restcontrollers;

import hello.model.User;
import hello.responses.SuccessResponse;
import hello.model.userDTOs.UserDTO;
import hello.model.userDTOs.DeleteUserDTO;
import hello.model.userDTOs.NewUserDTO;
import hello.model.userDTOs.UpdateUserDTO;
import hello.repository.UserRepository;
import hello.utils.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hello.restcontrollers.RestConstants.PARAM_USER_ID;
import static hello.restcontrollers.RestConstants.USERS_ENDPOINT;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    public String index() {
        return "Hello from SpringBoot Example!!";
    }

    @GetMapping(value = "/users")
    private List<UserDTO> getUsers() {
        List<UserDTO> listOfUsers = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (User user : repository.findAll()) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            listOfUsers.add(userDTO);
        }

        return listOfUsers;
    }

    @GetMapping(value = USERS_ENDPOINT)
    private @ResponseBody
    UserDTO getUser(@RequestParam(value = PARAM_USER_ID, required = false) Long userId) {
        if (userId != null) {
            ModelMapper modelMapper = new ModelMapper();

            Optional<User> customerOptional = repository.findById(userId);
            if (customerOptional.isPresent()) {
                return modelMapper.map(customerOptional.get(), UserDTO.class);
            }

        }
        return null;
    }

    @PostMapping(value = USERS_ENDPOINT)
    private @ResponseBody
    UserDTO newUser(@DTO(NewUserDTO.class) User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(user), UserDTO.class);
    }

    @PutMapping(value = USERS_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    private @ResponseBody
    UserDTO updateUser(@DTO(UpdateUserDTO.class) User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(user), UserDTO.class);
    }

    @DeleteMapping(value = USERS_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    private @ResponseBody
    SuccessResponse deleteUser(@DTO(DeleteUserDTO.class) User user) {
        repository.delete(user);
        return new SuccessResponse(true);
    }
}