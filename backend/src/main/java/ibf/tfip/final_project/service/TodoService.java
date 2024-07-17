package ibf.tfip.final_project.service;

import ibf.tfip.final_project.model.TodoList;
import ibf.tfip.final_project.model.Task;
import ibf.tfip.final_project.repository.TodoListRepository;
import ibf.tfip.final_project.repository.TaskRepository;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoListRepository todoListRepo;
    private final TaskRepository taskRepo;
    // private final GoogleCalendarService googleCalendarService;

    // public TodoService(TodoListRepository todoListRepo, TaskRepository taskRepo, GoogleCalendarService googleCalendarService) {
    //     this.todoListRepo = todoListRepo;
    //     this.taskRepo = taskRepo;
    //     this.googleCalendarService = googleCalendarService;
    // }

    public TodoService(TodoListRepository todoListRepo, TaskRepository taskRepo) {
        this.todoListRepo = todoListRepo;
        this.taskRepo = taskRepo;
    }

    // todo list
    public List<TodoList> getTodoListsForUser(Long userId) {
        return todoListRepo.findAllByUserId(userId);
    }

    public List<Task> getTasksForTodoList(Long todoListId) {
        return taskRepo.findAllByTodoListId(todoListId);
    }

    public TodoList createTodoList(Long userId, String name) {
        TodoList todoList = new TodoList();
        todoList.setUserId(userId);
        todoList.setName(name);
        todoList.setCreatedAt(LocalDateTime.now());
        return todoListRepo.create(todoList);
    }

    public TodoList updateTodoList(TodoList todoList) {
        return todoListRepo.update(todoList);
    }

    public boolean deleteTodoList(Long id) {
        return todoListRepo.delete(id);
    }

    // task
    public Task createTask(Long todoListId, Task task) throws IOException {
        task.setTodoListId(todoListId);
        task.setCreatedAt(LocalDateTime.now());
        Task createdTask = taskRepo.create(task);

        // // Create Google Calendar event
        // String eventId = googleCalendarService.createEvent(
        //     task.getName(),
        //     task.getDescription(),
        //     new DateTime(task.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()),
        //     new DateTime(task.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        // );
        // createdTask.setGoogleCalendarEventId(eventId);
        // return taskRepo.update(createdTask);

        return createdTask;

    }

    public Optional<Task> updateTask(Task task) throws IOException {
        // Task updatedTask = taskRepo.update(task);
        
        // // Update Google Calendar event
        // googleCalendarService.updateEvent(
        //     task.getGoogleCalendarEventId(),
        //     task.getName(),
        //     task.getDescription(),
        //     new DateTime(task.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()),
        //     new DateTime(task.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        // );
        
        return taskRepo.update(task);
    }

    public boolean deleteTask(Long id) throws IOException {
        // Task task = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        // googleCalendarService.deleteEvent(task.getGoogleCalendarEventId());
        return taskRepo.delete(id);
    }
}