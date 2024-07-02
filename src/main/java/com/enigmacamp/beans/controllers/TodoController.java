package com.enigmacamp.beans.controllers;

import com.enigmacamp.config.DBConfig;
import com.enigmacamp.entity.Todo;
import com.enigmacamp.beans.TodoService;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TodoController implements TodoService {

    public List<Todo> findAll(int id, boolean status) {
        TypedQuery<Todo> users = DBConfig.em().createQuery("SELECT t from Todo t WHERE t.user_id = :user_id ORDER BY t.id", Todo.class);
        users.setParameter("user_id", id);
        List<Todo> todoList = users.getResultStream().filter(todo -> todo.isCompleted().equals(status)).toList();

        if(todoList.isEmpty()){
            System.out.println("Todo list is empty");
            return null;
        } else {
            return todoList;
        }
    }

    @Override
    public void addTodo(Todo todo) {

        try {
            DBConfig.em().getTransaction().begin();
            DBConfig.em().persist(todo);
            DBConfig.em().getTransaction().commit();
            System.out.println("Todo saved");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            DBConfig.em().getTransaction().rollback();
        }
    }

    public void updateTodo(Todo todo) {

        try {
            DBConfig.em().getTransaction().begin();
            Todo updateTodo = DBConfig.em().find(Todo.class, todo.getId()); // managed

            updateTodo.setTitle(todo.getTitle());
            updateTodo.setDeadLine(todo.getDeadLine());
            updateTodo.setPriority(todo.getPriority());
            updateTodo.setCompleted(todo.isCompleted());
            DBConfig.em().getTransaction().commit();

            System.out.println("Todo updated");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTodo(int todoId) {

        try {
            DBConfig.em().getTransaction().begin();
            Todo todo = DBConfig.em().find(Todo.class, todoId);
            DBConfig.em().remove(todo);
            DBConfig.em().getTransaction().commit();
            System.out.println("Todo deleted with id: " + todoId);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void toCompleted(int todoid) {

        try {
            DBConfig.em().getTransaction().begin();
            Todo todo = DBConfig.em().find(Todo.class, todoid);
            todo.setCompleted(true);
            DBConfig.em().getTransaction().commit();
            System.out.println("Todo set to Completed");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
