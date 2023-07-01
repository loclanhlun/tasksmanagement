package com.hbloc.taskmanagement.api.auth.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")

public class DemoController {

    @PreAuthorize(value = "hasRole('MANAGER') and hasAnyAuthority('READ','CREATE','UPDATE')")
    @GetMapping("/manager")
    public ResponseEntity<String> manager() {
        return ResponseEntity.ok("Hello from secured endpoint!");
    }


    @PreAuthorize(value = "hasRole('STAFF') and hasAnyAuthority('CREATE')")
    @GetMapping("/staff/create")
    public ResponseEntity<String> staffCreate() {
        return ResponseEntity.ok("STAFF::Create");
    }

    @PreAuthorize(value = "hasRole('STAFF') and hasAnyAuthority('UPDATE')")
    @GetMapping("/staff/update")
    public ResponseEntity<String> staffUpdate() {
        return ResponseEntity.ok("STAFF::Update");
    }

    @PreAuthorize(value = "hasRole('STAFF') and hasAnyAuthority('DELETE')")
    @GetMapping("/staff/delete")
    public ResponseEntity<String> staffDelete() {
        return ResponseEntity.ok("STAFF::Delete");
    }

    @PreAuthorize(value = "hasRole('STAFF') and hasAnyAuthority('READ')")
    @GetMapping("/staff/read")
    public ResponseEntity<String> staffRead() {
        return ResponseEntity.ok("STAFF::Read");
    }


}
