package com.mediscreen.user.controller;

import com.mediscreen.user.controller.exception.*;
import com.mediscreen.user.model.User;
import com.mediscreen.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /*---------------------------  Signin -----------------------------*/
    /**
     * Check user/password for signin
     * @param username user id
     * @param pwd password for user id
     * @return JWT tocken
     * @throws UserNotFoundException error if not found or technical error
     */
    @GetMapping(value = "signin")
    @ResponseStatus(HttpStatus.OK)
    public String signin(@RequestParam(name = "username") String username,@RequestParam(name = "pwd") String pwd) throws UserNotFoundException {
        logger.info("signin start/finish");
        User userIn =new User(username,pwd);
        String result = userService.signin(userIn);
        if ((result == null) || (result.isEmpty())) {
            logger.warn("The user " + username + " with password is incorrect");
            throw new UserNotFoundException("The user " + username + " with password is incorrect");

        }
        return result;
    }

    /*---------------------------  Post Signup-----------------------------*/
    /**
     * Création a new user
     * @param username user id
     * @param pwd password for user id
     * @return JWT tocken
     * @throws UserSignupException error if already exist or technical error
     */
    @PostMapping(value="/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestParam(name = "username") String username,@RequestParam(name = "pwd") String pwd) throws UserSignupException {
        User user;
        String result = null;
        logger.info("signup start");
        if ((!username.isEmpty()) && (!pwd.isEmpty())){
            user = new User(username,pwd);
            result = userService.addUser(user);
        }

        if (result == null) {
            logger.error(" Impossible to Signup username " + username);
            throw new UserSignupException(" Impossible to Signup username " + username);
        }
        logger.info("signup finish");
        return result;
    }

    /*---------------------------  PUT user -----------------------------*/
    /**
     * udpade an existing user
     * @param username user id to be updated
     * @param pwd new password
     * @return user informations
     * @throws UserCanNotBeSavedException error if not found or technical error
     */
    @PutMapping(value = "user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestParam(name = "username") String username,@RequestParam(name = "pwd") String pwd) throws UserCanNotBeSavedException {
        logger.info("saveUser start");
        User finalResult = null;
        User userIn = new User(username,pwd);

        finalResult = userService.updateUser(userIn);
        if (finalResult == null) {

            logger.warn("The patient " + username + " does not exist");
            throw new UserCanNotBeSavedException("The patient " + username + " does not exist");
        }
        finalResult.setPwd("");//mask pwd to avoid dto implementaition
        return finalResult;
    }
    /*---------------------------  Delete user -----------------------------*/
    /**
     * delete an existing user
     * @param username user id to be deleted
     * @return user info deleted
     * @throws UserCanNotBeDeleteException error if not found or technical error
     */
    @DeleteMapping(value = "user")
    @ResponseStatus(HttpStatus.OK)
    public User deleteUser(@RequestParam(name = "username") String username) throws UserCanNotBeDeleteException {
        logger.info("deleteUser start");

        User finalResult = null;
        User userIn = new User(username,"");

        finalResult = userService.deleteUser(userIn);
        if (finalResult == null) {

            logger.warn("The User " + username + " can not be delete");
            throw new UserCanNotBeDeleteException("The User " + username +  " can not be delete");
        }
        return finalResult;
    }
}
