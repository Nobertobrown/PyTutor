package com.example.pytutor.model;

import java.util.HashMap;

public class Lesson {
    private HashMap<String, Object> content;
    private String title;
    private Boolean isCompleted;

    public Lesson(String title, HashMap<String, Object> content, Boolean isCompleted) {
        this.content = content;
        this.title = title;
        this.isCompleted = isCompleted;
    }

    public Lesson(String title, HashMap<String, Object> content) {
        this(title, content, false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public HashMap<String, Object> getContent() {
        return content;
    }

    public void setContent(HashMap<String, Object> content) {
        this.content = content;
    }
}
