# Spring-Boot-Security-Jwt
Project using Spring Boot + Security + JWT for REST endpoints authentication / authorization.


## About the example

* Spring Boot 1.5.4.RELEASE
* Sprign Framework 4.3.9.RELEASE
* Spring Security 4.2.3.RELEASE
* Tomcat Embed 8.5.15
* Joda DateTime 2.9.9

## Login
You can login using two ways:

1 - Calling the endpoint /login in LoginController<br/>
    Format: JSON
``` 
{ 
  "username" : "user"
  "password" : "test123"
}
```
2 - Calling the endpoint /loginForm that SpringSecurity offers and we config in WebSecurityConfig class.<br/> 
    Format: x-www-form-urlencoded (Form submit, for example)
    
The success response is the following:
```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaHIiLCJyb2xlIjoiQURNSU4iLCJleHAiOjE1MDcxMDg3MjJ9.-fkoAQ-u8zHQBE4OgayRtJOpSTaEEyaL1bbPRt-bRNUy_qarcA8zs_BQ4aIh8n4FcQ3eZbK8HzOHZ5JzX08Yhg  
```  
In case of any error during authentication:
```
401 Unauthorized
```
    
## Authenticated URL's 
This URL's can only be reached if the user is authenticated (token is valid in the HEADER)

Header:
```
  Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaHIiLCJyb2xlIjoiQURNSU4iLCJleHAiOjE1MDcxMDg3MjJ9.-fkoAQ-u8zHQBE4OgayRtJOpSTaEEyaL1bbPRt-bRNUy_qarcA8zs_BQ4aIh8n4FcQ3eZbK8HzOHZ5JzX08Yhg
```

Endpoints Available:

`http://localhost:8080/api/hello/admin` (GET) <br/>
`http://localhost:8080/api/hello/user` (GET) <br/>
`http://localhost:8080/api/me` (POST) <br/>
`http://localhost:8080/api/user` (POST) <br/>


## Running the Example
This project contains an Embedded maven. 
In a terminal, navigate to the project folder and run:

On Linux:
```
./mvnw clean spring-boot:run
```
On Windows
```
mvnw.cmd clean spring-boot:run
```


Then, you can login:

`http://localhost:8080/login`<br/>
`http://localhost:8080/loginForm`

Try any of the combinations:

* User role: user/test123
* Admin role: admin/test123

They are configured in WebSecurityConfig.java:


```
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //Default users to grant access
        authenticationManagerBuilder
            .inMemoryAuthentication()
            .withUser("user").password("test123").authorities("USER").and()
            .withUser("admin").password("test123").authorities("ADMIN");

        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }
```