package com.hbloc.taskmanagement.api.auth;

import com.hbloc.taskmanagement.request.AuthenticateRequest;
import com.hbloc.taskmanagement.request.RegisterRequest;
import com.hbloc.taskmanagement.response.AuthenticationResponse;
import com.hbloc.taskmanagement.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        try {
            authenticationService.register(request);
        } catch (Throwable e) {
            response.setResultCode("999");
            response.setResultDescription(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
 }
