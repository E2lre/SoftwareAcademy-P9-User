package com.mediscreen.user.service;

import com.mediscreen.user.config.security.JwtTokenProvider;
import com.mediscreen.user.controller.exception.CustomException;
import com.mediscreen.user.dao.UserDao;
import com.mediscreen.user.model.Role;
import com.mediscreen.user.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String signin(User user) {
        logger.info("start");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPwd()));
            logger.info("finish");
            return jwtTokenProvider.createToken(user.getUsername(), userDao.findByUsername(user.getUsername()).getRoles());
        } catch (AuthenticationException e) {

            logger.error("Invalid username/password supplied for " + user.getUsername());
            return "";
        }
    }

    @Override
    public String addUser(User user) {
        boolean result = false;
        logger.info("start");
        if (!userDao.existsByUsername(user.getUsername())) {
            user.setPwd(passwordEncoder.encode(user.getPwd()));
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
            User resultUser = userDao.save(user);
            if (resultUser != null) {
                    result = true;
            }

            if (result) {
                logger.info("adduser ok");
                return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
            } else {
                logger.error("technical error for username : " + user.getUsername());
                throw new CustomException("technical error", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            logger.error("Username is already use : " + user.getUsername());
            return null;

        }
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        logger.info("Start");
        User resultUser = null;
        boolean saveOk = false;

        resultUser = userDao.findByUsername(user.getUsername());

        if (resultUser!=null) { // username exist in DB

            user.setId(resultUser.getId());
            user.setPwd(passwordEncoder.encode(user.getPwd()));
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

            resultUser = userDao.save(user);
            logger.info("The user "+ user.getUsername() + " is updated");
        } else {
            resultUser = null;
            logger.info("The user "+ user.getUsername() + " does not exist");
        }
        logger.info("Finish");

        return resultUser;
    }

    @Override
    public User deleteUser(User user) {
        logger.info("Start");
        User resultUser = null;
        User userByUsername = userDao.findByUsername(user.getUsername());
        //Check correspondance between id and patient with
        if (userByUsername!=null) {

            userDao.delete(userByUsername);
            resultUser = user;
            logger.info("The user "+ user.getUsername() + " is deleted");
        } else {
            resultUser = null;
            logger.info("The user "+ user.getUsername() +  " is not found");

        }
        logger.info("Finish");
        return resultUser;
    }


}
