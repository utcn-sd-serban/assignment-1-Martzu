package com.example.assig1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteQuestion
{
    private int id = 0;
    private int userId;
    private int questionId;
    private String type;

}
