package hello.responses;

import hello.model.userDTOs.UserDTO;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    String token;
    UserDTO user;

    public TokenResponse(UserDTO user, String token) {
        this.token = token;
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
