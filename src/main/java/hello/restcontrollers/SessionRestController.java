package hello.restcontrollers;

import hello.config.security.token.utils.ManageToken;
import hello.model.User;
import hello.model.userDTOs.LoginUserDTO;
import hello.model.userDTOs.NewUserDTO;
import hello.model.userDTOs.UserDTO;
import hello.repository.UserRepository;
import hello.responses.SuccessResponse;
import hello.responses.TokenResponse;
import hello.utils.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static hello.restcontrollers.RestConstants.LOGIN_USER_ENDPOINT;
import static hello.restcontrollers.RestConstants.LOGOUT_USER_ENDPOINT;
import static hello.restcontrollers.RestConstants.REGISTER_USER_ENDPOINT;

@RestController
public class SessionRestController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = REGISTER_USER_ENDPOINT)
    private @ResponseBody
    TokenResponse registerUser(@DTO(NewUserDTO.class) User user) {
        if (user != null
                && user.getUserName() != null && !user.getUserName().isEmpty()
                && user.getPassword() != null && !user.getPassword().isEmpty()) {
            User registeredUser = userRepository.save(user);

            String token = ManageToken.generateTokenFromUser(registeredUser);
            return new TokenResponse(new ModelMapper().map(registeredUser, UserDTO.class), token);
        }

        throw new BadCredentialsException("Error registering");
    }

    @PostMapping(value = LOGIN_USER_ENDPOINT)
    private @ResponseBody
    TokenResponse loginUser(@DTO(LoginUserDTO.class) LoginUserDTO loginUser) {
        if (loginUser != null
                && loginUser.getUserName() != null && !loginUser.getUserName().isEmpty()
                && loginUser.getPassword() != null && !loginUser.getPassword().isEmpty()) {
            User databaseUser = userRepository.findByUserName(loginUser.getUserName().toLowerCase());

            if (databaseUser != null) {
                if (databaseUser.getPassword().equals(loginUser.getPassword())) {
                    String token = ManageToken.generateTokenFromUser(databaseUser);
                    return new TokenResponse(new ModelMapper().map(databaseUser, UserDTO.class), token);
                }
            }
        }

        throw new BadCredentialsException("No user found for username - " + loginUser.getUserName());
    }

    @PostMapping(value = LOGOUT_USER_ENDPOINT)
    private @ResponseBody
    SuccessResponse logoutUser(@DTO(NewUserDTO.class) User user) {
        ModelMapper modelMapper = new ModelMapper();
        return new SuccessResponse(true);
    }
}
