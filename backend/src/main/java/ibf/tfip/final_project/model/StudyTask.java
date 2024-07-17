package ibf.tfip.final_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyTask {
    private Long id;
    private Long studyTopicId;
    private String name;
    private String description;
    private LocalDate dueDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String googleCalendarEventId;
    private boolean completed;
    private int revisionCount;
    private LocalDateTime createdAt;
}