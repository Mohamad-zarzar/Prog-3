package model.interfaces;

import java.util.Set;

import model.implementation.Modul;
import model.implementation.Projekt;

public interface ThemaInterface {

	public long getId();

	public void setId(long id);

	public String getName();

	public void setName(String name);

	public String getBeschreibung();

	public void setBeschreibung(String beschreibung);

	public String getNotiz();

	public void setNotiz(String notiz);

	public Set<Modul> getModules();

	public void setModules(Set<Modul> modules);

	public Set<Projekt> getProjectsList();

	public void setProjectsList(Set<Projekt> projectsList);
}
