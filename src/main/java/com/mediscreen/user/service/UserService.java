package com.mediscreen.user.service;

import com.mediscreen.user.model.User;

public interface UserService {

    /**
     * identifier a user
     * @param user user to be identify
     * @return JWT tocken if user is identify
     */
    public String signin(User user);
    /**
     * create a new user
     * @param user user to be create
     * @return JWT tocken
     */
    public String addUser(User user);
    /**
     * update password for a user
     * @param user user to be update with new password
     * @return user updated
     */
    public User updateUser(User user);
    /**
     * delete a user
     * @param user user to be delete
     * @return deleted user
     */
    public User deleteUser(User user);
}
