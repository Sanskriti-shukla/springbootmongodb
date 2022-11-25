package com.example.springbootmongodb.exception;

public class TodoCollectionException extends Exception{
    private static final long serialVersionUID= 1L;
    public TodoCollectionException(String message){
        super(message);
    }
    public static String NotFoundException(String id){
        return "todo wth"+" "+id+" "+"not found";
    }
    public static String TodoAlreadyExists(){
        return "todo with given id already exist.";
    }
}
