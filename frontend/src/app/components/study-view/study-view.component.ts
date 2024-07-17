import { Component, OnInit } from '@angular/core';
import { StudyService } from '../../services/study.service';

@Component({
  selector: 'app-study-view',
  templateUrl: './study-view.component.html',
  styleUrl: './study-view.component.css'
})
export class StudyViewComponent implements OnInit {
  studyTopics: any[] = [];
  showingAddTopicForm = false;
  showingAddTaskForm = false;
  selectedTopicId: number | null = null;

  constructor(private studyService: StudyService) {}

  ngOnInit() {
    this.loadStudyTopics();
  }

  loadStudyTopics() {
    this.studyService.getStudyTopics().subscribe(
      topics => this.studyTopics = topics,
      error => console.error('Error loading study topics', error)
    );
  }

  showAddTopicForm() {
    this.showingAddTopicForm = true;
    this.showingAddTaskForm = false;
  }

  showAddTaskForm(topicId: number) {
    this.selectedTopicId = topicId;
    this.showingAddTaskForm = true;
    this.showingAddTopicForm = false;
  }

  hideForm() {
    this.showingAddTopicForm = false;
    this.showingAddTaskForm = false;
    this.selectedTopicId = null;
    this.loadStudyTopics();
  }
}
