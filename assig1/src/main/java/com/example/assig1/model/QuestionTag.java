package com.example.assig1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuestionTag {

    private int id = 0;
    private int questionId;
    private int tagId;

    public QuestionTag(int questionId, int tagId)
    {
        this.questionId = questionId;
        this.tagId = tagId;
    }
}
