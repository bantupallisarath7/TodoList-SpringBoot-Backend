package todoapp.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import todoapp.dto.request.TodoCreateRequest;
import todoapp.dto.request.TodoPatchRequest;
import todoapp.dto.request.TodoUpdateRequest;
import todoapp.dto.response.TodoResponse;
import todoapp.model.Todo;
import todoapp.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
//@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {

    private final TodoService todoService;

    // Constructor Injection
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    // Convert Entity to Response DTO
    private TodoResponse convertToResponse(Todo todo){
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted()
        );
    }

    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoCreateRequest request){

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(false);

        Todo savedTodo = todoService.createTodo(todo);

        return convertToResponse(savedTodo);
    }

    @GetMapping
    public List<TodoResponse> getAllTodos(){
        return todoService.getAllTodos()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TodoResponse getTodoById(@PathVariable Long id){
        Todo todo = todoService.getTodoById(id);
        return convertToResponse(todo);
    }

    @PutMapping("/{id}")
    public TodoResponse updateTodo(@PathVariable Long id,@Valid @RequestBody TodoUpdateRequest request){
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());

        Todo updatedTodo = todoService.updateTodo(id,todo);
        return convertToResponse(updatedTodo);
    }

    @PatchMapping("/{id}")
    public TodoResponse patchTodo(@PathVariable Long id, @Valid @RequestBody TodoPatchRequest request){
        Todo existingTodo = todoService.getTodoById(id);

        if(request.getTitle() != null){
            if (request.getTitle().isBlank()) {
                throw new IllegalArgumentException(
                        "Title cannot be blank"
                );
            }
            existingTodo.setTitle(request.getTitle());
        }

        if(request.getDescription() != null){
            existingTodo.setDescription(request.getDescription());
        }

        if(request.getCompleted() != null){
            existingTodo.setCompleted(request.getCompleted());
        }

        Todo updatedTodo = todoService.updateTodo(id,existingTodo);
        return convertToResponse(updatedTodo);

    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return "Todo deleted Successfully";
    }
}
