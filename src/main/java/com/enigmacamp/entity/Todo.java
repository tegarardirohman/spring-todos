package com.enigmacamp.entity;

import com.enigmacamp.beans.TodoService;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String priority;
    private LocalDate deadLine;
    private int user_id;
    private Boolean completed;

    @Transient
    private TodoService todoService;
    public Todo(TodoService todoService) {
        this.todoService = todoService;
    }

    public Todo(String title, String priority, LocalDate deadLine, int user_id, boolean completed) {
        this.title = title;
        this.priority = priority;
        this.deadLine = deadLine;
        this.user_id = user_id;
        this.completed = completed;
    }

    public Todo(int id, String title, String priority, LocalDate deadLine, int user_id, Boolean completed) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.deadLine = deadLine;
        this.user_id = user_id;
        this.completed = completed;
    }

    public Todo() {

    }


    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";


    @Override
    public String toString() {
        String format;
        if (priority.equals("High")) {
            format = ANSI_RED + priority + ANSI_RESET;
        } else if (priority.equals("Medium")) {
            format = ANSI_YELLOW + priority + ANSI_RESET;
        } else {
            format = ANSI_GREEN + priority + ANSI_RESET;
        }
        return "id: " + id + ", Title: " + title + ", Priority: " + format + ", Deadline: " + deadLine.toString();
    }
}

