package nl.palmapps.myawesomeproject.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.palmapps.myawesomeproject.security.transfer.JwtUserDto;
import nl.palmapps.myawesomeproject.security.util.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Created by brunohenriquerother on 04/10/2017.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication)
      throws IOException, ServletException {

    JwtUserDto jwtUserDto = JwtUserDto.buildFromAuthentication(authentication);

    httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
    httpServletResponse.getWriter().append(JwtTokenGenerator.generateToken(jwtUserDto, jwtSecret)).flush();
  }
}
