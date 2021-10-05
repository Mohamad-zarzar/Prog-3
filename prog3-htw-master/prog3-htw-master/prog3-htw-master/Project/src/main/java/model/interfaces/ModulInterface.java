package model.interfaces;

import java.util.List;

import model.implementation.Projekt;
import model.implementation.Thema;

/*
 * OLBERTZ In Java ist es eher unueblich, dass man fuer Interfaces ein Praefix oder ein Suffix
 * verwendet. In anderen Programmiersprachen wird das gemacht, aber wenn man mit Java arbeitet,
 * sollte man sich an die allgemein anerkannten Konventionen halten. Nach der Konvention wuerde
 * Das Interface Modul heissen und eine Implementierung z.B. ModulImpl, wie ich es im Kapitel
 * Architektur auch gezeigt habe. 
 */
public interface ModulInterface {
	/*
	 * OLBERTZ Kein Fehler, aber unueblich: Da Methoden in einem Interface nichts anderes
	 * sein koennen als public, laesst man das public normalerweise weg. Das wird in der
	 * Literatur im Allgemeinen auch so empfohlen.
	 */
	public long getId();

	public String getName();

	public List<Projekt> getProjects();

	public String getNotiz();

	public String getSemester();

	public String getImage();

	public List<Thema> getThemen();

	public void setProjects(List<Projekt> projects);

	public void setSemester(String semester);

	public void setNotiz(String notiz);

	public void setName(String name);

	public void setId(long id);

	public void setImage(String imagePath);

	public void setThemen(List<Thema> themen);
}
