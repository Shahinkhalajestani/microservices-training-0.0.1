package com.shahintraining.aggregatorservice.exception;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
