package hello.responses;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
