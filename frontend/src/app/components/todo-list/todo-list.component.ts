import { Component, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TodoService } from '../../services/todo.service';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrl: './todo-list.component.css'
})
export class TodoListComponent {
  @Output() onBack = new EventEmitter<void>();
  todoListForm: FormGroup;

  constructor(private fb: FormBuilder, private todoService: TodoService) {
    this.todoListForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.todoListForm.valid) {
      this.todoService.createTodoList(this.todoListForm.value).subscribe(
        response => {
          console.log('Todo list created', response);
          this.onBack.emit();
        },
        error => console.error('Error creating todo list', error)
      );
    }
  }
}
