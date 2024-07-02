package com.enigmacamp;

import com.enigmacamp.beans.TodoService;
import com.enigmacamp.beans.UserService;
import com.enigmacamp.beans.controllers.TodoController;
import com.enigmacamp.beans.controllers.UserController;
import com.enigmacamp.entity.Todo;
import com.enigmacamp.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class ApplicationConfiguration {

    @Bean
    TodoService todoService() {
        return new TodoController();
    }

    @Bean
    UserService userService() {
        return new UserController();
    }

    @Bean(name = "user")
    User user(UserController userController){
        return new User(userController);
    }

    @Bean(name = "todo")
    Todo todo(TodoController todoController) {
        return new Todo(todoController);
    }


}
