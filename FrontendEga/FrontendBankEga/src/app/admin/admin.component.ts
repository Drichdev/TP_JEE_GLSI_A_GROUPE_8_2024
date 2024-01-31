import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [HttpClientModule,CommonModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})

export class AdminComponent {
  // clients: Array<any> = []; 

  // constructor(private http: HttpClient) {}

  // ngOnInit(): void {
  //   this.http.get<Array<any>>("http://localhost:8085/clients")
  //   .subscribe({
  //     next : data => {
  //     this.clients = data
  //     },
  //     error: err =>{
  //     console.log(err);
  //     }
  //     })
  // }
}