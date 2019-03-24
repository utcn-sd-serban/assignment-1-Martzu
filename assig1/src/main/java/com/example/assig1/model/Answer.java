package com.example.assig1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private int id = 0;
    private int questionId;
    private int userId;
    private String text;
    private Date creationDate;



}
