package org.kp.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException (String string){
        super(string);
    }
}
