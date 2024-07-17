package ibf.tfip.final_project.controller;

import ibf.tfip.final_project.model.StudyTopic;
import ibf.tfip.final_project.model.StudyTask;
import ibf.tfip.final_project.service.StudyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/study")
public class StudyController {
    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    // Study Topic endpoints
    @GetMapping("/topics")
    public ResponseEntity<List<StudyTopic>> getStudyTopics(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<StudyTopic> studyTopics = studyService.getStudyTopicsForUser(userId);
        return ResponseEntity.ok(studyTopics);
    }

    @PostMapping("/topics")
    public ResponseEntity<StudyTopic> createStudyTopic(@RequestBody StudyTopic studyTopic, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        StudyTopic createdTopic = studyService.createStudyTopic(userId, studyTopic.getName());
        return ResponseEntity.ok(createdTopic);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<StudyTopic> updateStudyTopic(@PathVariable Long id, @RequestBody StudyTopic studyTopic) {
        studyTopic.setId(id);
        StudyTopic updatedTopic = studyService.updateStudyTopic(studyTopic);
        return updatedTopic != null ? ResponseEntity.ok(updatedTopic) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteStudyTopic(@PathVariable Long id) {
        boolean deleted = studyService.deleteStudyTopic(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Study Task endpoints
    @GetMapping("/topics/{topicId}/tasks")
    public ResponseEntity<List<StudyTask>> getStudyTasksForTopic(@PathVariable Long topicId) {
        List<StudyTask> tasks = studyService.getStudyTasksForTopic(topicId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/topics/{topicId}/tasks")
    public ResponseEntity<StudyTask> createStudyTask(@PathVariable Long topicId, @RequestBody StudyTask task) {
        StudyTask createdTask = studyService.createStudyTask(topicId, task);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<StudyTask> updateStudyTask(@PathVariable Long id, @RequestBody StudyTask task) {
        task.setId(id);
        Optional<StudyTask> updatedTask = studyService.updateStudyTask(task);
        return updatedTask.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteStudyTask(@PathVariable Long id) {
        boolean deleted = studyService.deleteStudyTask(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/tasks/{id}/complete")
    public ResponseEntity<StudyTask> markStudyTaskAsCompleted(@PathVariable Long id) {
        Optional<StudyTask> completedTask = studyService.markStudyTaskAsCompleted(id);
        return completedTask.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        // auth
        return 1L;
    }
}