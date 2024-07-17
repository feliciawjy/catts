package ibf.tfip.final_project.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {
    private Long id;
    private Long userId;
    private String name;
    private LocalDateTime createdAt;

}
