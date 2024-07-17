package ibf.tfip.final_project.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private Long todoListId;
    private String name;
    private String description;
    private LocalDate dueDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String googleCalendarEventId;
    private boolean isCompleted;
    private LocalDateTime createdAt;


}