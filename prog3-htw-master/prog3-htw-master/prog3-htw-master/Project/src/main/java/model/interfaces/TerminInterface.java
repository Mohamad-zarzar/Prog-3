package model.interfaces;


import java.time.LocalTime;

public interface TerminInterface {

	public long getId();

	public void setId(long id);

	public String getTitle();

	public void setTitle(String title);

	public String getComment();

	public void setComment(String comment);

	public String getDate();

	public void setDate(String date);

	public LocalTime getTime();

	public void setTime(LocalTime time);

	public boolean isFinished();

	public void setFinished(boolean finished);


	
}