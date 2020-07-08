package com.example.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserModel {
    private int id;
    private String name;
    private String email;

    public UserModel(String uname, String email) {
        this.name = name;
        this.email = email;
    }
}