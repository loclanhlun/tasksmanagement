package com.hbloc.taskmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "permissions")
public class PermissionEntity extends BaseEntity{
    private String code;
    private String name;
    @OneToMany(mappedBy = "permission", fetch = FetchType.EAGER)
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
