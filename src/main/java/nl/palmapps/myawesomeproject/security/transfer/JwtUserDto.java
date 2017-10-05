package nl.palmapps.myawesomeproject.security.transfer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

/**
 * Simple placeholder for info extracted from the JWT
 */
public class JwtUserDto {

    private String username;

    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static JwtUserDto buildFromAuthentication(Authentication authentication) {
        JwtUserDto jwtUserDto = new JwtUserDto();
        jwtUserDto.setRole(authentication.getAuthorities().toArray()[0].toString());
        jwtUserDto.setUsername(((User) authentication.getPrincipal()).getUsername());

        return jwtUserDto;
    }
}