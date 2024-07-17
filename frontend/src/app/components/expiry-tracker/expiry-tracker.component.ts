import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ExpiryItemService } from '../../services/expiry-item.service';

@Component({
  selector: 'app-expiry-tracker',
  templateUrl: './expiry-tracker.component.html',
  styleUrl: './expiry-tracker.component.css'
})

export class ExpiryTrackerComponent implements OnInit {
  expiryItems: any[] = [];

  constructor(private expiryItemService: ExpiryItemService, private router: Router) {}

  ngOnInit() {
    this.loadExpiryItems();
  }

  loadExpiryItems() {
    this.expiryItemService.getExpiryItems().subscribe(
      items => this.expiryItems = items,
      error => console.error('Error loading expiry items', error)
    );
  }

  addExpiryItem() {
    this.router.navigate(['/expiry/add']);
  }
}
