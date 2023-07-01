package com.hbloc.taskmanagement.service.impl;

import com.hbloc.taskmanagement.config.JwtService;
import com.hbloc.taskmanagement.entity.*;
import com.hbloc.taskmanagement.repositories.*;
import com.hbloc.taskmanagement.request.AuthenticateRequest;
import com.hbloc.taskmanagement.request.RegisterRequest;
import com.hbloc.taskmanagement.response.AuthenticationResponse;
import com.hbloc.taskmanagement.service.IAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, StatusRepository statusRepository, RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
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

        StatusEntity status = getStatus(1);
        user.setStatus(status);



        RoleEntity roleEntity = getRole(request.getRole());


        Set<RolePermissionsEntity> permissionEntitySet = new HashSet<>();
        for(Integer permissionId : request.getPermissionsId()) {
            Optional<PermissionEntity> permission =
                    Optional.of(permissionRepository.findById(permissionId).orElseThrow());
            RolePermissionsEntity rolePermissions = new RolePermissionsEntity();
            rolePermissions.setPermission(permission.get());
            rolePermissions.setRole(roleEntity);
            rolePermissionRepository.save(rolePermissions);
            permissionEntitySet.add(rolePermissions);
        }

        roleEntity.setRolePermissions(permissionEntitySet);
        user.setRole(roleEntity);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        response.setResultCode("0");
        response.setResultDescription("Success");
        response.setToken(jwtToken);
        return response;
    }

    private RoleEntity getRole(Integer roleId) {
        Optional<RoleEntity> roleEntity = Optional.of(roleRepository.findById(roleId).orElseThrow());
        return roleEntity.get();
    }

    private StatusEntity getStatus(Integer statusId) {
        Optional<StatusEntity> status = Optional.of(statusRepository.findById(statusId).orElseThrow());
        return status.get();
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
