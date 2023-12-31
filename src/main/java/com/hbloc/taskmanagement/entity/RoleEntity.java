package com.hbloc.taskmanagement.entity;

import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    private String code;
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<RolePermissionsEntity> rolePermissions;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RolePermissionsEntity> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Set<RolePermissionsEntity> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
