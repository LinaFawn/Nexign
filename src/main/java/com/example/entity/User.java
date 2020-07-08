package com.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "statusId")
    private int statusId;

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}