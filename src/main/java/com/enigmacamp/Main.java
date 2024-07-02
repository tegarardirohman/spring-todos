package com.enigmacamp;

import com.enigmacamp.beans.controllers.TodoController;
import com.enigmacamp.beans.controllers.UserController;
import com.enigmacamp.helper.Helper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        TodoController todosService = applicationContext.getBean(TodoController.class);
        UserController userService = applicationContext.getBean(UserController.class);

        Helper helper = new Helper(applicationContext, todosService, userService);
        helper.start();

    }
}
