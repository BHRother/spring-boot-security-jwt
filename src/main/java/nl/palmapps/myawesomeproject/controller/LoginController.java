package nl.palmapps.myawesomeproject.controller;

import nl.palmapps.myawesomeproject.model.LoginModel;
import nl.palmapps.myawesomeproject.security.transfer.JwtUserDto;
import nl.palmapps.myawesomeproject.security.util.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping
  public ResponseEntity login(@RequestBody LoginModel loginModel) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

    if (isAuthenticated(authentication)) {
      JwtUserDto jwtUserDto = JwtUserDto.buildFromAuthentication(authentication);
      return ResponseEntity.status(HttpStatus.CREATED).body(JwtTokenGenerator.generateToken(jwtUserDto, jwtSecret));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  private boolean isAuthenticated(Authentication authentication) {
    return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
  }
}
