package ibf.tfip.final_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyTopic {
    private Long id;
    private Long userId;
    private String name;
    private LocalDateTime createdAt;
}