package com.mediscreen.user.controller.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class UserCanNotBeSavedException extends Exception {
    private static final Logger logger = LogManager.getLogger(UserCanNotBeSavedException.class);
    public UserCanNotBeSavedException(String s) {
        super(s);
        logger.error(s);
    }
}
