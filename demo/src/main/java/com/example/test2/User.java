package com.example.test2;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String email;
    private String name;
    private List<String> roles;
}