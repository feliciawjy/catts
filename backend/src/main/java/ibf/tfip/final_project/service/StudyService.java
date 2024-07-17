package ibf.tfip.final_project.service;

import ibf.tfip.final_project.model.StudyTopic;
import ibf.tfip.final_project.model.StudyTask;
import ibf.tfip.final_project.repository.StudyTopicRepository;
import ibf.tfip.final_project.repository.StudyTaskRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudyService {
    private final StudyTopicRepository studyTopicRepo;
    private final StudyTaskRepository studyTaskRepo;

    public StudyService(StudyTopicRepository studyTopicRepo, StudyTaskRepository studyTaskRepo) {
        this.studyTopicRepo = studyTopicRepo;
        this.studyTaskRepo = studyTaskRepo;
    }

    // Study Topic methods
    public List<StudyTopic> getStudyTopicsForUser(Long userId) {
        return studyTopicRepo.findAllByUserId(userId);
    }

    public StudyTopic createStudyTopic(Long userId, String name) {
        StudyTopic studyTopic = new StudyTopic();
        studyTopic.setUserId(userId);
        studyTopic.setName(name);
        studyTopic.setCreatedAt(LocalDateTime.now());
        return studyTopicRepo.create(studyTopic);
    }

    public StudyTopic updateStudyTopic(StudyTopic studyTopic) {
        return studyTopicRepo.update(studyTopic);
    }

    public boolean deleteStudyTopic(Long id) {
        return studyTopicRepo.delete(id);
    }

    // Study Task methods
    public List<StudyTask> getStudyTasksForTopic(Long topicId) {
        return studyTaskRepo.findAllByStudyTopicId(topicId);
    }

    public StudyTask createStudyTask(Long topicId, StudyTask task) {
        task.setStudyTopicId(topicId);
        task.setCreatedAt(LocalDateTime.now());
        task.setRevisionCount(0);
        task.setCompleted(false);
        return studyTaskRepo.create(task);
    }

    public Optional<StudyTask> updateStudyTask(StudyTask task) {
        return studyTaskRepo.update(task);
    }

    public boolean deleteStudyTask(Long id) {
        return studyTaskRepo.delete(id);
    }

    public Optional<StudyTask> markStudyTaskAsCompleted(Long taskId) {
        Optional<StudyTask> optionalTask = studyTaskRepo.findById(taskId);
        if (optionalTask.isPresent()) {
            StudyTask task = optionalTask.get();
            task.setCompleted(true);

            if (task.getRevisionCount() < 3) {
                task.setRevisionCount(task.getRevisionCount() + 1);
                task.setCompleted(false);

                LocalDateTime now = LocalDateTime.now();
                Duration taskDuration = Duration.between(task.getStartTime(), task.getEndTime());

                LocalDateTime newStartTime;
                LocalDate newDueDate;

                switch (task.getRevisionCount()) {
                    case 1:

                        newStartTime = now.plusDays(1);
                        newDueDate = newStartTime.toLocalDate();
                        task.setDueDate(newDueDate);
                        task.setStartTime(newStartTime);
                        task.setEndTime(newStartTime.plus(taskDuration));
                        break;
                    case 2:
                        newStartTime = now.plusWeeks(1);
                        newDueDate = newStartTime.toLocalDate();
                        task.setDueDate(newDueDate);
                        task.setStartTime(newStartTime);
                        task.setEndTime(newStartTime.plus(taskDuration));
                        break;
                    case 3:
                        newStartTime = now.plusMonths(1);
                        newDueDate = newStartTime.toLocalDate();
                        task.setDueDate(newDueDate);
                        task.setStartTime(newStartTime);
                        task.setEndTime(newStartTime.plus(taskDuration));
                        break;
                }

            }

            return studyTaskRepo.update(task);
        }
        return Optional.empty();
    }
}