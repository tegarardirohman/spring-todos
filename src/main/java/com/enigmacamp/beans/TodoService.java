package com.enigmacamp.beans;

import com.enigmacamp.entity.Todo;

import java.util.List;

public interface TodoService {
    void addTodo(Todo todo);
    void updateTodo(Todo todo);
    void toCompleted(int todoId);
    void deleteTodo(int todoId);
    List<Todo> findAll(int user_id, boolean status);

}
