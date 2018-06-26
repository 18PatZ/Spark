package com.patrickzhong.spark.command;

import lombok.Getter;

public class CommandException extends RuntimeException {

    @Getter private String message;

    public CommandException(String message){
        this.message = message;
    }

}
