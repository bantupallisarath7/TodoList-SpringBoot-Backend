package todoapp.service;

import org.springframework.stereotype.Service;
import todoapp.exception.ResourceNotFoundException;
import todoapp.model.Todo;
import todoapp.repository.TodoRepository;

import java.util.List;

@Service
public class TodoServiceImp implements TodoService {

    private final TodoRepository todoRepository;

    // Constructor Injection
    public TodoServiceImp(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo createTodo(Todo todo) {
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Todo not found with id: " + id));
    }

    @Override
    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existedTodo = getTodoById(id);

        if (existedTodo == null) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }

        existedTodo.setTitle(updatedTodo.getTitle());
        existedTodo.setDescription(updatedTodo.getDescription());
        existedTodo.setCompleted(updatedTodo.isCompleted());
        return todoRepository.save(existedTodo);
    }

    @Override
    public void deleteTodo(Long id) {

        Todo existedTodo = getTodoById(id);
        if(existedTodo == null){
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.delete(existedTodo);
    }
}
