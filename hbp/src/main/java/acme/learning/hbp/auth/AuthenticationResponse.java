package acme.learning.hbp.auth;

import ch.qos.logback.core.joran.conditional.PropertyEvalScriptBuilder;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
