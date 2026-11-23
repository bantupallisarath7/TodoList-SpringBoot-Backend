package todoapp.service;

import org.springframework.stereotype.Service;
import todoapp.model.Todo;
import java.util.*;


public interface TodoService {

    Todo createTodo(Todo todo);

    List<Todo> getAllTodos();

    Todo getTodoById(Long id);

    Todo updateTodo(Long id, Todo todo);

    void deleteTodo(Long id);

}
