package ru.fsw.revo.utils.exception;

public class VotePerDayException extends RuntimeException{
    public VotePerDayException (String msg){
        super(msg);
    }
}
