package com.bay.diaryapp.post;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String exception) {
        super(exception);
    }
}
