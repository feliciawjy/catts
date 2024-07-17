import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private apiUrl = '/api/todo';

  constructor(private http: HttpClient) { }

  getCurrentEvent(): Observable<any> {
    return this.http.get(`${this.apiUrl}/current-event`);
  }

  getUpcomingEvents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/upcoming-events`);
  }

  getTodoLists(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/lists`);
  }

  createTodoList(todoList: { name: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/lists`, todoList);
  }

  getTasksForTodoList(todoListId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/lists/${todoListId}/tasks`);
  }

  createTask(todoListId: number, task: { name: string, description: string, dueDate: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/lists/${todoListId}/tasks`, task);
  }

  updateTask(taskId: number, task: { name: string, description: string, dueDate: string, isCompleted: boolean }): Observable<any> {
    return this.http.put(`${this.apiUrl}/tasks/${taskId}`, task);
  }

  deleteTask(taskId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/tasks/${taskId}`);
  }
}