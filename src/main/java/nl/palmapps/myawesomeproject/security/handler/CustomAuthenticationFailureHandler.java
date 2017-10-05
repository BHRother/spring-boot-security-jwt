package nl.palmapps.myawesomeproject.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Created by brunohenriquerother on 04/10/2017.
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {

    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
