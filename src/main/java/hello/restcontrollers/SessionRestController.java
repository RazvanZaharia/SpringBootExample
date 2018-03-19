package hello.restcontrollers;

import hello.config.SpringSecurityConfig;
import hello.config.security.token.utils.ManageAuthToken;
import hello.config.userdetails.CustomUserDetailsService;
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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static hello.config.security.token.jwt.JWTConstants.*;
import static hello.restcontrollers.RestConstants.LOGIN_USER_ENDPOINT;
import static hello.restcontrollers.RestConstants.LOGOUT_USER_ENDPOINT;
import static hello.restcontrollers.RestConstants.REGISTER_USER_ENDPOINT;

@RestController
public class SessionRestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping(value = REGISTER_USER_ENDPOINT)
    private @ResponseBody
    TokenResponse registerUser(@DTO(NewUserDTO.class) User user,
                               HttpServletResponse response) {
        if (user != null
                && user.getUsername() != null && !user.getUsername().isEmpty()
                && user.getPassword() != null && !user.getPassword().isEmpty()) {
            User registeredUser = userRepository.save(user);

            String token = ManageAuthToken.generateTokenFromUser(registeredUser);

            response.addHeader(HEADER_STRING, token);
            return new TokenResponse(new ModelMapper().map(registeredUser, UserDTO.class), token);
        }

        throw new BadCredentialsException("Error registering");
    }

    @PostMapping(value = LOGIN_USER_ENDPOINT)
    private @ResponseBody
    TokenResponse loginUser(@DTO(LoginUserDTO.class) LoginUserDTO loginUser,
                            HttpServletResponse response) {

        Authentication authResult = internalAuthenticationProvider().authenticate(new UsernamePasswordAuthenticationToken(
                loginUser.getUsername(),
                loginUser.getPassword(),
                new ArrayList<>()));

        if (authResult == null || !authResult.isAuthenticated()) {
            throw new BadCredentialsException("No user found for username - " + loginUser.getUsername());
        }

        User databaseUser = userRepository.findByUsername(loginUser.getUsername().toLowerCase());
        String token = ManageAuthToken.generateTokenFromUser(databaseUser);

        response.addHeader(HEADER_STRING, token);
        return new TokenResponse(new ModelMapper().map(databaseUser, UserDTO.class), token);
    }

    @PostMapping(value = LOGOUT_USER_ENDPOINT)
    private @ResponseBody
    SuccessResponse logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new SuccessResponse(true);
    }

    public AuthenticationProvider internalAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(SpringSecurityConfig.passwordEncoder());
        return authProvider;
    }

}
