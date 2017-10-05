package nl.palmapps.myawesomeproject.controller;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicLong;
import nl.palmapps.myawesomeproject.model.Greeting;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example controller to test security calls
 */
@RestController
@RequestMapping("/api")
public class MainController {

    private static final String TEMPLATE = "Hello, %s!";
    private static final String TEMPLATE_ADMIN = "Hello Admin, %s!";
    private final AtomicLong counter = new AtomicLong();


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hello/admin")
    public Greeting greetingAdmin(@RequestParam(value = "name", defaultValue = "World") String name) {

        return new Greeting(counter.incrementAndGet(),
                String.format(TEMPLATE_ADMIN, name));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hello/user")
    public Greeting greetingUser(@RequestParam(value = "name", defaultValue = "World") String name) {

        return new Greeting(counter.incrementAndGet(),
            String.format(TEMPLATE, name));
    }

    @PostMapping("/")
    public Greeting homePage(@RequestParam(value = "name", defaultValue = "World") String name) {

        return new Greeting(counter.incrementAndGet(),
                String.format(TEMPLATE, name));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping({"/user", "/me"})
    public ResponseEntity<?> user(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}