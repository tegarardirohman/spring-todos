package com.enigmacamp.beans;

public interface UserService {
    boolean login(String username, String password);
    boolean signUp(String username, String password, String firstName, String lastName);
}
