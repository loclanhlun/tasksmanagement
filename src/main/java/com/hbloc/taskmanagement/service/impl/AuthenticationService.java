package com.hbloc.taskmanagement.service.impl;

import com.hbloc.taskmanagement.config.JwtService;
import com.hbloc.taskmanagement.entity.RoleEntity;
import com.hbloc.taskmanagement.entity.UserEntity;
import com.hbloc.taskmanagement.repositories.RoleRepository;
import com.hbloc.taskmanagement.repositories.UserRepository;
import com.hbloc.taskmanagement.request.AuthenticateRequest;
import com.hbloc.taskmanagement.request.RegisterRequest;
import com.hbloc.taskmanagement.response.AuthenticationResponse;
import com.hbloc.taskmanagement.service.IAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        RoleEntity roleEntity = getRole(request.getRole());
        user.setRole(roleEntity);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        response.setResultCode("0");
        response.setResultDescription("Success");
        response.setToken(jwtToken);
        return response;
    }

    private RoleEntity getRole(Integer role_id) {
        Optional<RoleEntity> roleEntity = Optional.of(roleRepository.findById(role_id).orElseThrow());
        return roleEntity.get();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        AuthenticationResponse response = new AuthenticationResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Optional<UserEntity> user = Optional.of(userRepository.findByEmail(request.getEmail()).orElseThrow());

        String jwtToken = jwtService.generateToken(user.get());
        response.setResultCode("0");
        response.setResultDescription("Success");
        response.setToken(jwtToken);
        return response;
    }
}
