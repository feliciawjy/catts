import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StudyService } from '../../services/study.service';

@Component({
  selector: 'app-study-task',
  templateUrl: './study-task.component.html',
  styleUrl: './study-task.component.css'
})
export class StudyTaskComponent {
  @Input() topicId: number | null = null;
  @Output() onBack = new EventEmitter<void>();
  taskForm: FormGroup;

  constructor(private fb: FormBuilder, private studyService: StudyService) {
    this.taskForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      dueDate: [null]
    });
  }

  onSubmit() {
    if (this.taskForm.valid && this.topicId) {
      this.studyService.createStudyTask(this.topicId, this.taskForm.value).subscribe(
        response => {
          console.log('Study task created', response);
          this.onBack.emit();
        },
        error => console.error('Error creating study task', error)
      );
    }
  }
}
