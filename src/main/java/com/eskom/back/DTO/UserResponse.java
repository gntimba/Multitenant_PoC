package com.eskom.back.DTO;

import com.eskom.back.model.entity.User;
import lombok.Data;

@Data
public class UserResponse {
    private String token;
    private String message;
    User user;

    public UserResponse(String token, String message,User user) {
        this.token = token;
        this.message = message;
        this.user=user;
    }
}
