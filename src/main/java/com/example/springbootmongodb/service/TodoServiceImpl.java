package com.example.springbootmongodb.service;

import com.example.springbootmongodb.exception.TodoCollectionException;
import com.example.springbootmongodb.model.TodoDTO;
import com.example.springbootmongodb.repository.TodoRepository;
import com.sun.xml.bind.v2.TODO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
   private TodoRepository todoRepository;
    @Override
    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException,TodoCollectionException {
      Optional<TodoDTO>  todoDTO1=todoRepository.findByTodo(todoDTO.getTodo());
      if (todoDTO1.isPresent()){

throw  new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());

      }else {
          todoDTO.setCreatedAt(new Date(System.currentTimeMillis()));
          todoRepository.save(todoDTO);
      }

    }

    @Override
    public List<TodoDTO> getAllTodos() {
      List<TodoDTO> todoDTOS=todoRepository.findAll();
      if (todoDTOS.size()>0){
          return todoDTOS;
      }
      else {
          return  new ArrayList<TodoDTO>();
      }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws  TodoCollectionException{
        Optional<TodoDTO> todoDTO = todoRepository.findById(id);
        if (todoDTO.isPresent()) {
         return todoDTO.get();
        } else {
           throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todoDTO) throws TodoCollectionException {
        Optional<TodoDTO> todoDTO1 =  todoRepository.findById(id);
        Optional<TodoDTO> todoDTO3 =    todoRepository.findById(todoDTO.getTodo());
        if (todoDTO1.isPresent()){
            if (todoDTO3.isPresent() && !todoDTO3.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }
           TodoDTO todoDTO2= todoDTO1.get();
            todoDTO2.setTodo(todoDTO.getTodo());
            todoDTO2.setDescription(todoDTO.getDescription());
            todoDTO2.setCompleted(true);
            todoDTO2.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoDTO2);
        }
        else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void delete(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoDTO= todoRepository.findById(id);
        if (todoDTO.isPresent()){
            todoRepository.deleteById(id);
        }else{
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }
}
