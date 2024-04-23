import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TodosService } from '../../todo.service'; // Your service to interact with the API
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-todo',
  templateUrl: './create-todo.component.html',
  styleUrls: ['./create-todo.component.css']
})
export class CreateTodoComponent implements OnInit {
  todoForm!: FormGroup;

  constructor(private fb: FormBuilder, private todosService: TodosService, private router: Router) {}

  ngOnInit(): void {
    this.todoForm = this.fb.group({
      userId: [localStorage.getItem('user_id'), Validators.required],
      sta: ['PENDING', Validators.required],
      eventTime: [new Date().toISOString(), Validators.required],
      content: ['', Validators.maxLength(100)],
      title: ['', [Validators.required, Validators.maxLength(100)]]
    });
  }

  createTodo(): void {
    if (this.todoForm.valid) {
      this.todosService.createTodo(this.todoForm.value).subscribe({
        next: () => {
          this.router.navigate(['/dashboard']);
          alert('Todo created successfully!');
        },
        error: (err) => {
          console.error('Error creating todo:', err);
          alert('Failed to create todo. Please try again.');
        }
      });
    }
  }
}
