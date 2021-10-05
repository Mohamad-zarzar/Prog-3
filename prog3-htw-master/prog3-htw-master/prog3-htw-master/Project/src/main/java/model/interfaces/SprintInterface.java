package model.interfaces;

import java.time.LocalDate;

import model.implementation.Projekt;

public interface SprintInterface {

	    public long getId();

	    public String getSprintBeschreibung();

	    public String getNotizen();

	    public LocalDate getSprintAnfang();

	    public LocalDate getSprintEnde();
	    
	    public Projekt getProjekt();


	    public void setId(long id);

	    public void setSprintBeschreibung(String sprintBeschreibung);

	    public void setNotizen(String notizen);

	    public void setSprintAnfang(LocalDate sprintAnfang);

	    public void setSprintEnde(LocalDate sprintEnde);

		void setProjekt(Projekt projekt);
}
