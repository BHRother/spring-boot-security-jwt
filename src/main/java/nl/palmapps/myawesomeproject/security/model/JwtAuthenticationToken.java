package nl.palmapps.myawesomeproject.security.model;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


/**
 * Holder for JWT token from the request.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {


    private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}