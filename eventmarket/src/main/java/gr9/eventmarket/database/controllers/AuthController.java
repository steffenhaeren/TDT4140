package gr9.eventmarket.database.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr9.eventmarket.database.model.ERole;
import gr9.eventmarket.database.model.Role;
import gr9.eventmarket.database.model.User;
import gr9.eventmarket.database.repository.RoleRepository;
import gr9.eventmarket.database.repository.UserRepository;
import gr9.eventmarket.database.security.jwt.JwtUtil;
import gr9.eventmarket.database.security.service.UserDetailImpl;
import gr9.eventmarket.database.payload.response.MessageResponse;
import gr9.eventmarket.database.payload.request.LoginReq;
import gr9.eventmarket.database.payload.request.SignUpReq;
import gr9.eventmarket.database.payload.response.JwtResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginReq loginRequest) {
        logger.debug("Received login request");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getEmail(),
                                                 roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpReq signUpReq) {
        logger.debug("Received signup request");

        if (userRepository.existsByUsername(signUpReq.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username is already in use"));
        }
        if (userRepository.existsByEmail(signUpReq.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user;
        try {
        // Create new user's account
            user = new User(signUpReq.getUsername(),
                             signUpReq.getEmail(),
                             signUpReq.getPhoneNumber(),
                             encoder.encode(signUpReq.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse(e.getMessage()));
        }
        Set<String> strRoles = signUpReq.getRoles();

        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                var eRole = ERole.getRoleByName(role);
                if (eRole.isEmpty()) {
                    throw new RuntimeException("Invalid role: " + role);
                }
                Role r = roleRepository.findByName(eRole.get())
                    .orElseThrow(() -> new RuntimeException("Bad database configuration: Role " + role + " not found"));
                roles.add(r);
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        logger.info("New user registered");
        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }
}
