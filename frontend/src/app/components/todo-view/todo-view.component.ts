import { Component, OnInit } from '@angular/core';
import { TodoService } from '../../services/todo.service';

@Component({
  selector: 'app-todo-view',
  templateUrl: './todo-view.component.html',
  styleUrl: './todo-view.component.css'
})
export class TodoViewComponent implements OnInit {
  todoLists: any[] = [];
  showingAddListForm = false;
  showingAddTaskForm = false;
  selectedListId: number | null = null;

  constructor(private todoService: TodoService) {}

  ngOnInit() {
    this.loadTodoLists();
  }

  loadTodoLists() {
    this.todoService.getTodoLists().subscribe(
      lists => this.todoLists = lists,
      error => console.error('Error loading todo lists', error)
    );
  }

  showAddListForm() {
    this.showingAddListForm = true;
    this.showingAddTaskForm = false;
  }

  showAddTaskForm(listId: number) {
    this.selectedListId = listId;
    this.showingAddTaskForm = true;
    this.showingAddListForm = false;
  }

  hideForm() {
    this.showingAddListForm = false;
    this.showingAddTaskForm = false;
    this.selectedListId = null;
    this.loadTodoLists();
  }
}