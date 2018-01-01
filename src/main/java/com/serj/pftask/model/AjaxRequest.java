package com.serj.pftask.model;

import org.hibernate.validator.constraints.NotBlank;

public class AjaxRequest {
    @NotBlank(message = "select start date")
    private String firstDate;
    @NotBlank(message = "select second date")
    private String secondDate;

    public AjaxRequest() {
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }

    @Override
    public String toString() {
        return "AjaxRequest{" +
                "firstDate='" + firstDate + '\'' +
                ", secondDate='" + secondDate + '\'' +
                '}';
    }
}
