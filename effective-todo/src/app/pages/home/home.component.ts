import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth.service'; 

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  username: string = "";
  password: string = "";

  signup_username: string = "";
  signup_password: string = "";
  signup_password_confirm: string = "";

  @ViewChild('loginForm') loginForm!: NgForm;
  @ViewChild('signupForm') signupForm!: NgForm;   
  constructor( private authService: AuthService, private router: Router) {}

  ngAfterViewInit(): void {
    this.setupEventListeners();
  }

  private setupEventListeners(): void {
    const switchers: NodeListOf<HTMLElement> = document.querySelectorAll('.switcher');

    switchers.forEach((item: HTMLElement) => {
      item.addEventListener('click', function(this: HTMLElement) {
        switchers.forEach((innerItem: HTMLElement) => 
          innerItem.parentElement?.classList.remove('is-active')
        );
        this.parentElement?.classList.add('is-active');
      });
    });
  }

  login(): void {
    // alert(this.username + this.password );
    if (!this.loginForm.valid) {
      alert('Please check your input and try again.');
      return;
    }

    this.authService.login(this.username, this.password)
    .subscribe({
      next: (response) => {
        console.log('Login successful', response);
        localStorage.setItem('token', response.jwtToken);
        localStorage.setItem('user_id', response.user_id);
        localStorage.setItem('username', response.username);
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Login failed', error);
        if(error.error){
          alert(error.error);
        }
        alert('Login failed. Please try again.');
        window.location.reload();
      }
    });
  }

  signup(): void {
    if (!this.signupForm.valid || !this.passwordsMatch()) {
      alert('Please check your input and try again.');
      return;
    }
  
    this.authService.register(this.signup_username, this.signup_password)
      .subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          alert('Registered successfully. Please login.');
          this.router.navigate(['/']);
          window.location.reload();
        },
        error: (error) => {
          console.error('Registration failed', error);
          if(error.error){
            alert(error.error);
          }
          alert('Registration failed. Please try again.');
        }
      });
  }

  passwordsMatch(): boolean {
    return this.signup_password === this.signup_password_confirm;
  }
}
