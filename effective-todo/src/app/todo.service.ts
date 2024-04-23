import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodosService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getTodosByUserId(userId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/todos/user/${userId}`);
  }

  createTodo(todoData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/todos`, todoData);
  }

  deleteTodo(todoId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/todos/${todoId}`);
  }

  updateTodo(todoId: number, todoData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/todos/${todoId}`, todoData);
  }
  
}
