package com.example.assig1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteAnswer
{
    private int id = 0;
    private int userId;
    private int answerId;
    private String type;

}
