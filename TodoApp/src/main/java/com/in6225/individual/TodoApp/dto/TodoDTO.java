package com.in6225.individual.TodoApp.dto;

import java.util.Date;
import java.util.Enumeration;

//import com.in6225.individual.TodoApp.model.Todo.Status;

import lombok.Data;

@Data
public class TodoDTO {
	   private Integer id;
	   private Integer user_id;
	   private Date creation_time;
	   private Date modification_time;
	   private Date event_time;
	   private String content;
	   private String title;
	   private String sta;
//	   private enum status {
//	        PENDING,
//	        COMPLETED,
//	        CANCELLED
//	    }
//	public void setStatus(Status status) {
//		status = status;
//		
//	}
}
