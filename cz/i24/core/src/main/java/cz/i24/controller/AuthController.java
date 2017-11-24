package cz.i24.controller;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.i24.entity.Privilege;
import cz.i24.entity.Role;
import cz.i24.entity.User;
import cz.i24.exception.InvalidPasswordException;
import cz.i24.exception.UserAlreadyExistsException;
import cz.i24.exception.UserNotFoundException;
import cz.i24.security.auth.JwtAuthenticationRequest;
import cz.i24.security.auth.JwtAuthenticationResponse;
import cz.i24.security.auth.JwtUser;
import cz.i24.security.auth.JwtUtil;
import cz.i24.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthController provides signup, signin and token refresh methods
 * @author saka7
 */
@RestController
@Slf4j
public class AuthController {

    @Value("${auth.header}")
    private String tokenHeader;

    public final static String SIGNUP_URL = "/api/auth/signup";
    public final static String SIGNIN_URL = "/api/auth/signin";
    public final static String REFRESH_TOKEN_URL = "/api/auth/token/refresh";

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private UserService userService;

    /**
     * Injects AuthenticationManager instance
     * @param authenticationManager to inject
     */
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Injects JwtUtil instance
     * @param jwtUtil to inject
     */
    @Autowired
    public void setJwtTokenUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Injects UserDetailsService instance
     * @param userDetailsService to inject
     */
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Injects UserService instance
     * @param userService to inject
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Adds new user and returns authentication token
     * @param authenticationRequest request with username, email and password fields
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNUP_URL)
    public ResponseEntity createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        final String name = authenticationRequest.getUsername();
        final String email = authenticationRequest.getEmail();
        final String password = authenticationRequest.getPassword();
        log.info("[POST] CREATING TOKEN FOR User " + name);

        if(userService.findByName(name) != null) {
            throw new UserAlreadyExistsException();
        }

        if(userService.findByEmail(email) != null) {
            throw new UserAlreadyExistsException();
        }

        List<Privilege> privileges = Arrays.asList(
                new Privilege("readProduct"),
                new Privilege("createCustomer"),
                new Privilege("readCustomer"),
                new Privilege("updateCustomer"),
                new Privilege("deleteCustomer")
                );

        Role role  = new Role(1L, "ROLE_ADMIN");
        userService.save(new User(0L, name, email, password, role));
        JwtUser userDetails;

        try {
            userDetails = (JwtUser) userDetailsService.loadUserByUsername(name);
        } catch (UsernameNotFoundException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * Returns authentication token for given user
     * @param authenticationRequest with username and password
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNIN_URL)
    public ResponseEntity getAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        final String name = authenticationRequest.getUsername();
        final String password = authenticationRequest.getPassword();
        log.info("[POST] GETTING TOKEN FOR User " + name);
        JwtUser userDetails;

        try {
            userDetails = (JwtUser) userDetailsService.loadUserByUsername(name);
        } catch (UsernameNotFoundException | NoResultException ex) {
            log.error(ex.getMessage());
            throw new UserNotFoundException();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }

        if(!passwordEncoder().matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException();
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * Refreshes token
     * @param request with old JWT
     * @return Refreshed JWT
     */
    @PostMapping(REFRESH_TOKEN_URL)
    public ResponseEntity refreshAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        log.info("[POST] REFRESHING TOKEN");
        String refreshedToken = jwtUtil.refreshToken(token);
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
    }

}
