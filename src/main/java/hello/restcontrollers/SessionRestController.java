package hello.restcontrollers;

import hello.model.User;
import hello.model.userDTOs.LoginUserDTO;
import hello.model.userDTOs.NewUserDTO;
import hello.model.userDTOs.UserDTO;
import hello.repository.UserRepository;
import hello.responses.SuccessResponse;
import hello.utils.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static hello.restcontrollers.RestConstants.LOGIN_USER_ENDPOINT;
import static hello.restcontrollers.RestConstants.LOGOUT_USER_ENDPOINT;
import static hello.restcontrollers.RestConstants.REGISTER_USER_ENDPOINT;

@RestController
public class SessionRestController {

    @Autowired
    private UserRepository repository;

    @PostMapping(value = REGISTER_USER_ENDPOINT)
    private @ResponseBody
    UserDTO registerUser(@DTO(NewUserDTO.class) User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(repository.save(user), UserDTO.class);
    }

    @PostMapping(value = LOGIN_USER_ENDPOINT)
    private @ResponseBody
    SuccessResponse loginUser(@DTO(LoginUserDTO.class) User user) {
        ModelMapper modelMapper = new ModelMapper();
        return new SuccessResponse(true);
    }

    @PostMapping(value = LOGOUT_USER_ENDPOINT)
    private @ResponseBody
    SuccessResponse logoutUser(@DTO(NewUserDTO.class) User user) {
        ModelMapper modelMapper = new ModelMapper();
        return new SuccessResponse(true);
    }
}
