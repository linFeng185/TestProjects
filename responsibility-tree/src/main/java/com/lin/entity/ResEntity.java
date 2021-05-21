package com.lin.entity;

/**
 * @author lin
 * @date 2021/5/20 16:27
 **/
public class ResEntity {
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResEntity (){
        code = 200;
        message = "success";
    }
}
