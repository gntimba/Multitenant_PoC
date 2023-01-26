package com.eskom.back.service;

import com.eskom.back.DTO.ROLE;
import com.eskom.back.DTO.userDTO;
import com.eskom.back.model.entity.User;
import com.eskom.back.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;


    @Transactional
    public User save(userDTO user) {

        User newUser = new User(user);


        newUser.setPassword(bCryptEncoder.encode(user.getPassword()));
        Set<ROLE> roles = new HashSet<>();
        roles.add(ROLE.APP);
        roles.add(ROLE.USER);
        newUser.setRoles(roles);
        return userRepo.save(newUser);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findByEmail(username);

        opt.get();

        org.springframework.security.core.userdetails.User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        } else {
            User user = opt.get();    //retrieving user from DB
            Set<ROLE> roles = user.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (ROLE role : roles) {
                ga.add(new SimpleGrantedAuthority(role.toString()));
            }

            springUser = new org.springframework.security.core.userdetails.User(
                    username,
                    user.getPassword(),
                    ga);
        }

        return springUser;
    }
    public Optional<User> findByemail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepo.findByEmail(currentPrincipalName);
    }
}
