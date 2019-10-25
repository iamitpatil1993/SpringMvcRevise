package com.example.mvc.revise.dto;

import java.util.Calendar;

public class Spittle {

    private String id;
    private String message;
    private Calendar postedOn;

    public Spittle(String id, String message, Calendar postedOn) {
        this.id = id;
        this.message = message;
        this.postedOn = postedOn;
    }

    public Spittle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Calendar postedOn) {
        this.postedOn = postedOn;
    }
}
