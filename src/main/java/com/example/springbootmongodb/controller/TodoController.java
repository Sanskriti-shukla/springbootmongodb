package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.exception.TodoCollectionException;
import com.example.springbootmongodb.model.TodoDTO;
import com.example.springbootmongodb.repository.TodoRepository;
import com.example.springbootmongodb.service.TodoService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
      List<TodoDTO> todos= todoService.getAllTodos();
      return new ResponseEntity<>(todos, todos.size()> 0 ? HttpStatus.OK: HttpStatus.NOT_FOUND );
    }
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO){
        try {
            todoService.createTodo(todoDTO);
            return  new ResponseEntity<TodoDTO>(todoDTO,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TodoCollectionException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/todos/{id}")

    public ResponseEntity<?> getSingleTodo(@PathVariable ("id") String id) {
        try{
       return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK) ;
    }catch (Exception e){
  return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable ("id") String id,@RequestBody TodoDTO todoDTO){
   try{
       todoService.updateTodo(id,todoDTO);
       return new ResponseEntity<>("update todo with id"+" "+id,      HttpStatus.OK);
   }catch ( ConstraintViolationException e){
       return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

   }catch (TodoCollectionException e){


       return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
   }


}
@DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable ("id") String id){
  try {
        todoService.delete(id);
        return new ResponseEntity<>("successfully deleted with id"+" "+id, HttpStatus.OK);
    }
    catch (TodoCollectionException e){
        return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    }

}

