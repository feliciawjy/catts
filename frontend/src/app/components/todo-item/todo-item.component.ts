import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TodoService } from '../../services/todo.service';
@Component({
  selector: 'app-todo-item',
  templateUrl: './todo-item.component.html',
  styleUrl: './todo-item.component.css'
})
export class TodoItemComponent {
  @Input() todoListId: number | null = null;
  @Output() onBack = new EventEmitter<void>();
  taskForm: FormGroup;

  constructor(private fb: FormBuilder, private todoService: TodoService) {
    this.taskForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      dueDate: ['']
    });
  }

  onSubmit() {
    if (this.taskForm.valid && this.todoListId) {
      this.todoService.createTask(this.todoListId, this.taskForm.value).subscribe(
        response => {
          console.log('Task created', response);
          this.onBack.emit();
        },
        error => console.error('Error creating task', error)
      );
    }
  }
}
