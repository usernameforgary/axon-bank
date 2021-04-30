package com.example.axonbank.exception;

public enum ErrorCode {
    //CommandExecutionException error code
    CommandError(1000, "命令执行错误");

    private int code;

    private String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
