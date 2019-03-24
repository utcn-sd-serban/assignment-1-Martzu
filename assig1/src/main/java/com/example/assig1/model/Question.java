package com.example.assig1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private int id = 0;
    private int userId;
    private String title;
    private String text;


    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public Question(int userId, String title, String text, Date creationDate)
    {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
    }

    public String toString()
    {
        return title + "\n" + "Description: " + text + "\n" + "Creation date:" + creationDate;
    }


}
