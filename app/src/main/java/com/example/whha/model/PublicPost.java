package com.example.whha.model;

public class PublicPost {
    private String author;
    private String content;

    public PublicPost(String author, String content){
        this.author = author;
        this.content = content;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getContent(){
        return this.content;
    }
}
