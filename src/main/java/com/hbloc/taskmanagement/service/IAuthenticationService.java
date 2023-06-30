package com.hbloc.taskmanagement.service;

import com.hbloc.taskmanagement.request.AuthenticateRequest;
import com.hbloc.taskmanagement.request.RegisterRequest;
import com.hbloc.taskmanagement.response.AuthenticationResponse;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticateRequest request);
}
