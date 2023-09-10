package jiraapi.controller;

import javax.swing.plaf.PanelUI;

public enum ErrorMessages {
    ISSUE_DOES_NOT_EXIST("Issue does not exist or you do not have permission to see it."),
    NOT_PERMISSION("Issue does not exist or you do not have permission to see it.");

    private String errorMessage;

    ErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString(){
        return this.errorMessage;
    }
}
