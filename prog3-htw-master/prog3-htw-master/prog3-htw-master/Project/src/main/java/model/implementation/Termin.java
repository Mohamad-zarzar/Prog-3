package model.implementation;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import model.interfaces.*;

@Entity
@Table(name = "Termin")
public class Termin implements TerminInterface {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String title;

	private String comment;

	private String  date;

	private LocalTime time;

	private boolean finished;

	public Termin(long id, String comment, LocalTime time) {
		setId(id);
		setComment(comment);
		setTime(time);
	}

	public Termin() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return title + " " + date;
	}

	@Override
	public boolean equals(Object obj) {
	
		if (obj instanceof Termin) {

			Termin reminder = (Termin) obj;

			if ((Long.valueOf(reminder.getId()).equals(this.getId()))) 
				return true;	
		}
		
		return false;
	}
	
}
