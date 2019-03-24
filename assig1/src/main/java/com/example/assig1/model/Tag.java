package com.example.assig1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private int id = 0;
    private String text;


    public Tag(String text)
    {
        this.text = text;
    }


    public boolean equals(Object object)
    {
        final Tag other = (Tag) object;
        if (this.text.equals(other.getText()))
        {
            return true;
        }
        return false;
    }
}
