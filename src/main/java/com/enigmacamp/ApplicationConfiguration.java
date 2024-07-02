package com.enigmacamp;

import com.enigmacamp.beans.TodoService;
import com.enigmacamp.beans.UserService;
import com.enigmacamp.beans.services.TodoServiceImpl;
import com.enigmacamp.beans.services.UserServiceImpl;
import com.enigmacamp.entity.Todo;
import com.enigmacamp.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
//@ComponentScan(basePackageClasses = UserServiceImpl.class)
public class ApplicationConfiguration {

    @Bean
    TodoService todoService() {
        return new TodoServiceImpl();
    }

    @Bean
    UserService userService() {
        return new UserServiceImpl();
    }

    @Bean(name = "user")
    User user(UserServiceImpl userController){
        return new User(userController);
    }

    @Bean(name = "todo")
    Todo todo(TodoServiceImpl todoController) {
        return new Todo(todoController);
    }


}
