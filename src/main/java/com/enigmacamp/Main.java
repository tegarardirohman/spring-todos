package com.enigmacamp;

import com.enigmacamp.beans.services.TodoServiceImpl;
import com.enigmacamp.beans.services.UserServiceImpl;
import com.enigmacamp.helper.Helper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        TodoServiceImpl todosService = applicationContext.getBean(TodoServiceImpl.class);
        UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);

        Helper helper = new Helper(applicationContext, todosService, userService);
        helper.start();

    }
}
