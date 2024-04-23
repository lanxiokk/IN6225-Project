package com.in6225.individual.TodoApp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
@Data
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "creation_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "modification_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Sta sta;
    @Column(nullable = false)
    private String sta;

    @Column(name = "event_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime;
    
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;


//    public enum Sta {
//        PENDING,
//        COMPLETED,
//        CANCELLED
//    }
    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationTime() {
		return creationTime;
	}
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

//	public Sta getStatus() {
//		return sta;
//	}
//
//	public void setStatus(Sta status) {
//		this.sta = status;
//	}

	public Date getEventTime() {
		return eventTime;
	}
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
	@Override
	public String toString() {
		return "Todo is id "+ this.id+" user_id is "+this.getUser().getId()+" eventTime is "+this.getEventTime().toString();
		
	}
    
}
