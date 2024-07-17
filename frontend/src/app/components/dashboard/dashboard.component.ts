import { Component, OnInit } from '@angular/core';
import { TodoService } from '../../services/todo.service';
import { ExpiryItemService } from '../../services/expiry-item.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  currentTime: Date = new Date();
  currentEvent: any = null;
  upcomingEvents: any[] = [];
  expiringItems: any[] = [];

  constructor(
    private todoService: TodoService,
    private expiryItemService: ExpiryItemService
  ) {}

  ngOnInit() {
    this.updateTime();
    this.loadCurrentEvent();
    this.loadUpcomingEvents();
    this.loadExpiringItems();

    setInterval(() => this.updateTime(), 1000);
  }

  updateTime() {
    this.currentTime = new Date();
  }

  loadCurrentEvent() {
    this.todoService.getCurrentEvent().subscribe(
      event => this.currentEvent = event,
      error => console.error('Error loading current event', error)
    );
  }

  loadUpcomingEvents() {
    this.todoService.getUpcomingEvents().subscribe(
      events => this.upcomingEvents = events,
      error => console.error('Error loading upcoming events', error)
    );
  }

  loadExpiringItems() {
    this.expiryItemService.getExpiringItems().subscribe(
      items => this.expiringItems = items,
      error => console.error('Error loading expiring items', error)
    );
  }
}
