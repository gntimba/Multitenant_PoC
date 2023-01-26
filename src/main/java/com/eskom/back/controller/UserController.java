package com.eskom.back.controller;

import com.eskom.back.DTO.UserRequest;
import com.eskom.back.DTO.UserResponse;
import com.eskom.back.DTO.userDTO;
import com.eskom.back.Exception.NotFoundException;
import com.eskom.back.model.entity.User;
import com.eskom.back.repo.UserRepo;
import com.eskom.back.security.JWTUtil;
import com.eskom.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@Transactional
public class UserController {

    @Autowired
    private UserService service;


    @Autowired
    private JWTUtil util;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {

        //Validate username/password with DB(required in case of Stateless Authentication)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        String token = util.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token, "Token generated successfully!",service.findByemail(request.getUsername()).get()));
    }



    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody userDTO user) {
        ResponseEntity<User> resp = null;
        ResponseEntity<String> error = null;
        try {
            var id = service.save(user);
            resp = new ResponseEntity<User>(
                    id, HttpStatus.CREATED
            );
        } catch (Exception e) {
            e.printStackTrace();
            error = new ResponseEntity<String>(
                    "Unable to save User",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id,@RequestHeader HttpHeaders headers) {
        ResponseEntity<?> resp = null;
        try {
            Optional<User> user = service.findByemail(id);
            resp = new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to find User",
                    HttpStatus.NOT_FOUND);
        }
        return resp;
    }


}
