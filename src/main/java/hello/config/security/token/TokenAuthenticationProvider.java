package hello.config.security.token;

import hello.config.security.token.utils.ManageToken;
import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final AuthenticationToken tokenContainer = (AuthenticationToken) auth;
        final String token = tokenContainer.getToken();

        //do i know this token?
       /* if (!tokenStore.contains(token)) {
            //...if not: the token is invalid!
            throw new BadCredentialsException("Invalid token - " + token);
        }*/

        final User user = ManageToken.checkTokenAndReturnUser(token, userRepository);
        if (user == null) {
            throw new BadCredentialsException("No user found for token - " + token);
        } else {
            return new AuthenticationToken(token, user);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //this class is only responsible for AuthTokenContainers
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
