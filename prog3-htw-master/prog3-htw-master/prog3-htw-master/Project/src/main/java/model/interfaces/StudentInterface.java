package model.interfaces;

import model.implementation.Projekt;

public interface StudentInterface {
	public int getMatrikelnummer();

	public String getVorname();

	public String getName();

	public double getNote();

	public Projekt getProjekt();

	public void setMatrikelnummer(int matrikelnummer);

	public void setName(String name);

	public void setVorname(String vorname);

	public void setNote(double note);

	public void setProjekt(Projekt projekt);
}
