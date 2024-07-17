import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ExpiryItemService } from '../../services/expiry-item.service';
import { WebcamImage, WebcamInitError, WebcamUtil } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-expiry-item',
  templateUrl: './expiry-item.component.html',
  styleUrl: './expiry-item.component.css'
})
export class ExpiryItemComponent implements OnInit {
  expiryItemForm: FormGroup;
  webcamImage: WebcamImage | null = null;
  private trigger: Subject<void> = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private expiryItemService: ExpiryItemService,
    private router: Router
  ) {
    this.expiryItemForm = this.fb.group({
      itemName: ['', Validators.required],
      expiryDate: ['', Validators.required]
    });
  }

  ngOnInit() {}

  triggerSnapshot(): void {
    this.trigger.next();
  }

  handleImage(webcamImage: WebcamImage): void {
    this.webcamImage = webcamImage;
  }

  retakePhoto(): void {
    this.webcamImage = null;
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  onSubmit() {
    if (this.expiryItemForm.valid && this.webcamImage) {
      const formData = new FormData();
      formData.append('itemName', this.expiryItemForm.get('itemName')?.value);
      formData.append('expiryDate', this.expiryItemForm.get('expiryDate')?.value.toISOString());
      
      const blob = this.dataURItoBlob(this.webcamImage.imageAsDataUrl);
      formData.append('image', blob, 'image.jpg');

      this.expiryItemService.addExpiryItem(formData).subscribe(
        response => {
          console.log('Expiry item added', response);
          this.router.navigate(['/expiry']);
        },
        error => console.error('Error adding expiry item', error)
      );
    }
  }

  private dataURItoBlob(dataURI: string): Blob {
    const byteString = atob(dataURI.split(',')[1]);
    const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: mimeString });
  }
}
