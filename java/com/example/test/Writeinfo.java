package com.example.test;

public class Writeinfo {

    private  String title;
    private  String Content;
    private   String name;
    private String time;

    public Writeinfo(String title , String content ,String name , String time) {
        this.Content = content;
        this.title  = title;
        this.name = name;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
