import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudyService {
  private apiUrl = '/api/study';

  constructor(private http: HttpClient) { }

  getStudyTopics(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/topics`);
  }

  createStudyTopic(topic: { name: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/topics`, topic);
  }

  updateStudyTopic(topicId: number, topic: { name: string }): Observable<any> {
    return this.http.put(`${this.apiUrl}/topics/${topicId}`, topic);
  }

  deleteStudyTopic(topicId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/topics/${topicId}`);
  }

  getStudyTasks(topicId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/topics/${topicId}/tasks`);
  }

  createStudyTask(topicId: number, task: { name: string, description: string, dueDate: Date }): Observable<any> {
    return this.http.post(`${this.apiUrl}/topics/${topicId}/tasks`, task);
  }

  updateStudyTask(taskId: number, task: { name: string, description: string, dueDate: Date }): Observable<any> {
    return this.http.put(`${this.apiUrl}/tasks/${taskId}`, task);
  }

  deleteStudyTask(taskId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/tasks/${taskId}`);
  }
}