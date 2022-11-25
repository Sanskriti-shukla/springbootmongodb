package com.example.springbootmongodb.service;

import com.example.springbootmongodb.exception.TodoCollectionException;
import com.example.springbootmongodb.model.TodoDTO;
import org.apache.catalina.LifecycleState;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {
    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException,TodoCollectionException;
public List<TodoDTO> getAllTodos();
public TodoDTO getSingleTodo(String id) throws  TodoCollectionException;
public void updateTodo(String id,TodoDTO todoDTO) throws TodoCollectionException;
public  void delete(String id) throws TodoCollectionException;

}
