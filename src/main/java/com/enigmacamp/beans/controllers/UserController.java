package com.enigmacamp.beans.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.enigmacamp.beans.UserService;
import com.enigmacamp.config.DBConfig;
import com.enigmacamp.entity.User;
import com.enigmacamp.helper.Helper;
import org.springframework.stereotype.Component;

@Component
public class UserController implements UserService {

    private void setUser(User user) {
        Helper.setUser(user);
    }

    private String byCrypt(String str) {
        return BCrypt.withDefaults().hashToString(12, str.toCharArray());
    }

    public boolean login(String username, String password) {

        try {
            User user = DBConfig.em().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (user != null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
                setUser(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("username or password incorrect");
            return false;
        }

    }

    public boolean signUp(String username, String password, String firstName, String lastName) {

        User user = new User(firstName, lastName, username, byCrypt(password));

        try {
            DBConfig.em().getTransaction().begin();
            DBConfig.em().persist(user);
            DBConfig.em().getTransaction().commit();
            this.setUser(user);

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            DBConfig.em().getTransaction().rollback();
            return false;
        }
    }
}
