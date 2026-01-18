package com.ecom.controller;

import com.ecom.dto.UpdateRole;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/swiftmart/admin/")
@RequiredArgsConstructor
public class AdminController {


    private final UserService userService;
    @GetMapping("/findall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> findAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/update-role")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<UserDto> updateRole(@RequestBody UpdateRole updateRole){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateRole(updateRole));
    }
}
