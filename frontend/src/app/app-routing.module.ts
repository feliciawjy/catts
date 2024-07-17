import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TodoListComponent } from './components/todo-list/todo-list.component';
import { TodoItemComponent } from './components/todo-item/todo-item.component';
import { StudyTopicComponent } from './components/study-topic/study-topic.component';
import { StudyTaskComponent } from './components/study-task/study-task.component';
import { ExpiryTrackerComponent } from './components/expiry-tracker/expiry-tracker.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { TodoViewComponent } from './components/todo-view/todo-view.component';
import { StudyViewComponent } from './components/study-view/study-view.component';
import { AuthGuard } from './components/guards/auth.guard';
import { ExpiryItemComponent } from './components/expiry-item/expiry-item.component';



const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  {
    path: 'tasks', 
    component: TodoViewComponent, 
    canActivate: [AuthGuard],
    children: [
      { path: 'list', component: TodoListComponent },
      { path: 'item', component: TodoItemComponent },
    ]
  },
  { 
    path: 'study', 
    component: StudyViewComponent, 
    canActivate: [AuthGuard],
    children: [
      { path: 'topic', component: StudyTopicComponent },
      { path: 'task', component: StudyTaskComponent },
    ] 
  },
  { path: 'expiry', component: ExpiryTrackerComponent, canActivate: [AuthGuard] },
  { path: 'expiry/add', component: ExpiryItemComponent },
  { path: 'calendar', component: CalendarComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }