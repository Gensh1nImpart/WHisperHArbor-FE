package com.example.whha.model;

import java.sql.Time;

public class PublicPost {
    private String author;
    private String content;
    private String time;

    public PublicPost(String author, String content, String time){
        this.author = author;
        this.content = content;
        this.time = time;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getContent(){
        return this.content;
    }

    public String getTime(){return this.time;}
}
