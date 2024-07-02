package com.enigmacamp.helper;


import com.enigmacamp.beans.services.TodoServiceImpl;
import com.enigmacamp.beans.services.UserServiceImpl;
import com.enigmacamp.entity.Todo;
import com.enigmacamp.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Helper {
    static Scanner scanner = new Scanner(System.in);
    static User user;
    AnnotationConfigApplicationContext applicationContext;
    TodoServiceImpl todosService;
    UserServiceImpl userService;


    public Helper(AnnotationConfigApplicationContext applicationContext, TodoServiceImpl todosService, UserServiceImpl userService) {
        this.applicationContext = applicationContext;
        this.todosService = todosService;
        this.userService = userService;
    }


    static List<Todo> todos = new ArrayList<>();

    public static void header(String title) {
        System.out.println("=============================================================");
        System.out.println(title);
        System.out.println("-------------------------------------------------------------");
    }

    public static String inputText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void setUser(User user) {
        Helper.user = user;
    }

    public void login() {
        String username;
        String password;

        header("Login to My TODOS");

        do {
            username = inputText("Username: ");
        } while (username == null || username.isEmpty());

        do {
            password = inputText("Password: ");
        } while (password == null || password.isEmpty());

        if (userService.login(username, password)) {
            System.out.println("Login Successful");
            // if success
            menu();

        } else {
            System.out.println("Invalid username or password");
            if(inputText("SignUp? (Y/N): ").equalsIgnoreCase("y")) {
                signUp();
            } else {
                login();
            }
        }

    }

    public void signUp() {
        String username;
        String password;
        String firstName;
        String lastName;

        header("Create new Account");

        do {
            firstName = inputText("First Name: ");
        } while (firstName== null || firstName.isEmpty());

        do {
            lastName = inputText("Last Name: ");
        } while (lastName== null || lastName.isEmpty());

        do {
            username = inputText("Username: ");
        } while (username == null || username.isEmpty());

        do {
            password = inputText("Password: ");
        } while (password == null || password.isEmpty());

        if (userService.signUp(username, password, firstName, lastName)) {
            System.out.println("Sign Up Successful");
            // if success
            menu();

        } else {
            System.out.println("Invalid username or password");
            if(inputText("Login? (Y/N): ").equalsIgnoreCase("y")) {
                login();
            } else {
                signUp();
            }
        }
    }

    void getTodo(boolean status) {

        try {
            todos = todosService.findAll(user.getId(), status);
            todos.forEach(System.out::println);

        } catch (NullPointerException e) {
            System.out.println("No todos found");
        }

    }

    public void menu() {
        String option;

        do {
            header(user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase() +"'s TODOS");
            this.getTodo(false);

            System.out.println("_______________");
            System.out.println("MENU");
            System.out.println("________________");
            System.out.println("1. Add Todo");
            System.out.println("2. Set to Completed");
            System.out.println("3. History");
            System.out.println("4. Update Todo");
            System.out.println("5. Delete Todo");
            System.out.println("0. Log Out");
            option = inputText("Choose an option: ");
            switch (option) {
                case "1":
                    this.addTodo();
                    break;
                case "2":
                    this.toCompleted(getIdTodo());
                    break;
                case "3":
                    this.getTodo(true);
                    break;
                case "4":
                    this.updateTodo(getIdTodo());
                    break;
                case "5":
                    this.deleteTodo(getIdTodo());
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option");
            }
        } while (true);

    }

    public void start() {
        String choice;

        do {
            header("Login or Sign Up");
            System.out.println("________________");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            choice = inputText("Enter your choice: ");

            switch (choice) {
                case "1":
                    this.login();
                    break;
                case "2":
                    signUp();
                    break;
                case "3":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (true);

    }

    public void addTodo() {
        header("Add Todo");

        String title, deadline, priority;
        LocalDate deadLine = null;
        int user_id = user.getId();

        do {
            title = inputText("Title: ");
        } while (title == null || title.isEmpty());

        do {
            deadline = inputText("Deadline (YYYY-MM-DD): ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                java.util.Date date = dateFormat.parse(deadline);
                // convert java.util.Date ke java.time.LocalDate
                deadLine = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (deadLine == null);

        do {
            System.out.println("Priority");
            System.out.println("________________");
            System.out.println("1. Low");
            System.out.println("2. Medium");
            System.out.println("3. High");
            priority = inputText("Priority: ");

        } while (Integer.parseInt(priority) < 1 || Integer.parseInt(priority) > 3);

        switch (priority) {
            case "1" -> priority = "Low";
            case "2" -> priority = "Medium";
            case "3" -> priority = "High";
        }

        todosService.addTodo(new Todo(title, priority, deadLine, user_id, false));
    }

    void toCompleted(int todoId) {
        todosService.toCompleted(todoId);
    }

    static int getIdTodo() {
        System.out.println();
        String id;
        do {
            id = inputText("Enter ID: ");

        } while (Integer.parseInt(id) <= 0);
        return Integer.parseInt(id);
    }

    void updateTodo(int todoId) {
        header("Update TODO");

        String title, deadline, priority, completed;
        LocalDate deadLine = null;
        boolean completedBool = false;
        int user_id = user.getId();

        do {
            title = inputText("New Title: ");
        } while (title == null || title.isEmpty());

        do {
            deadline = inputText("New Deadline (YYYY-MM-DD): ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                java.util.Date date = dateFormat.parse(deadline);
                // convert java.util.Date ke java.time.LocalDate
                deadLine = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (deadLine == null);

        do {
            System.out.println("Priority");
            System.out.println("________________");
            System.out.println("1. Low");
            System.out.println("2. Medium");
            System.out.println("3. High");
            priority = inputText("New Priority: ");

        } while (Integer.parseInt(priority) < 1 || Integer.parseInt(priority) > 3);

        switch (priority) {
            case "1" -> priority = "Low";
            case "2" -> priority = "Medium";
            case "3" -> priority = "High";
        }

        do {
            completed = inputText("Completed (Y/N)? : ");
        } while (!completed.equalsIgnoreCase("y") && !completed.equalsIgnoreCase("n"));

        if (completed.equalsIgnoreCase("y")) {
            completedBool = true;
        }
        todosService.updateTodo(new Todo(todoId, title, priority, deadLine, user_id, completedBool));
    }

    void deleteTodo(int todoId) {
        header("Delete TODO");

        todosService.deleteTodo(todoId);
    }
}

