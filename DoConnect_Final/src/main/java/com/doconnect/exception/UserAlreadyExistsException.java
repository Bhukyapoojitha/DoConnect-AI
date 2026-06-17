package com.doconnect.exception; 

 

/** 

 * UserAlreadyExistsException 

 * Thrown when email/username already registered 

 * Returns 409 HTTP status 

 */ 

public class UserAlreadyExistsException 

                extends RuntimeException { 

 

    public UserAlreadyExistsException( 

                            String message) { 

        super(message); 

    } 

} 