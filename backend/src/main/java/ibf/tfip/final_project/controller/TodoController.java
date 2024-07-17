package ibf.tfip.final_project.controller;

import ibf.tfip.final_project.model.TodoList;
import ibf.tfip.final_project.model.Task;
import ibf.tfip.final_project.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // todo list
    @GetMapping("/lists")
    public ResponseEntity<List<TodoList>> getTodoLists(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<TodoList> todoLists = todoService.getTodoListsForUser(userId);
        return ResponseEntity.ok(todoLists);
    }

    @PostMapping("/lists")
    public ResponseEntity<TodoList> createTodoList(@RequestBody TodoList todoList, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        TodoList createdList = todoService.createTodoList(userId, todoList.getName());
        return ResponseEntity.ok(createdList);
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<TodoList> updateTodoList(@PathVariable Long id, @RequestBody TodoList todoList) {
        todoList.setId(id);
        TodoList updatedList = todoService.updateTodoList(todoList);
        return updatedList != null ? ResponseEntity.ok(updatedList) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/lists/{id}")
    public ResponseEntity<Void> deleteTodoList(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodoList(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // tasks
    @GetMapping("/lists/{listId}/tasks")
    public ResponseEntity<List<Task>> getTasksForList(@PathVariable Long listId) {
        List<Task> tasks = todoService.getTasksForTodoList(listId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        try {
            Optional<Task> updatedTask = todoService.updateTask(task);
            return updatedTask.map(ResponseEntity::ok)
                              .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the task: " + e.getMessage());
        }
    }

    @PostMapping("/lists/{listId}/tasks")
    public ResponseEntity<?> createTask(@PathVariable Long listId, @RequestBody Task task) {
        try {
            Task createdTask = todoService.createTask(listId, task);
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while creating the task: " + e.getMessage());
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            boolean deleted = todoService.deleteTask(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the task: " + e.getMessage());
        }
    }
    private Long getUserIdFromAuthentication(Authentication authentication) {
        // auth
        return 1L;
    }

}