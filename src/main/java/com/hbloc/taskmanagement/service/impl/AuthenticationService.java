package com.hbloc.taskmanagement.service.impl;

import com.hbloc.taskmanagement.config.JwtService;
import com.hbloc.taskmanagement.entity.*;
import com.hbloc.taskmanagement.enums.StatusEnum;
import com.hbloc.taskmanagement.repositories.*;
import com.hbloc.taskmanagement.request.AuthenticateRequest;
import com.hbloc.taskmanagement.request.RegisterRequest;
import com.hbloc.taskmanagement.response.AuthenticationResponse;
import com.hbloc.taskmanagement.response.BaseResponse;
import com.hbloc.taskmanagement.service.IAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

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


    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 StatusRepository statusRepository,
                                 RolePermissionRepository rolePermissionRepository,
                                 PermissionRepository permissionRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
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
    public AuthenticationResponse register(RegisterRequest request) throws Throwable {
        validateExistEmail(request);

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());

        validatePasswordAndConfirmPassword(request.getPassword(), request.getConfirmPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        StatusEntity status = getStatus(StatusEnum.ACTIVE.getValue());
        user.setStatus(status);

        RoleEntity role = getRole(request.getRoleCode());
        Set<RolePermissionsEntity> permissionEntitySet = getRolePermission(request, role);
        role.setRolePermissions(permissionEntitySet);

        user.setRole(role);
        userRepository.save(user);

        AuthenticationResponse response = buildSuccessResponse(user);
        return response;
    }

    private RoleEntity getRole(String roleCode) throws Throwable {
        Optional<RoleEntity> roleEntity =
                Optional.of(
                        roleRepository.findByCode(roleCode)
                                .orElseThrow(
                                        (Supplier<Throwable>) () -> new Exception("Role not found!")
                                )
                );
        return roleEntity.get();
    }

    private StatusEntity getStatus(Integer statusId) throws Throwable {
        Optional<StatusEntity> status =
                Optional.of(
                        statusRepository.findById(statusId)
                                .orElseThrow(
                                        (Supplier<Throwable>) () -> new Exception("Status not found!")
                                )
                );

        return status.get();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Optional<UserEntity> user = Optional.of(userRepository.findByEmail(request.getEmail()).orElseThrow());
        AuthenticationResponse response = buildSuccessResponse(user.get());
        return response;
    }

    private boolean isEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private void validatePasswordAndConfirmPassword(String password, String confirmPassword) throws Exception {
        if (!isEqual(password, confirmPassword)) {
            throw new Exception("Confirm password is not match");
        }
    }

    private void validateExistEmail(RegisterRequest request) throws Exception {
        Optional<UserEntity> oldUser = userRepository.findByEmail(request.getEmail());
        if (oldUser.get() != null) {
            throw new Exception("Email is already exists!");
        }
    }

    private Set<RolePermissionsEntity> getRolePermission(RegisterRequest request, RoleEntity role) throws Throwable {
        Set<RolePermissionsEntity> permissionEntitySet = new HashSet<>();
        for (String permissionCode : request.getPermissionsCode()) {
            PermissionEntity permission = getPermission(permissionCode);
            RolePermissionsEntity rolePermissions = new RolePermissionsEntity();
            rolePermissions.setPermission(permission);
            rolePermissions.setRole(role);
            rolePermissionRepository.save(rolePermissions);
            permissionEntitySet.add(rolePermissions);
        }
        return permissionEntitySet;
    }

    private PermissionEntity getPermission(String permissionCode) throws Throwable {
        Optional<PermissionEntity> permission =
                Optional.of(
                        permissionRepository.findByCode(permissionCode)
                                .orElseThrow(
                                        (Supplier<Throwable>) () -> new Exception("Permission not found!")
                                )
                );
        return permission.get();
    }

    private AuthenticationResponse buildSuccessResponse(UserEntity user) {
        AuthenticationResponse response = new AuthenticationResponse();
        String jwtToken = jwtService.generateToken(user);
        response.setResultCode("0");
        response.setResultDescription("Success");
        response.setToken(jwtToken);
        return response;
    }
}
