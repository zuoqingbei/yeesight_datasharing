package com.haier.datamart.exception;

/**
 * Created by long on 2018/11/19.
 */
public class BusinessException extends RuntimeException{
    public BusinessException(){
        super();
    }
    public BusinessException(String msg){
        super(msg);
    }
    public BusinessException(Throwable e){
        super(e);
    }
}
