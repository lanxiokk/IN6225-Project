import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TodosService } from '../../todo.service';
// import { TodoStatus } from '../../todo-status'; 

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css']
})
export class TodosComponent implements OnInit {
  // TodoStatus = TodoStatus;

  todos: any[] = [];
  editForms: FormGroup[] = [];
  userId = localStorage.getItem("user_id")!;

  constructor(private todosService: TodosService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.fetchTodos();
  }

  fetchTodos(): void {
    this.todosService.getTodosByUserId(this.userId).subscribe({
      next: (data) => {
        this.todos = data;
        this.editForms = this.todos.map(todo => this.fb.group({
          id: [todo.id],
          userId: [todo.user_id],
          sta: [todo.sta],
          eventTime: [new Date(todo.event_time).toISOString().substring(0, 16)], // Ensure correct format for datetime-local input
          content: [todo.content],
          title: [todo.title]
        }));
      },
      error: (err) => {
        console.error('Failed to fetch todos', err);
        alert("Failed to load todos.");
      }
    });
  }

  saveChanges(index: number): void {
    const todoData = this.editForms[index].value;
    this.todosService.updateTodo(todoData.id, todoData).subscribe({
      next: () => {
        alert('Todo updated successfully.');
      },
      error: (err) => {
        console.error('Error updating todo:', err);
        alert('Failed to update todo. Please try again.');
      }
    });
  }

  deleteTodo(index: number): void {
    const todoData = this.editForms[index].value;
    this.todosService.deleteTodo(todoData.id).subscribe({
      next: () => {
        alert('Todo deleted successfully.');
      },
      error: (err) => {
        console.error('Error deleting todo:', err);
        alert('Failed to delete todo. Please try again.');
      }
    });
  }
}
