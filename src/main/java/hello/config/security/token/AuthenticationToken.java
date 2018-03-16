package hello.config.security.token;

import hello.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Arrays;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final User user;

    public AuthenticationToken(String token) {
        super(Arrays.asList());

        this.token = token;
        this.user = null;
        setAuthenticated(false);
    }

    public AuthenticationToken(String token, User user) {
        super(Arrays.asList());

        this.token = token;
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }

    @Override
    public Object getPrincipal() {
        return getUser();
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
