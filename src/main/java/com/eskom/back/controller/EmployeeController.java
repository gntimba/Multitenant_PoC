package com.eskom.back.controller;

import com.eskom.back.model.entity.User;
import com.eskom.back.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class EmployeeController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping(path = "/user")
    public ResponseEntity<?> createEmployee() {
        User newEmployee = new User();
        newEmployee.setName("godfreyhghggfh");
        newEmployee.setEmail("gntimba@gmail.com");
        userRepo.save(newEmployee);
        return ResponseEntity.ok(newEmployee);
    }
}
