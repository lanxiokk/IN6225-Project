import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/authenticate`, { username, password });
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, { username, password });
  }

  validateToken(): Observable<any> {
    return this.http.get(`${this.baseUrl}/validateToken`, {});
  }

  isLoggedIn(): Promise<boolean> {
    const token = localStorage.getItem('token');
    const user_id = localStorage.getItem('user_id');
    if (!token || !user_id) {
        return Promise.resolve(false);
    }
    return this.validateToken().toPromise()
        .then(result => {
            console.log('Token validation result:', result);
            if (result.error) {  
                console.log('Token validation result!!:', result);
                return false; 
            }
            return true;
        })
        .catch(error => {
            console.error('Error during token validation:', error);
            return false; 
        });
  }

  logout(): void {
    localStorage.clear();
    alert("Successfully sign out")
  }

  
}
