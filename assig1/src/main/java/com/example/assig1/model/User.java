package com.example.assig1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    private int id = 0;
    private String fullName;
    private String password;
    private String email;


    public User(String fullName, String password, String email)
    {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }
}
