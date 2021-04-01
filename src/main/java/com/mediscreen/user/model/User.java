package com.mediscreen.user.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="username",length=100,unique=true)
    @NotNull
    private String username;

    @Column(name="pwd",length=100)
    @NotNull
    private String pwd;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    public User() {

    }

    public User(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }

    public User(long id, String username, String pwd) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
