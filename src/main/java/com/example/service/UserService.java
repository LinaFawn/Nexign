package com.example.service;

import com.example.entity.User;
import com.example.model.UserModel;
import com.example.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private  UserRepo userRepo;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public int addUser(UserModel userModel) {
        String name = userModel.getName();
        String email = userModel.getEmail();
        return this.userRepo.save(new User(name, email)).getId();
    }

    public UserModel getUserById(int id) {
        User user = this.userRepo.findById(id);
        String name = user.getName();
        String email = user.getEmail();
        UserModel userModel = new UserModel(name, email);
        return userModel;
    }
}