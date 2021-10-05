package model.interfaces;


import java.util.Set;

import model.implementation.Modul;
import model.implementation.Student;
import model.implementation.Thema;
import model.implementation.URLs;


public interface ProjektInterface {
	public long getId();

	public String getName();

	public String getNotizen();

	public String getMainUrl();

	public byte getSemester();

	public boolean isActive();

	public Modul getModul();

	public Thema getThema();

	public Set<URLs> getUrls();

	public Set<Student> getStudents();

	public Double getNote();
	

	public void setNote(Double note);
	public void setId(long id);

	public void setName(String name);

	public void setNotizen(String notizen);

	public void setSemester(byte semester);

	public void setStudents(Set<Student> students);

	public void setModul(Modul modul);

	public void setThema(Thema thema);

	public void setIsActive(boolean isActive);

	public void setUrls(Set<URLs> urls);

	public void setModul(ModulInterface modul);

	public void setMainUrl(String mainUrl);



    
}
