package com.mediscreen.user.service;

import com.mediscreen.user.model.User;

public interface UserService {
    /*public User findByUser(String username);*/
    /*public boolean checkUser (User user);*/
    public String signin(User user);
    public String addUser(User user);
    public User updateUser(User user);
    public User deleteUser(User user);
}
