import { Component, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudyService } from '../../services/study.service';

@Component({
  selector: 'app-study-topic',
  templateUrl: './study-topic.component.html',
  styleUrl: './study-topic.component.css'
})
export class StudyTopicComponent {
  @Output() onBack = new EventEmitter<void>();
  studyTopicForm: FormGroup;

  constructor(private fb: FormBuilder, private studyService: StudyService) {
    this.studyTopicForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.studyTopicForm.valid) {
      this.studyService.createStudyTopic(this.studyTopicForm.value).subscribe(
        response => {
          console.log('Study topic created', response);
          this.onBack.emit();
        },
        error => console.error('Error creating study topic', error)
      );
    }
  }
}
