package com.gz.emos.wx.exception;

import lombok.Data;

/**
 * 自定义异常类
 * 自定义异常类继承的父类，我没有选择Exception。因为Exception类型的异常，我们必须要手动
 * 显式处理，要么上抛，要么捕获。我希望我定义的异常采用既可以采用显式处理，也可以隐式处
 * 理，所以我选择继承RuntimeException这个父类。RuntimeException类型的异常可以被虚拟机隐
 * 式处理，这样就省去了我们很多手动处理异常的麻烦
 */
@Data
public class EmosException extends RuntimeException {
    private String msg;
    private int code = 500;

    public EmosException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public EmosException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public EmosException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public EmosException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
