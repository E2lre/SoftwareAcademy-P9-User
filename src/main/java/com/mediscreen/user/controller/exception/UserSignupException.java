package com.mediscreen.user.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserSignupException extends Exception {
    private static final Logger logger = LogManager.getLogger(UserSignupException.class);

    public UserSignupException(String s) {
        super(s);
        logger.error(s);
    }
}
