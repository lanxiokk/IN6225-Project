import { Component } from '@angular/core';
import { AuthService } from '../../auth.service'; 
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'] 
})
export class DashboardComponent {
  switchers = Array(3).fill(null); 
  activeIndex = 0;  

  showContentColumns: boolean = true;
  showCreateTodo: boolean = false;
  usernam:String = '';

  constructor(private authService: AuthService, private router: Router) {
  
  }

  ngOnInit(): void {
    this.usernam = localStorage.getItem('username') || 'Welcome!';
  }

  setActive(index: number): void {
    this.activeIndex = index;  
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);  
  }

  showCreateTodoForm(): void {
    this.showCreateTodo = true;
    this.showContentColumns = false;
  }

  showTodos(): void {
    this.showCreateTodo = false;
    this.showContentColumns = true;
  }

  
}

